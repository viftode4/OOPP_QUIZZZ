package server.service;

import commons.MultiPlayerGame;
import commons.Player;
import commons.Round;
import commons.questions.ComparisonQuestion;
import commons.questions.GuessQuestion;
import commons.questions.PreciseQuestion;
import commons.questions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thread.TimerThread;
import server.database.ActivityRepository;
import server.database.QuestionGenerator;
import server.storage.GameStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static commons.GameState.*;

@Service
public class GameService {

    private final ActivityRepository activityRepository;
    private final HashMap<Long, TimerThread> gameTimerThreads = new HashMap<>();
    @Autowired
    public GameService(ActivityRepository activityRepository)
    {
        this.activityRepository = activityRepository;
    }

    /**
     * Method for returning all the games in the server
     * @return List of the games
     */
    public List<MultiPlayerGame> getGames(){
        return new ArrayList<>(GameStorage.getInstance().getGames().values());
    }
    /**
     * Returns the game a player is curently on
     * @param player the player
     * @return the game
     */
    public MultiPlayerGame getGame(Player player)
    {
        return GameStorage.getInstance().getGames().get(player.getGame());
    }
    /**
     * Creates a new game and adds it to the GameStorage
     * @param player the player that created the game
     * @return the new game created
     */
    public MultiPlayerGame createGame(Player player)
    {
        MultiPlayerGame game = new MultiPlayerGame();
        //generation questions for the game
        QuestionGenerator qq = new QuestionGenerator(activityRepository.findAll());
        List<Round> roundList = new LinkedList<>();
        List<Question> questions = qq.generate20Questions();
        for (int i = 0; i < 20; i++)
            roundList.add(new Round(i+1,questions.get(i)));
        game.setRoundList(roundList);

        //setting the game id
        game.setGameId((long) (GameStorage.getInstance().getGames().size()+1));


        game.addPlayer(player);
        game.setStatus(NEW);
        GameStorage.getInstance().setGame(game);

        //why are gameids long and the other methods for gameid use int
        player.setGame(Math.toIntExact(game.getGameId()));
        //putting thread that keeps track of time
        gameTimerThreads.put(game.getGameId(), createTimerThread(game.getGameId()));
        return game;
    }

