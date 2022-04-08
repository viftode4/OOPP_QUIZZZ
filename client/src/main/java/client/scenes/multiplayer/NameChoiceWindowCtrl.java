package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.ServerUtils;

import commons.Player;
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

public class NameChoiceWindowCtrl implements Initializable {
    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    @FXML
    private Button enterButton;
    @FXML
    private Label gameLabel;
    @FXML
    private Label numberWaiting;
    @FXML
    private TextField usernameEntry;
    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private Button backToMain;
    @FXML
    private Label message;

    /**
     * This is constructor method for the controller class of the name choice window
     * where the player is prompted to enter a username by which they are identified
     * @param server
     * @param mainCtrl
     */
    @Inject
    public NameChoiceWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Sets some properties for the page, such as some image content, upon
     * execution of the program
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.left.setImage(new Image("@../../assets/filler1.png"));
        this.right.setImage(new Image("@../../assets/filler1.png"));
    }

    /**
     * takes the user to the main menu
     */
    public void jumpBack() {
        mainCtrl.showMenu();
    }


    /**
     * will take the user to the lobby choice window if the username that the user choose is not null, else, it will
     * will take the user to the name choice screen and tell the user to enter a username
     */
    public void jumpToLobbyChoice(){
        Player player = new Player();

        //we should/can make this feature work with white spaces too
        if(usernameEntry.getText().equals("")){
            message.setText("Please enter a username");
            return;
        }
        player.setScore(0);
        message.setText("");
        player.setUsername(usernameEntry.getText());
        mainCtrl.setPlayer(player);
        mainCtrl.showLobbyChoice();
    }

    /**
     * @return returns the label message
     */
    public Label getMessage() {
        return message;
    }
}
