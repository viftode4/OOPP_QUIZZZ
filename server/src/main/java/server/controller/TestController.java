package server.controller;


import commons.*;
import commons.questions.ComparisonQuestion;
import commons.questions.GuessQuestion;
import commons.questions.PreciseQuestion;
import commons.questions.Question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.ActivityRepository;
import server.database.QuestionGenerator;
import server.database.UserRepository;


import java.util.*;
import java.util.function.Consumer;


@RestController
@RequestMapping("/")
@EnableScheduling
public class TestController {

    public HashMap<Integer, MultiPlayerGame> MultiplayerGameHashMap;
    public HashMap<Integer, SinglePlayerGame> SingleplayerGameHashMap;
    private final ActivityRepository activityRepository;
    private final UserRepository singleplayerUserRepo;

    private HashMap<Object, Consumer<List<EmojiEntry>>> Emojilistener = new HashMap<>();
    private HashMap<Object, Consumer<Game>> GameStartListener = new HashMap<>();
    @Autowired
    public TestController(ActivityRepository activityRepository, UserRepository singleplayerUserRepo){
        this.singleplayerUserRepo = singleplayerUserRepo;
        this.MultiplayerGameHashMap = new HashMap<>();
        this.SingleplayerGameHashMap = new HashMap<>();
        this.activityRepository= activityRepository;
    }
    @GetMapping("/")
    @ResponseBody
    public String index() {
        return singleplayerUserRepo.toString();
    }

    /**
     * @return List of activities inside database
     */
    @GetMapping("/admin/getactivities")
    public ResponseEntity<List<Activity>> getAllActivities(){
        return ResponseEntity.ok(activityRepository.findAll());
    }

    /**
     * @param activity (Activity instance)
     * @return String with information about which activity was updated or saved
     */
    @PostMapping("/admin/updateactivity")
    public ResponseEntity<String> updateActivity(@RequestBody Activity activity){
        System.out.println(activity.getId());
        activityRepository.save(activity);
        return ResponseEntity.ok("Updated!" + " " +  "Activity with id " + activity.getId());
    }

    @GetMapping("/admin/deleteactivity")
    public ResponseEntity<String> deleteActivity(@RequestParam Long id){
        activityRepository.deleteById(id);
        return ResponseEntity.ok("Deleted activity with id" + id);
    }
    @PostMapping(path = {"/singleplayer/players"})
    public ResponseEntity<Player> addSinglePlayer(@RequestBody Player player) {
        List<Player> list = singleplayerUserRepo.findAll();
        Set<Integer> keySet = SingleplayerGameHashMap.keySet();
        SinglePlayerGame gm = new SinglePlayerGame();
        gm.setPlayer(player);
        gm.setGameId((long) (keySet.size()));
        player.setGame(keySet.size());
        SingleplayerGameHashMap.put(keySet.size()+1, gm);

        boolean contains = false;

        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getUsername().equals(player.getUsername()))
                contains = true;

        if (!contains)
            singleplayerUserRepo.save(player);

