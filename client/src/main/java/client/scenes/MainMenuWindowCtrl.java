package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javax.inject.Inject;


import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuWindowCtrl implements Initializable {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    @FXML
    private Label gameLabel;
    @FXML
    private Button multiPlayer;
    @FXML
    private Button singlePlayer;
    @FXML
    private Button howToPlay;
    @FXML
    private Button pastGames;
    @FXML
    private ImageView title;
    @FXML
    private GridPane layout;
    @FXML
    private AnchorPane window;
    @FXML
    private VBox titlePane;
    @FXML
    private Button adminInterface;


    /**
     * This is the constructor for the controller class of the main
     * menu window
     * @param server
     * @param mainCtrl
     */
    @Inject
    public MainMenuWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.title = new javafx.scene.image.ImageView();
    }

    /**
     * This method sets some style features for the menu window upon
     * execution of the program
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.title.setImage(new Image("@../../assets/title.png"));
        this.title.fitWidthProperty().bind(titlePane.widthProperty());
        this.title.fitHeightProperty().bind(titlePane.heightProperty());
        this.title.setPreserveRatio(true);
    }

    /**
     * takes the user to the name choice screen
     */
    public void jumpToNameChoice() {
        mainCtrl.showNameChoice();
    }

    /**
     * takes the user to the name choice screen for the singleplayer game
     */
    public void jumpToNameChoiceSP() {
        mainCtrl.showNameChoiceSP();
    }

    /**
     * takes the user to the how to play screen
     */
    public void jumpToHowToPlay() {
        mainCtrl.jumpToHowToPlay();
    }

    /**
     * takes teh user to the past games/leaderboard screen
     */
    public void showPastGames() {
        mainCtrl.updatePastGames();
        mainCtrl.showPastGames();
    }

    /**
     * takes the user to the admin interface window
     */
    public void showAdminInterface(){
        mainCtrl.showAdminInterfaceWindow();
    }
}
