package client.scenes;

import client.utils.ServerUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import javax.inject.Inject;

public class SinglePlayerEndWindowCtrl {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    @FXML
    private Button playAgain;

    @FXML
    private Button returnToMenu;

    @FXML
    private Button closeGame;

    @FXML
    private Text scoreText;

    @FXML
    private Text questionNoGood;


    @Inject
    public SinglePlayerEndWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }
    public void playAgain() {
        mainCtrl.showNameChoiceSP();
    }
    public void setScoreText(int score)
    {
        this.scoreText.setText(String.valueOf(score));
    }
    public void setQuestionNoGood(int noGood)
    {
        this.questionNoGood.setText(String.valueOf(noGood));
    }
    public void jumpToMenu() {
        mainCtrl.showMenu();
    }

    public void close() {
        Platform.exit();
    }


}
