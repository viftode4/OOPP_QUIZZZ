package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class LobbyWindowCtrl {

    @FXML
    private Button startgame;

    @FXML
    private Button leave;

    @FXML
    private Label waiting;

    @FXML
    private Label players;

    @FXML
    private Label gameID;

    @FXML
    private ListView playerList;

    private final ServerUtils server;

    private final MainGameCtrl mainCtrl;

    private final MultiplayerUtils multiServer;
    /**

     * @param server
     * @param mainCtrl
     */
    /**
     * This is the constructor method of the controller class for the
     * "Lobby Window"
     * @param server the server
     * @param mainCtrl the MainController
     * @param multiServer the utilities for multiplayer
     */
    @Inject
    public LobbyWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiServer){
        this.server = server;
        this.mainCtrl= mainCtrl;
        this.multiServer = multiServer;

    }

    /**
     * Removes the user from the player list of the game and sets player's gameid to -1, also, takes the user to the
     * name choice screen. Stop the game thread.
     */
    public void leave(){
        try{
            mainCtrl.setPlayer(multiServer.leaveLobby(mainCtrl.getPlayer()));
        }catch(Exception e){
            e.printStackTrace();
        }
        mainCtrl.stopGameThread();
        mainCtrl.showNameChoice();
        System.out.println(Thread.currentThread().getName());
    }

    /**
     * The method that executes when the player clicks the "start game" button. It shows a
     * new multiplayer game window to the user, and the game starts.
     */
    public void start(){
        multiServer.startGame(mainCtrl.getPlayer());
    }

    /**
     * Updates the player list on the screen
     * @param players the list of players in the game
     */
    public void updatePlayerList(List<Player> players) {
        playerList.getItems().clear();

        for (Player x : players) {
            String add = x.getUsername();
            if (mainCtrl.getPlayer().equals(x))
                add += " - YOU";

            playerList.getItems().add(add);
        }
    }

    /**
     * Stops the game
     */
    public void stop(){
        server.stop();
    }

    /**
     * Displays the game ID to the client in the lobby
     * @param gameID the game ID
     */
    public void setGameIDLabel(int gameID) {
        this.gameID.setText("GameID: " + gameID);
    }
}
