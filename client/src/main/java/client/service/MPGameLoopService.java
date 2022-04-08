package client.service;

import client.scenes.MainGameCtrl;
import client.scenes.multiplayer.LobbyWindowCtrl;
import client.scenes.multiplayer.MultiplayerComparisonQGameWindowCtrl;
import client.scenes.multiplayer.MultiplayerGuessQGameWindowCtrl;
import client.scenes.multiplayer.MultiplayerPreciseQGameWindowCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.MultiPlayerGame;
import commons.Player;
import commons.questions.ComparisonQuestion;
import commons.questions.GuessQuestion;
import commons.questions.PreciseQuestion;
import javafx.application.Platform;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;


import static commons.GameState.*;

@Service
public class MPGameLoopService {
    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;
    private final MultiplayerUtils multiServer;

    private Thread gamethread;
    private final LobbyWindowCtrl lobby;

    private final MultiplayerPreciseQGameWindowCtrl multiPreciseQWindowCtrl;
    private final MultiplayerGuessQGameWindowCtrl multiGuessQWindowCtrl;
    private final MultiplayerComparisonQGameWindowCtrl multiCompQWindowCtrl;

    private Player player;
    private MultiPlayerGame game;


    @Inject
    public MPGameLoopService(ServerUtils server,
                             MainGameCtrl mainCtrl,
                             MultiplayerUtils multiServer,
                             LobbyWindowCtrl lobby,
                             MultiplayerComparisonQGameWindowCtrl multiCompQWindowCtrl,
                             MultiplayerGuessQGameWindowCtrl multiGuessQWindowCtrl,
                             MultiplayerPreciseQGameWindowCtrl multiPreciseQWIndowCtrl)
    {
        this.mainCtrl = mainCtrl;
        this.server = server;
        this.multiServer = multiServer;
        this.lobby = lobby;
        this.multiCompQWindowCtrl = multiCompQWindowCtrl;
        this.multiPreciseQWindowCtrl = multiPreciseQWIndowCtrl;
        this.multiGuessQWindowCtrl = multiGuessQWindowCtrl;
        this.gamethread = GameThreadCreator();
    }


    /**
     * Updates the Player and Game values
     * Also updates timer on MP screens
     */
    public void updateCurent()
    {
        if(!gamethread.isInterrupted()) {


            //we get the game form the server first
            game = multiServer.getGame(player);
            //we update our with the server one
            mainCtrl.setGame(game);
            //we update the player we recieved from server
            player = game.returnUpdatedPlayer(player);
            //we set it
            mainCtrl.setPlayer(player);


            Platform.runLater(()->{
                if (game.getStatus().equals(SHOWANSWER)) {
                    mainCtrl.getMultiGuessQWindowCtrl().displayAnswerText();
                    mainCtrl.getMultiCompQWindowCtrl().displayAnswerText();
                    mainCtrl.getMultiPreciseQWindowCtrl().displayAnswerText();

                }else{
                    mainCtrl.getMultiGuessQWindowCtrl().displayTime();
                    mainCtrl.getMultiCompQWindowCtrl().displayTime();
                    mainCtrl.getMultiPreciseQWindowCtrl().displayTime();
                }

            });



        }
    }

    /**
     * Starts the game thread, the polling
     */
    public void startGame() {

        this.gamethread = GameThreadCreator();
        gamethread.start();

    }

    /**
     * Stops the game thread
     */
    public void stopGame(){
        this.gamethread.interrupt();
    }

