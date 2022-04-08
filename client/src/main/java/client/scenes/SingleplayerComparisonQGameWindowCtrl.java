package client.scenes;

import client.utils.ServerUtils;
import commons.questions.ComparisonQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.util.concurrent.ThreadLocalRandom;

public class SingleplayerComparisonQGameWindowCtrl {
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
    private ImageView answerOneImage;

    @FXML
    private ImageView answerTwoImage;

    @FXML
    private ImageView answerThreeImage;

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    private int correctChoice;

    public boolean canAnswer;
    public boolean exited = false;
    public boolean answerClicked = false;

    private int ansOne;
    private int ansTwo;
    private int ansThr;

    @Inject
    public SingleplayerComparisonQGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.timerComparisonLabel = new Label();
        this.mainCtrl = mainCtrl;
    }

    /**
     * Displays a ComparisonQuestion type by feeding the gui elements with the given question's fields
     *
     * Updates the display with the score of the player
     * It adds the texts and images to the buttons
     * It randomised which button holds the correct answer to the question
     *
     * @param cq the question for which the fields needs to be displayed through the gui elements
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

    }


    /**
     * runs when the player clicks on the first button to choose an answer
     *
     * paints the buttons red or green corresponding to which button held the right answer
     * updates the player's score according to whether they chose correctly
     * displays consumptions after answering
     */
    public void checkAnswerOne() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
            if (correctChoice == 1) {
                answerOne.setStyle("-fx-background-color: #3FA53FFF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
                scoreText.setText("Score: " + String.valueOf(mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8));
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
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
            answerOne.setText(answerOne.getText() + "\n" + ansOne);
            answerTwo.setText(answerTwo.getText() + "\n" + ansTwo);
            answerThree.setText(answerThree.getText() + "\n" + ansThr);
        }

    }
    /**
     * runs when the player clicks on the second button to choose an answer
     *
     * paints the buttons red or green corresponding to which button held the right answer
     * updates the player's score according to whether they chose correctly
     * displays consumptions after answering
     */
    public void checkAnswerTwo() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
            if (correctChoice == 1) {
                answerOne.setStyle("-fx-background-color: #3FA53FFF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
            }
            else if (correctChoice == 2){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerTwo.setStyle("-fx-background-color: #3FA53FFF");
                answerThree.setStyle("-fx-background-color: #C44141FF");
                scoreText.setText("Score: " + String.valueOf(mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8));
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
            }
            else if (correctChoice == 3){
                answerOne.setStyle("-fx-background-color: #C44141FF");
                answerTwo.setStyle("-fx-background-color: #C44141FF");
                answerThree.setStyle("-fx-background-color: #3FA53FFF");
            }
            answerOne.setText(answerOne.getText() + "\n" + ansOne);
            answerTwo.setText(answerTwo.getText() + "\n" + ansTwo);
            answerThree.setText(answerThree.getText() + "\n" + ansThr);
        }

    }
    /**
     * runs when the player clicks on the third button to choose an answer
     *
     * paints the buttons red or green corresponding to which button held the right answer
     * updates the player's score according to whether they chose correctly
     * displays consumptions after answering
     */
    public void checkAnswerThree() {
        if (!answerClicked && canAnswer) {
            this.answerClicked = true;
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
                scoreText.setText("Score: " + (mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8));
                mainCtrl.getPlayer().setScore(mainCtrl.getPlayer().getScore()+1000*Integer.parseInt(timerComparisonLabel.getText())/8);
                mainCtrl.getPlayer().incCorrectQNo();
            }
            answerOne.setText(answerOne.getText() + "\n" + ansOne);
            answerTwo.setText(answerTwo.getText() + "\n" + ansTwo);
            answerThree.setText(answerThree.getText() + "\n" + ansThr);
        }

    }

    /**
     * return the player to the main menu
     */
    public void backToMain() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.showMenu();
            exited = true;
        }
    }

    /**
     * refreshes the colours of the answer buttons to default
     */
    public void refreshColors() {
        answerOne.setStyle("-fx-background-color: #0179ff");
        answerThree.setStyle("-fx-background-color: #0179ff");
        answerTwo.setStyle("-fx-background-color: #0179ff");
    }
}