        return ResponseEntity.ok(player);
    }
    @PostMapping(path = {"/singleplayer/players/update"})
    public ResponseEntity<Player> updateSinglePlayer(@RequestBody Player player) {
        List<Player> list = singleplayerUserRepo.findAll();
        for (int i = 0; i < list.size(); i++) {
            if (player.getUsername().equals(list.get(i).getUsername())) {
                singleplayerUserRepo.delete(list.get(i));
            }
        }
        singleplayerUserRepo.save(player);
        return ResponseEntity.ok(player);
    }

    @GetMapping(path = {"/singleplayer/games/rounds"})
    //@Scheduled(fixedRate = 5000)
    public ResponseEntity<List<Round>> getRounds() {

        var rounds = fetchRandom20Question();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "MyController");

        return ResponseEntity.accepted().headers(headers).body(rounds);

    }


    /**
     * checks if there is anybody else in the game using the username which the new player wants to use
     * @param player player whose username is to be checked
     * @param gameid the game id which the username check will be made for
     * @return returns true if the username of the player is available for that game, returns false if it's not
     */
    public boolean checkUsername(Player player, int gameid){
        MultiPlayerGame game = MultiplayerGameHashMap.get(gameid);
        for(Player player1 : game.getPlayerList()){
            if (player.getUsername().equals(player1.getUsername())){
                return false;
            }
        }
        return true;
    }

    @GetMapping(path = {"/multiplayer/{gameid}/player"})
    @ResponseBody
    public String printPlayersInGame(@PathVariable("gameid") int gameid) {
        String str = "";
        List<Player> list = MultiplayerGameHashMap.get(gameid).getPlayerList();
        for (int i = 0; i < list.size(); i++) {
            str = str + list.get(i).toString();
            str = str + "\n";
        }
        return str;
    }


    @PostMapping("/question/answer/send")
    public ResponseEntity<Player> answerChecker(@RequestParam  int answerSent, @RequestParam int gameid, @RequestBody Player player) {
        //returns boolean since we can just change colour without knowing the right answer for the answer boxes
        // boolean could indicate a popup on the client side thought
        //update score for player object
        //call sort on playerlist for the leaderboard
        System.out.println("y");
        //finding game
        MultiPlayerGame curgame = MultiplayerGameHashMap.get(gameid);
        if(curgame!=null) {

            //calculate score
//            if (curgame.getRoundList() != null) {
//                int rightanswer = curgame.getRoundList().get(curgame.getRoundNumber()).getQuestion().getBaseActivityConsumption();
//                int timeleft = 8 - curgame.getRoundList().get(curgame.getRoundNumber()).getTime();
//                if (answersent == rightanswer) {
//                    curgame.getPlayerList().get(curgame.getPlayerList().indexOf(player)).updateScore(100 + 25 * timeleft);
//                }
//            }


            if(curgame.getRoundList() != null){
                Question question = curgame.getRoundList().get(curgame.getRoundNumber()).getQuestion();
                int timeLeft = 8 - curgame.getRoundList().get(curgame.getRoundNumber()).getTime();

                if(question.getClass().getSimpleName().equals("PreciseQuestion")){

                    PreciseQuestion preciseQuestion = (PreciseQuestion) question;

                    int correctConsumption = preciseQuestion.getBaseActivityConsumption();

                    if(answerSent == correctConsumption){
                        curgame.getPlayerList().get(curgame.getPlayerList().indexOf(player)).updateScore(100 + 25 * timeLeft);
                    }


                }
                else if(question.getClass().getSimpleName().equals("GuessQuestion")){
                    GuessQuestion guessQuestion = (GuessQuestion) question;

                    if(answerSent >= guessQuestion.getCorrectAnswerLowerLim() &&
                            answerSent <= guessQuestion.getCorrectAnswerUpperLim()){
                        curgame.getPlayerList().get(curgame.getPlayerList().indexOf(player)).updateScore(100 + 25 * timeLeft);
                    }

                }
                // Essential implementation! In the current check, the energy consumption of the selected element is sent as answersent
                else{
                    ComparisonQuestion comparisonQuestion = (ComparisonQuestion) question;

                    if(answerSent == comparisonQuestion.getBaseActivityConsumption()){
                        curgame.getPlayerList().get(curgame.getPlayerList().indexOf(player)).updateScore(100 + 25 * timeLeft);
                    }

                }

            }


        }

        return ResponseEntity.ok(new Player(1212L,"me")); //curgame.getPlayerList().get(curgame.getPlayerList().indexOf(player)));
    }

    /**
     * Retrieves a string representation of players in all multiplayer games
     * @return a string representing players
     */
    @GetMapping("/multiplayer/allplayers")
    @ResponseBody
    public String printPlayers() {
        String str = "";
        Iterator<MultiPlayerGame> ite = MultiplayerGameHashMap.values().iterator();
        while (ite.hasNext()) {
            str = str + ite.next().getPlayerList().toString();
            str = str + "\n";
        }
        return str;
    }

    /**
     * Retrieves the list of players in a certain game
     * @param gameid the gameid for which the players should be retrieved
     * @return a list of players in the game
     */
    @GetMapping("/multiplayer/{gameid}/leaderboard")
    @ResponseBody
    public List<Player> getAllPlayersByID(@PathVariable("gameid") long gameid) {
        return MultiplayerGameHashMap.get(gameid).getPlayerList();
    }

    @GetMapping("/singleplayer/allplayers")
    @ResponseBody
    @Scheduled(fixedRate = 2000,initialDelay = 8000)
    public List<Player> printSinglePlayers() {
        List<Player> list = singleplayerUserRepo.findAll();
        if(list.isEmpty()){
            return new ArrayList<>();
        }
        return list;
    }

    @GetMapping("/multiplayer/allgames")
    @ResponseBody
    public String printMultiplayerGames() { return MultiplayerGameHashMap.toString(); }

    @GetMapping("/singleplayer/allgames")
    @ResponseBody
    public String printSinglePlayerGames() { return SingleplayerGameHashMap.toString(); }


    //mapping for using jokers
    @GetMapping("/joker/{jokerid}/{gameid}/{clientid}")
    public ResponseEntity<String>JokerUsed(@PathVariable("jokerid") int jokerid, @PathVariable("clientid") Long clientid, @PathVariable("gameid") Long gameid){

        //three different jokers, based on id sent by user separate the diff functionalities
        if(jokerid<1 || jokerid>3){
            return ResponseEntity.badRequest().build();
        }else{

            //Half time
            if(jokerid==1){

                //no need to return information, maybe message showing succes on screen
                return ResponseEntity.ok("Halfed time");
            }
            //Remove wrong
            else if(jokerid==2){

                //info to remove wrong answer
                return ResponseEntity.ok("Return info to remove the wrong answer on client screen");
            }
            //Double point
            else{

                //Info to indicate double point is registered for the question
                return ResponseEntity.ok("2x");
            }
        }

    }

    //Mapping for receiving emojis sent from players
    @PostMapping("/emojisent")
    public ResponseEntity<String> IncomingEmoji(@RequestParam String emojiposted ,@RequestParam String username, @RequestParam int gameid){

        //receives emoji from client through post request
        //request parameters :
        // emojiposted -> actual emoji string
        // username -> username of poster
        // gameid -> gameid of the game of the user who posted

        MultiPlayerGame curgame = MultiplayerGameHashMap.get(gameid);
        EmojiEntry emojientry = new EmojiEntry(username,emojiposted);
        curgame.getEmojiChat().add(emojientry);

        //Emoji listening for new additions to be sent to the client
        Emojilistener.forEach((key, listener) -> listener.accept(curgame.getEmojiChat()));
        return ResponseEntity.ok("Sent");
    }

    //Mapping for giving players their game's emojichat
    //long polling with DeferredResult
    @GetMapping("/needemojis")
    public DeferredResult<ResponseEntity<List<EmojiEntry>>> SendingEmojis(@RequestParam int gameid){
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<List<EmojiEntry>>>(5000L,noContent);

        var key = new Object();
        Emojilistener.put(key, emojis -> {
            res.setResult(ResponseEntity.ok(MultiplayerGameHashMap.get(gameid).getEmojiChat()));
        });
        res.onCompletion(()->{
            Emojilistener.remove(key);
        });
        return res;
    }

    //long polling from clientside, to constantly check if anyone has started the game
    //
    @GetMapping("/startingame")
    public DeferredResult<ResponseEntity<Game>> GameStarter(@RequestParam int gameid){
        var noContent =ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Game>>(5000L,noContent);

        var key = new Object();
        GameStartListener.put(key, game -> {
            System.out.println(MultiplayerGameHashMap.get(gameid).getPlayerList().toString());
            res.setResult(ResponseEntity.ok(MultiplayerGameHashMap.get(gameid)));
        });
        res.onCompletion(()->{
            GameStartListener.remove(key);
        });
        return res;
    }

    /**
     * Creates a list of 20 random questions
     * @return 20 random questions in list form
     */
    public List<Round> fetchRandom20Question(){
//        List<Round> roundList = new LinkedList<>();
//        Set<Integer> alreadyselectedquestions = new HashSet<>();
//
//        //change to 20 iterations when database is ready
//        int noquestions = (int) qsRepo.count();
//        Random rnd = new Random();
//        int selected = Integer.MIN_VALUE;
//        for(int i = 1; i< 2; i++){
//            selected = rnd.nextInt(noquestions-1)+1;
//            while(alreadyselectedquestions.contains(selected)){
//                selected = rnd.nextInt(noquestions-1)+1;
//            }
//            alreadyselectedquestions.add(selected);
//            //creates and adds round objects into a list
//            //did not put optional check yet, not sure if needed
//            roundlist.add(new Round((long) i ,qsRepo.findById((long) selected).get()));
//
//        }
//        return roundlist;
        QuestionGenerator questionGenerator = new QuestionGenerator(activityRepository.findAll());
        List<Round> roundList = new LinkedList<>();

        List<Question> questions = questionGenerator.generate20Questions();
        for (int i = 0; i < 20; i++) {
            roundList.add(new Round(i+1,questions.get(i)));
        }

        return roundList;
    }
}