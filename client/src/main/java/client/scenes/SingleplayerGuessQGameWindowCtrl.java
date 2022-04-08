package client.scenes;

import client.utils.ServerUtils;
import commons.questions.GuessQuestion;

import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class SingleplayerGuessQGameWindowCtrl {
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

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    public int correctAnswerLowerBound;
    public int correctAnswerUpperBound;

    public boolean canAnswer;
    public boolean exited = false;
    public boolean answerClicked = false;


    @Inject
    public SingleplayerGuessQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.timerGuessLabel = new Label();
    }
    public void feedElements(GuessQuestion gq) throws IOException {
        guessText.setText("");
        questionText.setText("How much energy does\n" + gq.getBaseActivity() + "\nconsume?");
        scoreText.setText("Score: " + Integer.toString(mainCtrl.getPlayer().getScore()));
        correctAnswerLowerBound = gq.getCorrectAnswerLowerLim();
        correctAnswerUpperBound = gq.getCorrectAnswerUpperLim();


        Image img = new Image(new ByteArrayInputStream((gq.getBaseAcImage())));
        imageQuestion.setImage(img);
    }

    /**
     * Checks the answer inputted by the user
     * updates the score according to wether the answer was in the boundary limit
     * Paints the textfield according to correctness of the answer
     * Display correct answer
     */
    public void checkAnswer() {
        answerClicked = true;
        int givenAnswer = Integer.parseInt(guessText.getText());
        if (givenAnswer <= correctAnswerUpperBound && givenAnswer >= correctAnswerLowerBound) {
            guessText.setStyle("-fx-background-color: #3FA53FFF");
            mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.valueOf(timerGuessLabel.getText())/8);
            mainCtrl.getPlayer().incCorrectQNo();
            scoreText.setText("Score: " + Integer.toString(mainCtrl.getPlayer().getScore()));
        }
        else {
            guessText.setStyle("-fx-background-color: #C44141FF");
            int correctAns = (correctAnswerLowerBound+correctAnswerUpperBound)/2;
            guessText.setText("Correct: " + correctAns);
        }
    }

    /**
     * return user to the main menu
     * Associated with Leave Game button
     */
    public void backToMain() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.showMenu();
            exited = true;
        }
    }

    /**
     * sets default colour for the textfield
     */
    public void refreshColors() {
        guessText.setStyle("-fx-background-color: #d9d9d9");
    }
}
