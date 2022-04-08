package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.questions.ComparisonQuestion;
import commons.questions.GuessQuestion;
import commons.questions.PreciseQuestion;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class WindowTimer {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final SingleplayerPreciseQGameWindowCtrl singleplayerPreciseQGameWindowCtrl;
    private final SingleplayerGuessQGameWindowCtrl singleplayerGuessQGameWindowCtrl;
    private final SingleplayerComparisonQGameWindowCtrl singleplayerComparisonQGameWindowCtrl;
    private int timeLength;
    private int questionNr;
    private String questionType;

    /**
     * Constructor method for the window timer for the single player interface,
     * which injects instances of 3 different types of question windows, which are
     * precise question, quess question, and comparison question
     * @param server
     * @param mainCtrl
     * @param singleplayerPreciseQGameWindowCtrl
     * @param singleplayerComparisonQGameWindowCtrl
     * @param singleplayerGuessQGameWindowCtrl
     */
    @Inject
    public WindowTimer(ServerUtils server,
                       MainGameCtrl mainCtrl,
                       SingleplayerPreciseQGameWindowCtrl singleplayerPreciseQGameWindowCtrl,
                       SingleplayerComparisonQGameWindowCtrl singleplayerComparisonQGameWindowCtrl,
                       SingleplayerGuessQGameWindowCtrl singleplayerGuessQGameWindowCtrl){
        this.server = server;
        this.mainCtrl= mainCtrl;
        this.singleplayerPreciseQGameWindowCtrl = singleplayerPreciseQGameWindowCtrl;
        this.questionNr = 1;
        this.singleplayerComparisonQGameWindowCtrl = singleplayerComparisonQGameWindowCtrl;
        this.singleplayerGuessQGameWindowCtrl = singleplayerGuessQGameWindowCtrl;
    }

    /**
     * Starts a timer for a question
     * @param duration how long the timer should last
     */
    public void startTimer(int duration) {
        this.timeLength = duration;
        Timer timer = new Timer();
        singleplayerPreciseQGameWindowCtrl.exited = false;
        singleplayerGuessQGameWindowCtrl.exited = false;
        singleplayerComparisonQGameWindowCtrl.exited = false;
        singleplayerPreciseQGameWindowCtrl.timerLabel.setText(Integer.toString(timeLength));
        singleplayerGuessQGameWindowCtrl.timerGuessLabel.setText(Integer.toString(timeLength));
        singleplayerComparisonQGameWindowCtrl.timerComparisonLabel.setText((Integer.toString(timeLength)));

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeLength--;
                if(singleplayerPreciseQGameWindowCtrl.exited) {
                    timer.cancel();
                    questionNr = 0;
                    singleplayerPreciseQGameWindowCtrl.answerClicked = false;
                    singleplayerPreciseQGameWindowCtrl.canAnswer = true;
                    singleplayerGuessQGameWindowCtrl.answerClicked = false;
                    singleplayerComparisonQGameWindowCtrl.answerClicked = false;
                }
                if(singleplayerGuessQGameWindowCtrl.exited) {
                    timer.cancel();
                    questionNr = 0;
                    singleplayerPreciseQGameWindowCtrl.answerClicked = false;
                    singleplayerPreciseQGameWindowCtrl.canAnswer = true;
                    singleplayerGuessQGameWindowCtrl.answerClicked = false;
                    singleplayerComparisonQGameWindowCtrl.answerClicked = false;
                }
                if(singleplayerComparisonQGameWindowCtrl.exited) {
                    timer.cancel();
                    questionNr = 0;
                    singleplayerPreciseQGameWindowCtrl.answerClicked = false;
                    singleplayerPreciseQGameWindowCtrl.canAnswer = true;
                    singleplayerGuessQGameWindowCtrl.answerClicked = false;
                    singleplayerComparisonQGameWindowCtrl.answerClicked = false;
                }
                if(timeLength > 0){
                    Platform.runLater(() -> singleplayerPreciseQGameWindowCtrl.timerLabel.setText(Integer.toString(timeLength)));
                    Platform.runLater(() -> singleplayerGuessQGameWindowCtrl.timerGuessLabel.setText(Integer.toString(timeLength)));
                    Platform.runLater(() -> singleplayerComparisonQGameWindowCtrl.timerComparisonLabel.setText(Integer.toString(timeLength)));
                }
                if(timeLength <= 0) {
                    Platform.runLater(() -> singleplayerPreciseQGameWindowCtrl.timerLabel.setText("Time's up!\n Continue in:\n" + (5+timeLength)));
                    Platform.runLater(() -> singleplayerGuessQGameWindowCtrl.timerGuessLabel.setText("Time's up!\n Continue in:\n" + (5+timeLength)));
                    Platform.runLater(() -> singleplayerComparisonQGameWindowCtrl.timerComparisonLabel.setText("Time's up!\n Continue in:\n" + (5+timeLength)));
                    singleplayerPreciseQGameWindowCtrl.canAnswer = false;
                    singleplayerGuessQGameWindowCtrl.canAnswer = false;
                    singleplayerComparisonQGameWindowCtrl.canAnswer = false;
                }
                if(singleplayerPreciseQGameWindowCtrl.answerClicked == true) {
                    Platform.runLater(() -> singleplayerPreciseQGameWindowCtrl.timerLabel.setText("Breathe"));
                    singleplayerPreciseQGameWindowCtrl.canAnswer = false;
                    singleplayerGuessQGameWindowCtrl.canAnswer = false;
                    singleplayerComparisonQGameWindowCtrl.canAnswer = false;
                }
                if(singleplayerGuessQGameWindowCtrl.answerClicked == true) {
                    Platform.runLater(() -> singleplayerGuessQGameWindowCtrl.timerGuessLabel.setText("Breathe"));
                    singleplayerPreciseQGameWindowCtrl.canAnswer = false;
                    singleplayerGuessQGameWindowCtrl.canAnswer = false;
                    singleplayerComparisonQGameWindowCtrl.canAnswer = false;
                }
                if(singleplayerComparisonQGameWindowCtrl.answerClicked == true) {
                    Platform.runLater(() -> singleplayerComparisonQGameWindowCtrl.timerComparisonLabel.setText("Breathe"));
                    singleplayerPreciseQGameWindowCtrl.canAnswer = false;
                    singleplayerGuessQGameWindowCtrl.canAnswer = false;
                    singleplayerComparisonQGameWindowCtrl.canAnswer = false;
                }
                //change these parameters if necessary, they were sort of arbitrarily chosen.
                if(timeLength == -2 && questionNr == 19) {
                    questionNr = 1;
                    System.out.println("Ending game...");
                    timer.cancel();
                    Platform.runLater(() -> singleplayerPreciseQGameWindowCtrl.endGame());
                    singleplayerPreciseQGameWindowCtrl.answerClicked = false;
                    singleplayerPreciseQGameWindowCtrl.canAnswer = true;
                    singleplayerGuessQGameWindowCtrl.answerClicked = false;
                    singleplayerComparisonQGameWindowCtrl.answerClicked = false;
                }
                else if(timeLength == -2 && questionNr < 20) {
                    ++questionNr;
                    System.out.println("Up next: question " + questionNr);
                    timer.cancel();
                    Platform.runLater(() -> {
                        try {
                            showNextQuestion(questionNr);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    singleplayerPreciseQGameWindowCtrl.answerClicked = false;
                    singleplayerPreciseQGameWindowCtrl.canAnswer = true;
                    singleplayerGuessQGameWindowCtrl.answerClicked = false;
                    singleplayerComparisonQGameWindowCtrl.answerClicked = false;
                }
            }
        };

        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    /**
     * This method shows the next question to the player unless the game is over
     * @param i index of the question in the questions list
     * @throws IOException
     */
    public void showNextQuestion(int i) throws IOException {
        PreciseQuestion pq;
        GuessQuestion gq;
        ComparisonQuestion cq;

        singleplayerPreciseQGameWindowCtrl.answerClicked = false;
        singleplayerPreciseQGameWindowCtrl.canAnswer = true;
        singleplayerGuessQGameWindowCtrl.canAnswer = true;
        singleplayerComparisonQGameWindowCtrl.canAnswer = true;
        singleplayerGuessQGameWindowCtrl.answerClicked = false;
        singleplayerComparisonQGameWindowCtrl.answerClicked = false;

        if (mainCtrl.getRoundList().get(i).getQuestion().getClass().getSimpleName().equals("PreciseQuestion")) {
            pq = (PreciseQuestion) mainCtrl.getRoundList().get(i).getQuestion();
            mainCtrl.showSinglePlayerPreciseQ();
            singleplayerPreciseQGameWindowCtrl.feedElements(pq);
            singleplayerPreciseQGameWindowCtrl.refreshColors();
            questionType = "PreciseQuestion";
            startTimer(8);
        }
        else if (mainCtrl.getRoundList().get(i).getQuestion().getClass().getSimpleName().equals("GuessQuestion")) {
            gq = (GuessQuestion) mainCtrl.getRoundList().get(i).getQuestion();
            mainCtrl.showSinglePlayerGuessQ();
            singleplayerGuessQGameWindowCtrl.feedElements(gq);
            singleplayerGuessQGameWindowCtrl.refreshColors();
            questionType = "GuessQuestion";
            startTimer(8);
        }
        else if (mainCtrl.getRoundList().get(i).getQuestion().getClass().getSimpleName().equals("ComparisonQuestion")) {
            cq = (ComparisonQuestion) mainCtrl.getRoundList().get(i).getQuestion();
            mainCtrl.showSinglePlayerComparisonQ();
            questionType = "ComparisonQuestion";
            singleplayerComparisonQGameWindowCtrl.feedElements(cq);
            singleplayerComparisonQGameWindowCtrl.refreshColors();
            startTimer(8);
        }
    }
}
