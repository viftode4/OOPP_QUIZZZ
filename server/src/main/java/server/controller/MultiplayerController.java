package server.controller;

import commons.*;
import commons.dto.ConnectRequest;
import commons.dto.PlayerAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;
import server.database.UserRepository;
import server.service.GameService;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@RestController
@RequestMapping("/multiplayer")
@EnableScheduling
public class MultiplayerController {

    public HashMap<Integer, MultiPlayerGame> MultiplayerGameHashMap;
    public HashMap<Integer, SinglePlayerGame> SingleplayerGameHashMap;

    private final ActivityRepository activityRepository;
    private final UserRepository singleplayerUserRepo;

    private final GameService gameService;

    private final HashMap<Object, Consumer<List<EmojiEntry>>> Emojilistener = new HashMap<>();
    private final HashMap<Object, Consumer<Game>> GameStartListener = new HashMap<>();

    @Autowired
    public MultiplayerController(ActivityRepository activityRepository, UserRepository singleplayerUserRepo, GameService gameService){
        this.singleplayerUserRepo = singleplayerUserRepo;
        this.MultiplayerGameHashMap = new HashMap<>();
        this.SingleplayerGameHashMap = new HashMap<>();
        this.activityRepository = activityRepository;
        this.gameService = gameService;
    }

    /**
     * Method for returning all the games on the server
     * @return a list containing the games
     */
    @GetMapping("/games")
    public ResponseEntity<List<MultiPlayerGame>> getGames(){
        return ResponseEntity.ok(gameService.getGames());
    }

    /**
     * Method for returning the game a player is connected to
     * @param player the player
     * @return the game
     */
    @PostMapping("/get")
    public ResponseEntity<MultiPlayerGame> getGame(@RequestBody Player player){

        return ResponseEntity.ok(gameService.getGame(player));
    }
    /**
     * Make a new Game for a player
     * @param player the player that started a new game
     * @return the game created
     */
    @PostMapping("/make")
    public ResponseEntity<MultiPlayerGame> makeGame(@RequestBody Player player){
        System.out.println("make a new game for player " + player);
        return ResponseEntity.ok(gameService.createGame(player));
    }
    @PostMapping("/start")
    public ResponseEntity<MultiPlayerGame> start(@RequestBody Player player) throws Exception {
        System.out.println("start the game request by player: " + player);
        return ResponseEntity.ok(gameService.startGame(player));
    }
    /**
     * Joining a game with a gameId
     * @param request the player that wants to connect to the game
     * @return the game
     */
    @PostMapping("/connect")
    public ResponseEntity<MultiPlayerGame> connect(@RequestBody ConnectRequest request) throws Exception {
        System.out.println("connect to game " + request.getGameId() + " by player " + request.getPlayer());
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }

    /**
     * Joining a random game
     * @param player the player that wants to connect
     * @return the game
     */
    @PostMapping("/connect/random")
    public ResponseEntity<MultiPlayerGame> connectRandom(@RequestBody Player player) throws Exception {
        System.out.println("connect to random game by player " + player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    /**
     * Choosing an answer during the game
     * @param move the player that answered in the game
     * @return the game
     * @throws Exception
     */
    @PostMapping("/answer")
    public ResponseEntity<MultiPlayerGame> answerCheck(@RequestBody PlayerAnswer move) throws Exception {
        System.out.println("Answer " + move.getAnswer() + " by player " + move.getPlayer() + " in game " + move.getGameId());
        MultiPlayerGame game = gameService.chooseAns(move.getPlayer(), move.getAnswer(), move.getGameId());
        return ResponseEntity.ok(game);
    }

    /**
     * @return the Game Service
     */
    @GetMapping("/service")
    public ResponseEntity<GameService> getGameService(){
        return ResponseEntity.ok(gameService);
    }


    /**
     * Leaving the lobby
     * @param player player who wants to leave the lobby
     * @return the player who left the lobby
     * @throws Exception
     */
    @PostMapping("/leaveLobby")
    public ResponseEntity<Player> leaveLobby(@RequestBody Player player) throws Exception {
        System.out.println(player + " wants to leave the game");
        return ResponseEntity.ok(gameService.leaveLobby(player));
    }

    /**
     * Deletes the game when the game ends
     * @param game the game that has ended
     * @return returns the game that has ended
     * @throws Exception
     */
    @PostMapping("/gameCleanup")
    public ResponseEntity<MultiPlayerGame> gameCleanup(@RequestBody MultiPlayerGame game) throws Exception {
        System.out.println("Deleting the game" + game);
        return ResponseEntity.ok(gameService.gameCleanup(game));
    }


    /**
     * halves the time for every player in that game other than the player who used the joker
     * @param game the game of the player who used the joker
     * @return returns the game of the player who used the joker
     * @throws Exception
     */
    @PostMapping("/haste")
    public ResponseEntity<MultiPlayerGame> haste(@RequestBody MultiPlayerGame game) throws Exception {
        System.out.println("Haste joker used");
        return ResponseEntity.ok(gameService.haste(game));
    }

}
