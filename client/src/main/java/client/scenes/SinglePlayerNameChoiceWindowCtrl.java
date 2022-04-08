package client.scenes;

import client.utils.ServerUtils;
import commons.Player;
import commons.SinglePlayerGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

;

public class SinglePlayerNameChoiceWindowCtrl implements Initializable {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final SingleplayerPreciseQGameWindowCtrl preciseQGameWindowCtrl;
    private final SingleplayerGuessQGameWindowCtrl guessQGameWindowCtrl;
    private final SingleplayerComparisonQGameWindowCtrl comparisonQGameWindowCtrl;
    private final WindowTimer timer;
    private MediaPlayer player;

    @FXML
    private Button startSPGameButton;
    @FXML
    private TextField usernameEntryTextField;
    @FXML
    private Button backToMainButton;
    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private ImageView egg;
    @FXML
    private Label message;

    boolean eggFlag = false;
    @Inject
    public SinglePlayerNameChoiceWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, SingleplayerPreciseQGameWindowCtrl preciseQGameWindowCtrl,
                                            SingleplayerGuessQGameWindowCtrl guessQGameWindowCtrl,
                                            SingleplayerComparisonQGameWindowCtrl comparisonQGameWindowCtrl,
                                            WindowTimer timer) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.preciseQGameWindowCtrl = preciseQGameWindowCtrl;
        this.comparisonQGameWindowCtrl = comparisonQGameWindowCtrl;
        this.guessQGameWindowCtrl = guessQGameWindowCtrl;
        this.timer = timer;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.left.setImage(new Image("@../../assets/filler1.png"));
        this.right.setImage(new Image("@../../assets/filler1.png"));
        this.egg.setImage(new Image("@../../assets/sus.jpeg"));
        this.egg.setVisible(false);
    }

    public void jumpToMenu() {
        mainCtrl.showMenu();
        refresh();
    }

    private void refresh() {
        eggFlag = false;
        this.egg.setVisible(false);
    }


    public void startSPGame() throws IOException {
        //we should/can make this feature work with white spaces too
        boolean ok = false;
        if(usernameEntryTextField.getText().equals("")){
            message.setText("Please enter a username");
            return;
        }
        else if(usernameEntryTextField.getText().equalsIgnoreCase("sus") && !eggFlag){
            this.egg.setVisible(true);
            eggFlag = true;
            //Wonky way of getting the absolute path correctly using relative stuff
            String path = getClass().getResource("@../../").toExternalForm();
            path = path.substring(0,path.indexOf("/client")+7);
            path = path + "/src/main/resources/assets/sound/sus.mp3";
            Media fx = new Media(path);
            player = new MediaPlayer(fx);
            player.play();
            return;
        }
        message.setText("");
        Player player = new Player();
        player.setUsername(usernameEntryTextField.getText());
        server.addSinglePlayer(player);
        mainCtrl.setPlayerSP(player);
        mainCtrl.setGame(new SinglePlayerGame());
        mainCtrl.getGame().setGameId((long) player.getGame());
        mainCtrl.setRoundList(server.pullActivities());
        for (int i = 0; i < mainCtrl.getRoundList().size(); i++) {
            mainCtrl.getRoundList().get(i).setGame(mainCtrl.getGame());
        }
        refresh();
        timer.showNextQuestion(0);
    }

}
