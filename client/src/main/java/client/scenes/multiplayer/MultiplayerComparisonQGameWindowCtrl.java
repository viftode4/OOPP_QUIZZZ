package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.dto.PlayerAnswer;
import commons.questions.ComparisonQuestion;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

public class MultiplayerComparisonQGameWindowCtrl implements Initializable {
    @FXML
    private Button answerOne;

    @FXML
    private Button answerTwo;

    @FXML
    private Button answerThree;

    @FXML
    private Label questionText;

    @FXML
    public Label timerComparisonLabel;

    @FXML
    private Label scoreText;

    @FXML
    private Button joker1;

    @FXML
    private Button joker2;

    @FXML
    private Button joker3;

    @FXML
    private Button leaveGame;

    @FXML
    private ListView reactions;

    @FXML
    private ImageView answerOneImage;

    @FXML
    private ImageView answerTwoImage;

    @FXML
    private ImageView answerThreeImage;


    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final MultiplayerUtils multiServer;

    private int correctChoice;

    public int getChosen() {
        return chosen;
    }

    public void setChosen(int chosen) {
        this.chosen = chosen;
    }

    private int chosen = -1;

    public boolean canAnswer= true;
    public boolean exited = false;
    public boolean answerClicked = false;
    public boolean usedDoublePoints = false;

    private int ansOne;
    private int ansTwo;
    private int ansThr;

