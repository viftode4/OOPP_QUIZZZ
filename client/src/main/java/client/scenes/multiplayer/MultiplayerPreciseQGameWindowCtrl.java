package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.dto.PlayerAnswer;
import commons.questions.PreciseQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class MultiplayerPreciseQGameWindowCtrl implements Initializable {

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

    @FXML
    private ListView reactions;

    public boolean answerClicked = false;


    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final MultiplayerUtils multiServer;

    private int correct;

    public int getChosen() {
        return chosen;
    }

    public void setChosen(int chosen) {
        this.chosen = chosen;
    }

    private int chosen = 1;
    private int timeLength;
    public boolean canAnswer;
    private int questionNr;
    public boolean exited = false;
    private boolean usedDoublePoints;

    @Inject
    public MultiplayerPreciseQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiServer){
        this.server = server;
        this.mainCtrl= mainCtrl;
        this.timerLabel = new Label();
        this.canAnswer = true;
        this.multiServer = multiServer;
        this.joker1 = new Button();
        this.joker2 = new Button();
        this.joker3 = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.reactions.setVisible(false);
        this.reactions.setVisible(false);
    }

    /**
     * getter for the joker 1 button
     * @return returns the button joker1
     */
    public Button getJoker1() {
        return joker1;
    }

    /**
     * setter for the joker 1 button
     * @param joker1 the button that will be set
     */
    public void setJoker1(Button joker1) {
        this.joker1 = joker1;
    }

    /**
     * getter for the joker 2 button
     * @return returns the button joker2
     */
    public Button getJoker2() {
        return joker2;
    }

    /**
     * setter for the joker 2 button
     * @param joker2 the button that will be set
     */
    public void setJoker2(Button joker2) {
        this.joker2 = joker2;
    }

    /**
     * getter for the joker 3 button
     * @return returns the button joker3
     */
    public Button getJoker3() {
        return joker3;
    }


    /**
     * setter for the joker 3 button
     * @param joker3 the button that will be set
     */
    public void setJoker3(Button joker3) {
        this.joker3 = joker3;
    }

    /**
     * This function takes a Precise Question object and sets the information it contains to each element
     * It sets the answers and the corect answer position
     * @param q the Precise Question it needs with all the information
     */
    public void feedElements(PreciseQuestion q) {
        personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());

        Random rnd = new Random();
        int i = rnd.nextInt(3)+1;

        correct = i;
        System.out.println(" The correct answer is supposed to be at button : " + correct);
        if (i == 1){
            this.answerOne.setText(String.valueOf(q.getBaseActivityConsumption()));
            this.answerTwo.setText(String.valueOf(q.getWrongAnswer1()));
            this.answerThree.setText(String.valueOf(q.getWrongAnswer2()));
        }
        else if(i == 2){
            this.answerOne.setText(String.valueOf(q.getWrongAnswer1()));
            this.answerTwo.setText(String.valueOf(q.getBaseActivityConsumption()));
            this.answerThree.setText(String.valueOf(q.getWrongAnswer2()));
        }
        else{
            this.answerOne.setText(String.valueOf(q.getWrongAnswer1()));
            this.answerTwo.setText(String.valueOf(q.getWrongAnswer2()));
            this.answerThree.setText(String.valueOf(q.getBaseActivityConsumption()));
        }

        questionText.setText("How much energy does the following activity \""+q.getBaseActivity()+"\" consume?");

        Image img = new Image(new ByteArrayInputStream((q.getBaseAcImage())));
        imageQuestion.setImage(img);

        ImageView joker1image = new ImageView();
        joker1image.setImage(new Image("@../../assets/2x_joker.png"));
        joker1image.setFitWidth(100);
        joker1image.setFitHeight(150);
        if (mainCtrl.isJoker1()) {
            this.joker1.setGraphic(joker1image);
        }
        else if (!mainCtrl.isJoker1()) {
            ImageView flipImage = new ImageView();
            flipImage.setImage(new Image("@../../assets/card_back.png"));
            flipImage.setFitWidth(100);
            flipImage.setFitHeight(150);

            this.joker1.setGraphic(flipImage);
        }
        ImageView joker2image = new ImageView();
        joker2image.setImage(new Image("@../../assets/answer_joker.png"));
        joker2image.setFitWidth(100);
        joker2image.setFitHeight(150);
        if (mainCtrl.isJoker2()) {
            this.joker2.setGraphic(joker2image);
        }
        else if (!mainCtrl.isJoker2()) {
            ImageView flipImage = new ImageView();
            flipImage.setImage(new Image("@../../assets/card_back.png"));
            flipImage.setFitWidth(100);
            flipImage.setFitHeight(150);

            this.joker2.setGraphic(flipImage);
        }
        ImageView joker3image = new ImageView();
        joker3image.setImage(new Image("@../../assets/speed_joker.png"));
        joker3image.setFitWidth(100);
        joker3image.setFitHeight(150);
        if (mainCtrl.isJoker3()) {
            this.joker3.setGraphic(joker3image);
        }
        else{
            ImageView flipImage = new ImageView();
            flipImage.setImage(new Image("@../../assets/card_back.png"));
            flipImage.setFitWidth(100);
            flipImage.setFitHeight(150);
            this.joker3.setGraphic(flipImage);
        }
    }

    /**
     * This function is played when you click the answer button one
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void showAnswers() {
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
        }
        personalScore.setText("Score: " + mainCtrl.getPlayer().getScore());
    }
    public void clickAnswerOne() {
        if (!answerClicked) {
            System.out.println("pressed answer one");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerOne.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            //if 2xJoker used, send it again
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerOne.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            this.timeLength = 0;
            answerOne.setStyle("-fx-text-fill: #45007A");
            chosen = 1;
            if (correct == 1) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }

        }
    }
    /**
     * This function is played when you click the answer button two
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void clickAnswerTwo() {

        if (!answerClicked) {
            System.out.println("pressed answer two");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerTwo.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            //if 2xJoker used, send it again
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerTwo.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            this.timeLength = 0;
            answerTwo.setStyle("-fx-text-fill: #45007A");
            chosen = 2;
            if (correct == 2) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
        }
    }
    /**
     * This function is played when you click the answer button three
     * If it s the correct one then it increases the score
     * It also makes it so that you cannot spam the button and can only answer once
     */
    public void clickAnswerThree() {

        if (!answerClicked) {
            System.out.println("pressed answer three");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerThree.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), Integer.parseInt(answerThree.getText()), Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            this.timeLength = 0;
            answerThree.setStyle("-fx-text-fill: #45007A");
            chosen = 3;
            if (correct == 3) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
        }
    }

    /**
     * Make the player leave the game
     */
    public void quitGame(){
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.stopGameThread();
            mainCtrl.showMenu();
            exited = true;
        }
    }

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
        this.answerClicked = false;
        answerOne.setStyle("-fx-background-color: #0179ff");
        answerThree.setStyle("-fx-background-color: #0179ff");
        answerTwo.setStyle("-fx-background-color: #0179ff");
    }
    /**
     * feeds the timervalue from game into the gui, also the round number
     */
    public void displayTime(){
        timerLabel.setText("Round: "+(mainCtrl.getMPgame().getRoundNumber() + 1) + " Time : "+
                (int) Math.floor(mainCtrl.getMPgame().getTimervalue()));
    }
    public void displayAnswerText(){
        timerLabel.setText("Round: "+(1+mainCtrl.getMPgame().getRoundNumber())+ " Answers");
    }

    /**
     * Functionality of the clairvoyance joker (joker 2) when clicked
     */
    public void clairvoyance() {
        if (mainCtrl.isJoker2()) {
            mainCtrl.setJoker2(false);
            mainCtrl.getPlayer().setJoker2(false);

            ImageView joker2image = new ImageView();
            joker2image.setImage(new Image("@../../assets/card_back.png"));
            joker2image.setFitWidth(100);
            joker2image.setFitHeight(150);
            this.joker2.setGraphic(joker2image);

            Random r = new Random();
            if (correct == 1) {
                System.out.println("1 is right");
                int pick = r.nextInt(1);
                if (pick == 0) {
                    answerThree.setStyle("-fx-background-color: #E6A972");
                    answerThree.setText(answerThree.getText() + " is not right");
                }
                if (pick == 1) {
                    answerTwo.setStyle("-fx-background-color: #E6A972");
                    answerTwo.setText(answerTwo.getText() + " is not right");
                }
            }
            if (correct == 2) {
                System.out.println("2 is right");
                int pick = r.nextInt(2);
                if (pick == 0) {
                    answerOne.setStyle("-fx-background-color: #E6A972");
                    answerOne.setText(answerOne.getText() + " is not right");
                }
                if (pick == 1) {
                    answerThree.setStyle("-fx-background-color: #E6A972");
                    answerThree.setText(answerThree.getText() + " is not right");
                }
            }
            if (correct == 3) {
                System.out.println("3 is right");
                int pick = r.nextInt(2);
                if (pick == 0) {
                    answerOne.setStyle("-fx-background-color: #E6A972");
                    answerOne.setText(answerOne.getText() + " is not right");
                }
                if (pick == 1) {
                    answerTwo.setStyle("-fx-background-color: #E6A972");
                    answerTwo.setText(answerTwo.getText() + " is not right");
                }
            }
        }
    }

    /**
     * Triggered when a player used a double points joker
     */
    public void doubleScore() {
        if(mainCtrl.isJoker1()) {
            System.out.println("Joker successfully used!");
            usedDoublePoints = true;
            mainCtrl.setJoker1(false);
            mainCtrl.getPlayer().setJoker1(false);

            ImageView flippedImage = new ImageView();
            flippedImage.setImage(new Image("@../../assets/card_back.png"));
            flippedImage.setFitWidth(100);
            flippedImage.setFitHeight(150);
            this.joker1.setGraphic(flippedImage);
        }
        else{
            System.out.println("You already used this joker this game.");
        }
    }

    /**
     * Method for confirming whether the player has used the double points joker this round
     * @return whether this is the case
     */
    public boolean usedDoublePoints() {
        if(usedDoublePoints) {
            usedDoublePoints = false;
            System.out.println("Double points have been used!");
            return true;
        }
        return false;
    }

    /**
     * Functionality of the haste joker (joker 3) when clicked
     */
    public void haste(){
        if(mainCtrl.isJoker3()){
            mainCtrl.setJoker3(false);
            mainCtrl.getPlayer().setJoker3(false);

            ImageView joker3image = new ImageView();
            joker3image.setImage(new Image("@../../assets/card_back.png"));
            joker3image.setFitWidth(100);
            joker3image.setFitHeight(150);
            this.joker3.setGraphic(joker3image);
            multiServer.haste(mainCtrl.getMPgame(), mainCtrl.getPlayer());
        }
    }


}
