package client.scenes.multiplayer;


import client.scenes.MainGameCtrl;
import client.service.MPGameLoopService;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.MultiPlayerGame;
import commons.Player;
import commons.dto.ConnectRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

import static commons.GameState.*;

public class LobbyChoiceWindowCtrl implements Initializable {
    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final MultiplayerUtils multiServer;
    private final MPGameLoopService gameLoopService;

    @FXML
    private Button joinRandomly;
    @FXML
    private Button joinByID;
    @FXML
    private TextField gameIDEntry;
    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private Button returnBack;
    @FXML
    private Label numberWaiting;
    @FXML
    private Label numberPlaying;
    @FXML
    private Button newGameButton;

    /**
     * This is the constructor method for the controller class of the
     * "Lobby Choice Window" where the player is asked to choose the lobby
     * they will join, or join a random lobby instead.
     * @param server
     * @param mainCtrl
     * @param multiServer
     * @param gameLoopService
     */
    @Inject
    public LobbyChoiceWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiServer, MPGameLoopService gameLoopService) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiServer = multiServer;
        this.gameLoopService = gameLoopService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.left.setImage(new Image("@../../assets/filler1.png"));
        this.right.setImage(new Image("@../../assets/filler1.png"));
    }

    /**
     * takes the user to the name choice window
     */
    public void jumpToNameChoice(){
        mainCtrl.showNameChoice();
    }

    /**
     * Stops game polling thread
     */
    public void StopGameThread(){
        this.gameLoopService.stopGame();
    }
    /**
     * will take the user to the lobby screen of the game if the username that the user entered is available,
     * else, it will take the user to the name choice screen and tell the user to choose another username
     */
    public void jumpToRandomGame(){
        Player player = mainCtrl.getPlayer();
        MultiPlayerGame game = multiServer.connectToRandomGame(player);

        if(game.getStatus().equals(INVALID)){
            mainCtrl.getNameChoiceCtrl().getMessage().setText("There are no available games, " +
                    "try again later or make a new one!");
            jumpToNameChoice();
            return;
        }
        player = game.returnUpdatedPlayer(player);
        mainCtrl.setPlayer(player);

        mainCtrl.setJoker3(true);
        mainCtrl.setJoker2(true);
        mainCtrl.setJoker1(true);
        mainCtrl.getPlayer().setJoker1(true);
        mainCtrl.getPlayer().setJoker2(true);
        mainCtrl.getPlayer().setJoker3(true);

        mainCtrl.getNameChoiceCtrl().getMessage().setText("");
        mainCtrl.showLobby();
        gameLoopService.startGame();
    }


    /**
     * will take the user to the lobby screen of the game if the username that the user entered is available
     */
    public void jumpToSelectedGame(){
        Player player = mainCtrl.getPlayer();
        int gameID = Integer.parseInt(gameIDEntry.getText());
        //make the request
        ConnectRequest request = new ConnectRequest(player, gameID);

        MultiPlayerGame game = multiServer.connectToGameWithID(request);

        //check if the connection was succesfull without flags
        if(game.getStatus().equals(NOENTER)){
            mainCtrl.getNameChoiceCtrl().getMessage().setText("You cannot join this game because " +
                    "it is either full, started, or already finished");
            jumpToNameChoice();
            return;
        }
        else if(game.getStatus().equals(NOID)){
            mainCtrl.getNameChoiceCtrl().getMessage().setText("There doesn't exist a game with this gameID " +
                    "chose another one");
            jumpToNameChoice();
            return;
        }
        else if(game.getStatus().equals(INVALID)){
            mainCtrl.getNameChoiceCtrl().getMessage().setText("You cannot join this game because there is already" +
                    " another player with the same username. Chose another one!");
            jumpToNameChoice();
            return;
        }
        //if no flags then connect to the game and go to lobby
        player = game.returnUpdatedPlayer(player);
        mainCtrl.setPlayer(player);

        mainCtrl.setGame(game);
        mainCtrl.getNameChoiceCtrl().getMessage().setText("");
        mainCtrl.showLobby();
        gameLoopService.startGame();

        mainCtrl.getPlayer().setJoker1(true);
        mainCtrl.getPlayer().setJoker2(true);
        mainCtrl.getPlayer().setJoker3(true);
        mainCtrl.setJoker3(true);
        mainCtrl.setJoker2(true);
        mainCtrl.setJoker1(true);
    }

    /**
     * Will make a new game on the server and take the user to it
     */
    public void makeNewGame(){
        Player player = mainCtrl.getPlayer();
        MultiPlayerGame game = multiServer.makeNewGame(player);

        player = game.returnUpdatedPlayer(player);
        mainCtrl.setPlayer(player);

        mainCtrl.setGame(game);
        mainCtrl.showLobby();
        gameLoopService.startGame();

        mainCtrl.getPlayer().setJoker1(true);
        mainCtrl.getPlayer().setJoker2(true);
        mainCtrl.getPlayer().setJoker3(true);
        mainCtrl.setJoker3(true);
        mainCtrl.setJoker2(true);
        mainCtrl.setJoker1(true);
    }
}
