package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.dto.PlayerAnswer;
import commons.questions.GuessQuestion;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerGuessQGameWindowCtrl implements Initializable {
    @FXML
    private Button enter;

    @FXML
    private TextField guessText;

    @FXML
    private Label questionText;

    @FXML
    private Label scoreText;

    @FXML
    private ImageView imageQuestion;

    @FXML
    private Button joker1;



    @FXML
    public Label timerGuessLabel;

    @FXML
    private Button joker2;

    @FXML
    private Button joker3;

    @FXML
    private Button leaveGame;

    @FXML
    private ListView reactions;


    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final MultiplayerUtils multiServer;

    public int correctAnswerLowerBound;
    public int correctAnswerUpperBound;

    public boolean canAnswer;
    public boolean exited = false;
    public boolean answerClicked = false;
    public boolean usedDoublePoints;


    @Inject
    public MultiplayerGuessQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiServer) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.timerGuessLabel = new Label();
        this.multiServer = multiServer;
        guessText = new TextField();
        this.joker1 = new Button();
        this.joker2 = new Button();
        this.joker3 = new Button();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources){

        this.reactions.setVisible(false);
        ImageView joker1image = new ImageView();
        joker1image.setImage(new Image("@../../assets/2x_joker.png"));
        joker1image.setFitWidth(100);
        joker1image.setFitHeight(150);
        this.joker1.setGraphic(joker1image);

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
     * @param gq the (guess) question to be displayed
     */
    public void feedElements(GuessQuestion gq) {
        guessText.setText("");

        questionText.setText("How much energy does \"" + gq.getBaseActivity() + "\" consume?");
        scoreText.setText("Score: " + Integer.toString(mainCtrl.getPlayer().getScore()));
        correctAnswerLowerBound = gq.getCorrectAnswerLowerLim();
        correctAnswerUpperBound = gq.getCorrectAnswerUpperLim();

        Image img = new Image(new ByteArrayInputStream((gq.getBaseAcImage())));
        imageQuestion.setImage(img);

        ImageView cardBack = new ImageView();
        cardBack.setImage(new Image("@../../assets/card_back.png"));
        cardBack.setFitWidth(100);
        cardBack.setFitHeight(150);

        ImageView joker1image = new ImageView();
        joker1image.setImage(new Image("@../../assets/2x_joker.png"));
        joker1image.setFitWidth(100);
        joker1image.setFitHeight(150);
        if (mainCtrl.isJoker1()) {
            this.joker1.setGraphic(joker1image);
        }
        else if (!mainCtrl.isJoker1()) {
            this.joker1.setGraphic(cardBack);
        }
        ImageView joker2image = new ImageView();
        joker2image.setImage(new Image("@../../assets/card_back.png"));
        joker2image.setFitWidth(100);
        joker2image.setFitHeight(150);
        this.joker2.setGraphic(joker2image); // the joker 2 won't be available to use for quess questions
        this.joker2.setPrefHeight(150);
        this.joker2.setPrefWidth(100);

        ImageView joker3image = new ImageView();
        joker3image.setImage(new Image("@../../assets/speed_joker.png"));
        joker3image.setFitWidth(100);
        joker3image.setFitHeight(150);
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
    public void joker1press(){
        Platform.runLater(()->{
            System.out.println("joker 1 works");
            //mainCtrl.incrementJokers();
        });

    }
    public void showAnswer() {
        int givenAnswer = Integer.MIN_VALUE;
        try{
            givenAnswer = Integer.parseInt(guessText.getText());
        }catch (Exception e){

        }

        if (givenAnswer <= correctAnswerUpperBound && givenAnswer >= correctAnswerLowerBound) {
            guessText.setStyle("-fx-background-color: #3FA53FFF");
            scoreText.setText("Score: " + Integer.toString(mainCtrl.getPlayer().getScore()));
        }
        else {
            int correctAns = (correctAnswerLowerBound+correctAnswerUpperBound)/2;
            guessText.setText("Correct: " + correctAns);
            guessText.setStyle("-fx-background-color: #C44141FF");
        }
    }

    /**
     * Checks whether the question was answered correctly, and awards points accordingly
     */
    public void checkAnswer() {
        if (!answerClicked) {
            System.out.println("pressed answer");
            answerClicked = true;
            int givenAnswer = Integer.MIN_VALUE;
            try{
                givenAnswer = Integer.parseInt(guessText.getText());
            }catch(Exception e){

            }

            multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), givenAnswer, Math.toIntExact(mainCtrl.getGame().getGameId())));
            //if 2xJoker used, send it again
            if(usedDoublePoints()) {
                multiServer.sendAnswer(new PlayerAnswer(mainCtrl.getPlayer(), givenAnswer, Math.toIntExact(mainCtrl.getGame().getGameId())));
            }
            guessText.setStyle("-fx-text-fill: #45007A");
            if (givenAnswer <= correctAnswerUpperBound && givenAnswer >= correctAnswerLowerBound) {
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore());
                mainCtrl.getPlayer().incCorrectQNo();
            }
            usedDoublePoints = false;
        }
    }

    /**
     * Returns to main menu
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
     * Refreshes the color of the answer box
     */
    public void refreshColors() {
        this.answerClicked = false;
        guessText.setStyle("-fx-background-color: #d9d9d9");
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
     * feeds the timervalue from game into the gui, also the round number
     */
    public void displayTime(){
        timerGuessLabel.setText("Round: "+(mainCtrl.getMPgame().getRoundNumber() + 1) + " Time : "+
                (int) Math.floor(mainCtrl.getMPgame().getTimervalue()));

    }
    public void displayAnswerText(){
        timerGuessLabel.setText("Round: "+(1+mainCtrl.getMPgame().getRoundNumber())+ " Answers");
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