    /**
     * Method for joining a game with a specific gameId
     * @param player the player that wants to connect
     * @param gameId the gameId of the game requested
     * @return the game
     * @throws Exception
     */
    public MultiPlayerGame connectToGame(Player player, int gameId) throws Exception{
        if(!GameStorage.getInstance().getGames().containsKey(gameId)){
            MultiPlayerGame bad = new MultiPlayerGame();
            bad.setStatus(NOID);
            return bad;
        }

        MultiPlayerGame game = GameStorage.getInstance().getGames().get(gameId);

        if(game.getStatus().equals(FULL) || game.getStatus().equals(STARTED) || game.getStatus().equals(FINISHED)){
            MultiPlayerGame bad = new MultiPlayerGame();
            bad.setStatus(NOENTER);
            return bad;
        }
        if(game.getPlayerList().contains(player)) {
            MultiPlayerGame bad = new MultiPlayerGame();
            bad.setStatus(INVALID);
            return bad;
        }
        game.addPlayer(player);
        if(game.getPlayerList().size()==10)
            game.setStatus(FULL);
        // this not works cuz server cannot modify player client
        player.setGame(Math.toIntExact(game.getGameId()));
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     * Method for joining a random game
     * @param player the player that wants to connect
     * @return the game
     * @throws Exception
     */
    public MultiPlayerGame connectToRandomGame(Player player) throws Exception {
        List<MultiPlayerGame> games = GameStorage.getInstance().getGames().values().stream()
                .filter(x -> x.isValid())
                .collect(Collectors.toList());

        //takes all games that are new

        MultiPlayerGame game = new MultiPlayerGame();

        boolean found = false;
        for(MultiPlayerGame x : games)
            if(!x.getPlayerList().contains(player)) {
                found = true;
                game = x;
                break;
            }

        if(!found) {
            game.setStatus(INVALID);
            return game;
        }

        game.addPlayer(player);
        if(game.getPlayerList().size()==10)
            game.setStatus(FULL);
        // this not works cuz server cannot modify player client
        player.setGame(Math.toIntExact(game.getGameId()));
        GameStorage.getInstance().setGame(game);
        return game;
    }

    /**
     * Method for starting the game
     * @param player the player that starts the game
     * @return the game
     * @throws Exception
     */
    public MultiPlayerGame startGame(Player player) throws Exception {
        if(!GameStorage.getInstance().getGames().containsKey(player.getGame())) {
            throw new Exception("The game with id " + player.getGame() + " doesn't exist!");
        }

        MultiPlayerGame game = GameStorage.getInstance().getGames().get(player.getGame());
        if(!game.getPlayerList().contains(player)){
            throw new Exception("You cannot start a game you are not into");
        }
        if(game.getStatus().equals(STARTED)){
            throw new Exception("The game already started!");
        }
        if(game.getStatus().equals(FINISHED)){
            throw new Exception("The game already finished and cannot start again!");
        }

        game.setStatus(STARTED);

        // this not works cuz server cannot modify player client
        player.setGame(Math.toIntExact(game.getGameId()));
        game.setTimervalue(8.9);
        GameStorage.getInstance().setGame(game);

        //start the counter thread not adjusted for server delay

        gameTimerThreads.get(game.getGameId()).start();

        return game;
    }

    /**
     * Method for the equivalent of a game move. It choses an answer for
     * the player in the game and evaluates it
     * @param player the player that answered
     * @param answer the answer
     * @param gameId the id of the game in which the player is
     * @return the game
     * @throws Exception
     */
    public MultiPlayerGame chooseAns(Player player, int answer, int gameId) throws Exception {
        if(player.hasAnswered()) {
            throw new Exception("Player already answered this round!");
        }
        if(!GameStorage.getInstance().getGames().containsKey(gameId)){
            throw new Exception("Invalid gameID");
        }

        MultiPlayerGame game = GameStorage.getInstance().getGames().get(gameId);

        if(game.getStatus().equals(FINISHED)) {
            throw new Exception("Game is already finished!");
        }
        if(game.getStatus().equals(NEW)||game.getStatus().equals(FULL)){
            throw new Exception("Game hasn't started yet!");
        }
        //we get the current round we re on
        Round curentRound = game.getRoundList().get(game.getRoundNumber());
        Question curentQuestion = curentRound.getQuestion();
        //we check the answer with the question
        //if it's correct we increase the score
        if(checkAnswer(curentQuestion, answer)) {
            game.increaseScore(player);
            // this not works cuz server cannot modify player client
            player.increaseScore(1000 * (int)GameStorage.getInstance().getGames().get(Math.toIntExact(gameId))
                    .getTimervalue() / 8);
            player.incCorrectQNo();
        }
        //the player answered he cannot answer again
        game.setAnsFlag(player, true);
        // this not works cuz server cannot modify player client
        //player.setFlagAns(true);

        //if all players answered go to the next round
        if(game.allPlayersAnswered())
        {
            List<Player> playerList = game.getPlayerList();
            for(Player x : playerList)
                game.setAnsFlag(x, false);

            //show that round should be finished earlier since everyone finished answering

            if(game.getRoundNumber()==19){
                game.setStatus(FINISHED);
            }else{
                game.setTimervalue(0);
            }

        }
        //the game is finished after 20 rounds
        // round "20" is the ending
        GameStorage.getInstance().setGame(game);

        return game;
    }

    /**
     * Method for checking an answer for a specific question
     * @param q the question, can be either of the 3 types
     * @param answer the answer given, numerical value
     * @return true if the answer is correct, false otherwise
     */
    public boolean checkAnswer(Question q, int answer){
        if(q.getClass().getSimpleName().equals("ComparisonQuestion"))
        {
            ComparisonQuestion x = (ComparisonQuestion) q;
            if(x.getBaseActivityConsumption() == answer)
                return true;
            return false;
        }
        else if(q.getClass().getSimpleName().equals("GuessQuestion"))
        {
            GuessQuestion x = (GuessQuestion) q;

            if(answer >= x.getCorrectAnswerLowerLim() &&
                    answer <= x.getCorrectAnswerUpperLim())
                return true;
            return false;
        }
        else{
            PreciseQuestion x = (PreciseQuestion) q;

            if(answer == x.getBaseActivityConsumption())
                return true;
            return false;
        }
    }

    /**
     * Uses Thread sleep to imitate a timer and sets the game Status/RoundNumber accordingly
     * @param originalgame game id of the game to keep track of timer for
     * @return A new TimerThread with timer functinonality and ability to change Game Fields based on time
     */
    public TimerThread createTimerThread(long originalgame){
        return new TimerThread(originalgame);
    }

    /**
     * Method for leaving the lobby
     * @param player player who wants to leave the lobby
     * @return returns the new player with no game
     * @throws Exception
     */
    public Player leaveLobby(Player player) throws Exception {

        int gameid = player.getGame();
        MultiPlayerGame game = GameStorage.getInstance().getGames().get(gameid);

        if(!game.getPlayerList().contains(player)){
            throw new Exception("You cannot leave the game you are not into");
        }
        if(game.getStatus().equals(FINISHED)){
            throw new Exception("You cannot leave the game that is already finished");
        }

        for (Player player1 : game.getPlayerList()){
            if(player1.getUsername().equals(player.getUsername())){
                game.getPlayerList().remove(player1);
                player.setGame(-1);
                break;
            }
        }

        if (game.getPlayerList().size() == 0){
            GameStorage.getInstance().getGames().remove(gameid);
        }

        return  player;
    }


    /**
     * Deletes the game when the game ends
     * @param game the game that has ended
     * @return returns the game that has ended
     * @throws Exception
     */
    public MultiPlayerGame gameCleanup(MultiPlayerGame game) throws Exception {

        int gameid = game.getGameId().intValue();

        if (GameStorage.getInstance().getGames().get(gameid) == null){
            throw new Exception("This game does not exists");
        }

        MultiPlayerGame game1 = GameStorage.getInstance().getGames().get(gameid);

        if (!game1.getStatus().equals(FINISHED)){
            throw new Exception("The game has not finished yet");
        }

        GameStorage.getInstance().getGames().remove(gameid);

        return game;
    }

    /**
     * halves the time for every player in that game other than the player who used the joker
     * @param game the game of the player who used the joker
     * @return returns the game of the player who used the joker
     * @throws Exception
     */
    public MultiPlayerGame haste(MultiPlayerGame game) throws Exception {
        long gameid = game.getGameId();
//
//        if (GameStorage.getInstance().getGames().get((int)gameid) == null){
//            throw new Exception("This game does not exists");
//        }
//
//        MultiPlayerGame game1 = GameStorage.getInstance().getGames().get((int)gameid);
//
//        if (game1.getStatus().equals(FINISHED)){
//            throw new Exception("The game has already finished");
//        }

        gameTimerThreads.get(gameid).joker3 = true;

        return game;
    }
}

