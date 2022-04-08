package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.PreciseQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.concurrent.ThreadLocalRandom;

public class SingleplayerPreciseQGameWindowCtrl {

    @FXML
    private Label NoQuestion;

    @FXML
    public Label questionText;

    @FXML
    private Button joker1;

    @FXML
    private Button joker2;

    @FXML
    private Button joker3;

    @FXML
    private Button leaveGame;

    @FXML
    private ImageView imageQuestion;

    @FXML
    public Label timerLabel;

    @FXML
    private Label personalScore;

    @FXML
    public Button answerOne;

    @FXML
    public Button answerTwo;

    @FXML
    public Button answerThree;

    public boolean answerClicked = false;


    private final ServerUtils server;

    private final MainGameCtrl mainCtrl;

    private int correct;
    private int timeLength;
    public boolean canAnswer;
    private int questionNr;
    public boolean exited = false;

    @Inject
    public SingleplayerPreciseQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl){
        this.server = server;
        this.mainCtrl= mainCtrl;
        this.timerLabel = new Label();
        this.canAnswer = true;
    }

    /**
     * This function takes a Precise Question object and sets the information it contains to each element
     * It sets the answers and the corect answer position
     * @param q the Precise Question it needs with all the information
     */
    public void feedElements(PreciseQuestion q) {
        personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        int i = threadLocalRandom.nextInt(3)+1;
        correct = i;

        if (i == 1){
            answerOne.setText(String.valueOf(q.getBaseActivityConsumption()));
            answerTwo.setText(String.valueOf(q.getWrongAnswer1()));
            answerThree.setText(String.valueOf(q.getWrongAnswer2()));
        }
        if(i == 2){
            answerOne.setText(String.valueOf(q.getWrongAnswer1()));
            answerTwo.setText(String.valueOf(q.getBaseActivityConsumption()));
            answerThree.setText(String.valueOf(q.getWrongAnswer2()));
        }
        else{
            answerOne.setText(String.valueOf(q.getWrongAnswer1()));
            answerTwo.setText(String.valueOf(q.getWrongAnswer2()));
            answerThree.setText(String.valueOf(q.getBaseActivityConsumption()));
        }

        questionText.setText("How much energy does the following activity \n\""+q.getBaseActivity()+"\"\n consume?");

        Image img = new Image(new ByteArrayInputStream((q.getBaseAcImage())));
        imageQuestion.setImage(img);
    }

    /**
     * This function is played when you click the answer button one
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void clickAnswerOne() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
            this.timeLength = 0;
            if (correct == 1) {
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerOne.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.valueOf(timerLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
                personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());
            }
            else if (correct == 2){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerTwo.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
            }
            else if (correct == 3){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #3FA53FFF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
            }
        }
    }
    /**
     * This function is played when you click the answer button two
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void clickAnswerTwo() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
            this.timeLength = 0;
            if (correct == 1) {
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerOne.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
            }
            else if (correct == 2){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerTwo.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.valueOf(timerLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
                personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());
            }
            else if (correct == 3){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #3FA53FFF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
            }
        }
    }
    /**
     * This function is played when you click the answer button three
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void clickAnswerThree() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
            this.timeLength = 0;
            if (correct == 1) {
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerOne.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
            }
            else if (correct == 2){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerTwo.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
            }
            else if (correct == 3){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #3FA53FFF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.valueOf(timerLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
                personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());
            }
        }
    }

    /**
     * returns player to the lobby
     */
    public void quitGame(){
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.showMenu();
            exited = true;
        }
    }

    /**
     * called when the all the question have been answered
     * Transitions the player to the SinglePlayerEndGameWindow
     */
    public void endGame() {
        mainCtrl.getSinglePlayerEndWindowCtrl().setScoreText(mainCtrl.getPlayer().getScore());
        mainCtrl.getSinglePlayerEndWindowCtrl().setQuestionNoGood(mainCtrl.getPlayer().getCorrectQNo());
        server.updatePlayerScore(mainCtrl.getPlayer());
        mainCtrl.showSinglePlayerEndWindow();
    }

    /**
     *  Refreshes the elements for the next question
     */
    public void refreshColors() {
        answerOne.setStyle("-fx-background-color: #0179ff");
        answerThree.setStyle("-fx-background-color: #0179ff");
        answerTwo.setStyle("-fx-background-color: #0179ff");
    }
}