    /**
     * Constructor for the MP Comparison Question game window
     * @param server the serverutils
     * @param mainCtrl the main controller
     * @param multiServer the multiplayer utils
     */
    @Inject
    public MultiplayerComparisonQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiServer) {
        this.server = server;
        this.timerComparisonLabel = new Label();
        this.mainCtrl = mainCtrl;
        this.multiServer = multiServer;
        this.joker1 = new Button();
        this.joker2 = new Button();
        this.joker3 = new Button();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
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
     * Loads the elements for the given question into the clientside
     * @param cq the (comparison) question to be displayed
     */
    public void feedElements(ComparisonQuestion cq) {
        scoreText.setText("Score: " + Integer.toString(mainCtrl.getPlayer().getScore()));
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        int i = threadLocalRandom.nextInt(3)+1;
        correctChoice = i;

        if (i == 1){
            ansOne = cq.getBaseActivityConsumption();
            ansTwo = cq.getWrongAcCons1();
            ansThr = cq.getWrongAcCons2();
            answerOne.setText(cq.getBaseActivity());
            answerTwo.setText(cq.getWrongActivityComp1());
            answerThree.setText(cq.getWrongActivityComp2());

            Image img1 = new Image(new ByteArrayInputStream((cq.getBaseAcImage())));
            answerOneImage.setImage(img1);

            Image img2 = new Image(new ByteArrayInputStream((cq.getWrongAcImage1())));
            answerTwoImage.setImage(img2);

            Image img3 = new Image(new ByteArrayInputStream((cq.getWrongAcImage2())));
            answerThreeImage.setImage(img3);
        }
        else if(i == 2){
            ansOne = cq.getWrongAcCons1();
            ansTwo = cq.getBaseActivityConsumption();
            ansThr = cq.getWrongAcCons2();
            answerOne.setText(cq.getWrongActivityComp1());
            answerTwo.setText(cq.getBaseActivity());
            answerThree.setText(cq.getWrongActivityComp2());

            Image img1 = new Image(new ByteArrayInputStream((cq.getWrongAcImage1())));
            answerOneImage.setImage(img1);

            Image img2 = new Image(new ByteArrayInputStream((cq.getBaseAcImage())));
            answerTwoImage.setImage(img2);

            Image img3 = new Image(new ByteArrayInputStream((cq.getWrongAcImage2())));
            answerThreeImage.setImage(img3);
        }
        else{
            ansOne = cq.getWrongAcCons1();
            ansTwo = cq.getWrongAcCons2();
            ansThr = cq.getBaseActivityConsumption();
            answerOne.setText(cq.getWrongActivityComp1());
            answerTwo.setText(cq.getWrongActivityComp2());
            answerThree.setText(cq.getBaseActivity());

            Image img1 = new Image(new ByteArrayInputStream((cq.getWrongAcImage1())));
            answerOneImage.setImage(img1);

            Image img2 = new Image(new ByteArrayInputStream((cq.getWrongAcImage2())));
            answerTwoImage.setImage(img2);

            Image img3 = new Image(new ByteArrayInputStream((cq.getBaseAcImage())));
            answerThreeImage.setImage(img3);
        }
        questionText.setText("Which activity takes the most energy?");

        ImageView joker1image = new ImageView();
        joker1image.setImage(new Image("@../../assets/2x_joker.png"));
        joker1image.setFitWidth(100);
        joker1image.setFitHeight(150);
        this.joker1.setGraphic(joker1image);


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
        this.joker3.setGraphic(joker3image);
        if (mainCtrl.isJoker3()) {
            this.joker3.setGraphic(joker3image);
        }
        else if (!mainCtrl.isJoker3()) {
            ImageView flipImage = new ImageView();
            flipImage.setImage(new Image("@../../assets/card_back.png"));
            flipImage.setFitWidth(100);
            flipImage.setFitHeight(150);

            this.joker3.setGraphic(flipImage);
        }

    }
    public void showAnswers() {
        if (correctChoice == 1) {
            answerOne.setStyle("-fx-background-color: #3FA53FFF");
            answerTwo.setStyle("-fx-background-color: #C44141FF");
            answerThree.setStyle("-fx-background-color: #C44141FF");
        }
        else if (correctChoice == 2){
            answerOne.setStyle("-fx-background-color: #C44141FF");
            answerTwo.setStyle("-fx-background-color: #3FA53FFF");
            answerThree.setStyle("-fx-background-color: #C44141FF");
        }
        else if (correctChoice == 3){
            answerOne.setStyle("-fx-background-color: #C44141FF");
            answerTwo.setStyle("-fx-background-color: #C44141FF");
            answerThree.setStyle("-fx-background-color: #3FA53FFF");
        }
        scoreText.setText("Score: " + (mainCtrl.getPlayer().getScore()));
    }
    public void checkAnswerOne() {
        //we first send the answer to the server and locally we do the fancy stuff
        if (!answerClicked) {
            System.out.println("pressed answer one");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansOne, Math.toIntExact(mainCtrl.getGame().getGameId())));
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansOne, Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            chosen = 1;
            answerOne.setStyle("-fx-text-fill: #45007A");
            if (correctChoice == 1) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
            usedDoublePoints = false;
        }

    }

    /**
     * If answer 2 is clicked, checks correctness and awards points based on this
     */
    public void checkAnswerTwo() {
        if (!answerClicked) {
            System.out.println("pressed answer two");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansTwo, Math.toIntExact(mainCtrl.getGame().getGameId())));
            chosen = 2;
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansTwo, Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            answerTwo.setStyle("-fx-text-fill: #45007A");
            if (correctChoice == 2) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
            usedDoublePoints = false;
        }

    }

    /**
     * If answer 3 is clicked, checks correctness and awards points based on this
     */
    public void checkAnswerThree() {

        if (!answerClicked) {
            System.out.println("pressed answer three");
            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansThr, Math.toIntExact(mainCtrl.getGame().getGameId())));
            chosen =3;
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), ansThr, Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            this.answerClicked = true;
            answerThree.setStyle("-fx-text-fill: #45007A");
            if (correctChoice == 3) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
            usedDoublePoints = false;

        }

    }

    /**
     * Returns the user to the main menu
     */
    public void backToMain() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.stopGameThread();
            mainCtrl.showMenu();
            exited = true;
        }
    }

    /**
     * Resets the colors for answer option
     */
    public void refreshColors() {
        answerClicked = false;
        answerOne.setStyle("-fx-background-color: #0179ff");
        answerThree.setStyle("-fx-background-color: #0179ff");
        answerTwo.setStyle("-fx-background-color: #0179ff");
    }
    /**
     * feeds the timervalue from game into the gui, also the round number
     */
    public void displayTime(){
        timerComparisonLabel.setText("Round: "+(1+mainCtrl.getMPgame().getRoundNumber())+ " Time : "+
                (int) Math.floor(mainCtrl.getMPgame().getTimervalue()));
    }
    public void displayAnswerText(){
        timerComparisonLabel.setText("Round: "+(1+mainCtrl.getMPgame().getRoundNumber())+ " Answers");
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
            if (correctChoice == 1) {
                int pick = r.nextInt(2);
                if (pick == 0) {
                    answerThree.setStyle("-fx-background-color: #E6A972");
                    answerThree.setText("Not right");
                }
                if (pick == 1) {
                    answerTwo.setStyle("-fx-background-color: #E6A972");
                    answerTwo.setText("Not right");
                }
            }
            if (correctChoice == 2) {
                int pick = r.nextInt(2);
                if (pick == 0) {
                    answerOne.setStyle("-fx-background-color: #E6A972");
                    answerOne.setText("Not right");
                }
                if (pick == 1) {
                    answerThree.setStyle("-fx-background-color: #E6A972");
                    answerThree.setText("Not right");
                }
            }
            if (correctChoice == 3) {
                int pick = r.nextInt(2);
                if (pick == 0) {
                    answerOne.setStyle("-fx-background-color: #E6A972");
                    answerOne.setText("Not right");
                }
                if (pick == 1) {
                    answerTwo.setStyle("-fx-background-color: #E6A972");
                    answerTwo.setText("Not right");
                }
            }
        }
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
}