    /**
     * Creates a thread that's gonna poll for interaction with the server
     * It keeps updating the game object and show scenes according to Status/RoundNumber
     * @return Thread (polling the server for game actions)
     */
    public Thread GameThreadCreator(){
        return new Thread(){
            @Override
            public void run(){


                player = mainCtrl.getPlayer();
                updateCurent();

                while ((game.getStatus().equals(NEW) || game.getStatus().equals(FULL)) && !this.isInterrupted()) {
                    Platform.runLater(() -> {
                        lobby.updatePlayerList(game.getPlayerList());
                        lobby.setGameIDLabel(Math.toIntExact(game.getGameId()));
                    });

                    try {
                        this.sleep(1000);
                    } catch (InterruptedException e) {

                        this.interrupt();
                        e.printStackTrace();
                        break;
                    }
                    updateCurent();
                }

                PreciseQuestion pq;
                GuessQuestion gq;
                ComparisonQuestion cq;

                //do not change screens during the polling unless it goes to the next one
                int prevRound = -1;
                while ((game.getStatus().equals(STARTED) || game.getStatus().equals(TIMEUP) || game.getStatus().equals(SHOWANSWER)) && !this.isInterrupted()) {
                    //get the round number to know which question to display
                    int roundNo = game.getRoundNumber();
                    updateCurent();
                    //Show leaderboard for 5 seconds
                    if (roundNo != -1 && game.getStatus().equals(SHOWANSWER)) {
                        String questionType = game.getRoundList().get(roundNo).getQuestion().getClass().getSimpleName();
                            if (questionType.equals("PreciseQuestion")) {

                                Platform.runLater(() -> {
                                    if (!gamethread.isInterrupted()) {
                                        mainCtrl.getMultiPreciseQWindowCtrl().showAnswers();
                                    }
                                });
                            }
                            if (questionType.equals("GuessQuestion")) {

                                Platform.runLater(() -> {
                                    if (!gamethread.isInterrupted()) {
                                        mainCtrl.getMultiGuessQWindowCtrl().showAnswer();
                                    }
                                });
                            }
                            if (questionType.equals("ComparisonQuestion")) {

                                Platform.runLater(() -> {
                                    if (!gamethread.isInterrupted()) {
                                        mainCtrl.getMultiCompQWindowCtrl().showAnswers();
                                    }
                                });
                            }
                    }
                    if(roundNo != -1 && game.getStatus().equals(TIMEUP) && !gamethread.isInterrupted() && mainCtrl.isLeaderboard()) {
                        Platform.runLater(() -> mainCtrl.showMultiplayerLeaderboardWindow(Math.toIntExact(game.getGameId())));
                        System.out.println(game.getPlayerList().get(0).getScore());

                    }

                    if (prevRound != roundNo && roundNo!=20 && game.getStatus().equals(STARTED)) {
                        String questionType = game.getRoundList().get(roundNo).getQuestion().getClass().getSimpleName();
                        updateCurent();
                        if (questionType.equals("PreciseQuestion")) {
                            pq = (PreciseQuestion) mainCtrl.getGame().getRoundList().get(roundNo).getQuestion();

                            PreciseQuestion finalPq = pq;
                            Platform.runLater(() -> {
                                if(!gamethread.isInterrupted()){
                                    mainCtrl.getMultiPreciseQWindowCtrl().refreshColors();
                                    mainCtrl.getMultiPreciseQWindowCtrl().feedElements(finalPq);
                                    mainCtrl.showMultiPreciseQWindow();
                                }

                            });
                        } else if (questionType.equals("GuessQuestion")) {
                            gq = (GuessQuestion) mainCtrl.getGame().getRoundList().get(roundNo).getQuestion();

                            GuessQuestion finalGq = gq;
                            Platform.runLater(() -> {
                                if(!gamethread.isInterrupted()){
                                    mainCtrl.getMultiGuessQWindowCtrl().refreshColors();
                                    mainCtrl.getMultiGuessQWindowCtrl().feedElements(finalGq);
                                    mainCtrl.showMultiGuessQWindow();
                                }

                            });
                        } else if (questionType.equals("ComparisonQuestion")) {
                            cq = (ComparisonQuestion) mainCtrl.getGame().getRoundList().get(roundNo).getQuestion();

                            ComparisonQuestion finalCq = cq;
                            Platform.runLater(() -> {
                                if(!gamethread.isInterrupted()){
                                    mainCtrl.getMultiCompQWindowCtrl().refreshColors();
                                    mainCtrl.getMultiCompQWindowCtrl().feedElements(finalCq);
                                    mainCtrl.showMultiCompQWindow();

                                }

                            });
                        }
                        prevRound = roundNo;
                    }
                    updateCurent();




                }
                if (game.getStatus().equals(FINISHED)) {
                    Platform.runLater(() -> {
                        mainCtrl.showMultiplayerEndWindow(game,player,mainCtrl,multiServer);
                    });
                    game = multiServer.gameCleanup(game);
                }

            }
        };
    }



}
