package client.scenes;

import client.scenes.multiplayer.*;
import client.utils.MultiplayerUtils;
import commons.Game;
import commons.MultiPlayerGame;
import commons.Player;
import commons.Round;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;

public class MainGameCtrl {

    private Stage primaryStage;

    private MainMenuWindowCtrl menuCtrl;
    private Scene menu;

    private NameChoiceWindowCtrl nameChoiceCtrl;
    private Scene nameChoice;

    private SinglePlayerNameChoiceWindowCtrl singlePlayerNameChoiceWindowCtrl;
    private Scene singlePlayerNameChoice;

    private HowToPlayWindowCtrl howToPlayWindowCtrl;
    private Scene howToPlayGameWindow;

    private SingleplayerPreciseQGameWindowCtrl singleplayerPreciseQGameWindowCtrl;
    private Scene singleplayerPreciseQGameWindow;

    private SingleplayerGuessQGameWindowCtrl singleplayerGuessQGameWindowCtrl;
    private Scene singleplayerGuessQGameWindow;

    private SingleplayerComparisonQGameWindowCtrl singleplayerComparisonQGameWindowCtrl;
    private Scene singleplayerComparisonQGameWindow;

    private LobbyWindowCtrl lobbyCtrl;
    private Scene lobby;

    private MultiplayerEndWindowCtrl multiplayerEndWindowCtrl;
    private Scene multiplayerEndWindow;

    private SinglePlayerEndWindowCtrl singlePlayerEndWindowCtrl;
    private Scene singlePlayerEndWindow;

    private PastGamesCtrl pastGamesCtrl;
    private Scene pastGamesWindow;

    private LobbyChoiceWindowCtrl lobbyChoiceWindowCtrl;
    private Scene lobbyChoiceWindow;

    private AdminInterfaceCtrl adminInterfaceCtrl;
    private Scene adminInterfaceWindow;

    private MultiplayerLeaderboardWindowCtrl multiplayerLeaderboardWindowCtrl;
    private Scene multiplayerLeaderboardWindow;

    private MultiplayerComparisonQGameWindowCtrl multiCompQWindowCtrl;
    private Scene multiCompQWindow;

    private MultiplayerPreciseQGameWindowCtrl multiPreciseQWindowCtrl;
    private Scene multiPreciseQWindow;

    private MultiplayerGuessQGameWindowCtrl multiGuessQWindowCtrl;
    private Scene multiGuessQWindow;

    private Player player;
    private List<Round> roundList;

    private Game game;

    public boolean isJoker1() {
        return joker1;
    }

    public void setJoker1(boolean joker1) {
        this.joker1 = joker1;
    }

    public boolean isJoker2() {
        return joker2;
    }

    public void setJoker2(boolean joker2) {
        this.joker2 = joker2;
    }

    public boolean isJoker3() {
        return joker3;
    }

    public void setJoker3(boolean joker3) {
        this.joker3 = joker3;
    }

    boolean joker1 = true;
    boolean joker2 = true;
    boolean joker3 = true;


    private int totalJokersUsed;

    /**
     * @return the particular game instance belonging to this controller
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return the multiplayer game belonging to this controller
     */
    public MultiPlayerGame getMPgame(){
        return (MultiPlayerGame) game;
    }

    /**
     * Sets the game for this game controller
     * @param game
     */

    public void setGame(Game game) {

        //game is only set if the game is started
        this.game = game;
    }

    /**
     * Sets the game for this game controller
     * @param game
     */
    public void setGameatStart(Game game){
        this.game=game;

    }

    /**
     * Stop the MPGameLoopService thread instance
     *
     */
    public void stopGameThread(){
        this.lobbyChoiceWindowCtrl.StopGameThread();
    }

    /**
     * @return returns the primary stage for the application to run within
     */
    public Stage getPrimaryStage(){
        return this.primaryStage;
    }

    /**
     * Sets the player for the particular game instance
     * @param player
     */
    public void setPlayer(Player player){
        this.player=player;
        //player should be ready to join lobby since this is only set if username was found unique for given game
        //lobbyCtrl.gamepoller();
    }

    /**
     * Sets the player who is playing the particular game instance for a
     * single player game
     * @param player
     */
    public void setPlayerSP(Player player){
        this.player = player;
    }

    /**
     * @return returns the player of the game instance
     */
    public Player getPlayer(){
        //watch out for nullpointer
        return this.player;
    }

    /**
     * Initializes all the pages and controllers that are within the client
     * side of the program
     * @param primaryStage
     * @param menu
     * @param nameChoice
     * @param lobby
     * @param nameChoiceSP
     * @param howToPlay
     * @param singlePlayerPreciseQWindow
     * @param singlePlayerGuessQGameWindow
     * @param singlePlayerComparisonQGameWindow
     * @param multiplayerEndWindow
     * @param singlePlayerEndWindow
     * @param pastGames
     * @param lobbyChoiceWindow
     * @param adminInterfaceWindow
     * @param multiplayerLeaderboardWindow
     * @param multiPlayerPreciseQWindow
     * @param multiPlayerComparisonQWindow
     * @param multiPlayerGuessQWindow
     */
    public void initialize(Stage primaryStage,
                           Pair<MainMenuWindowCtrl, Parent> menu,
                           Pair<NameChoiceWindowCtrl, Parent> nameChoice,
                           Pair<LobbyWindowCtrl, Parent> lobby,
                           Pair<SinglePlayerNameChoiceWindowCtrl, Parent> nameChoiceSP,
                           Pair<HowToPlayWindowCtrl, Parent> howToPlay,
                           Pair<SingleplayerPreciseQGameWindowCtrl, Parent> singlePlayerPreciseQWindow,
                           Pair<SingleplayerGuessQGameWindowCtrl, Parent> singlePlayerGuessQGameWindow,
                           Pair<SingleplayerComparisonQGameWindowCtrl, Parent> singlePlayerComparisonQGameWindow,
                           Pair<MultiplayerEndWindowCtrl, Parent> multiplayerEndWindow,
                           Pair<SinglePlayerEndWindowCtrl, Parent> singlePlayerEndWindow,
                           Pair<PastGamesCtrl, Parent> pastGames,
                           Pair<LobbyChoiceWindowCtrl, Parent> lobbyChoiceWindow,
                           Pair<AdminInterfaceCtrl, Parent> adminInterfaceWindow,
                           Pair<MultiplayerLeaderboardWindowCtrl, Parent> multiplayerLeaderboardWindow,
                           Pair<MultiplayerPreciseQGameWindowCtrl, Parent> multiPlayerPreciseQWindow,
                           Pair<MultiplayerComparisonQGameWindowCtrl, Parent> multiPlayerComparisonQWindow,
                           Pair<MultiplayerGuessQGameWindowCtrl, Parent> multiPlayerGuessQWindow) {
        this.primaryStage = primaryStage;
        this.menuCtrl = menu.getKey();
        this.menu = new Scene(menu.getValue());

        this.nameChoiceCtrl = nameChoice.getKey();
        this.nameChoice = new Scene(nameChoice.getValue());

        this.lobbyCtrl = lobby.getKey();
        this.lobby = new Scene(lobby.getValue());

        this.singlePlayerNameChoiceWindowCtrl = nameChoiceSP.getKey();
        this.singlePlayerNameChoice = new Scene(nameChoiceSP.getValue());

        this.singleplayerPreciseQGameWindowCtrl = singlePlayerPreciseQWindow.getKey();
        this.singleplayerPreciseQGameWindow = new Scene(singlePlayerPreciseQWindow.getValue());

        this.singleplayerGuessQGameWindowCtrl = singlePlayerGuessQGameWindow.getKey();
        this.singleplayerGuessQGameWindow = new Scene(singlePlayerGuessQGameWindow.getValue());

        this.singleplayerComparisonQGameWindowCtrl = singlePlayerComparisonQGameWindow.getKey();
        this.singleplayerComparisonQGameWindow = new Scene(singlePlayerComparisonQGameWindow.getValue());

        this.howToPlayWindowCtrl = howToPlay.getKey();
        this.howToPlayGameWindow = new Scene(howToPlay.getValue());

        this.multiplayerEndWindowCtrl = multiplayerEndWindow.getKey();
        this.multiplayerEndWindow = new Scene(multiplayerEndWindow.getValue());

        this.singlePlayerEndWindowCtrl = singlePlayerEndWindow.getKey();
        this.singlePlayerEndWindow = new Scene(singlePlayerEndWindow.getValue());

        this.pastGamesCtrl = pastGames.getKey();
        this.pastGamesWindow = new Scene(pastGames.getValue());

        this.lobbyChoiceWindowCtrl = lobbyChoiceWindow.getKey();
        this.lobbyChoiceWindow = new Scene(lobbyChoiceWindow.getValue());

        this.adminInterfaceCtrl = adminInterfaceWindow.getKey();
        this.adminInterfaceWindow = new Scene(adminInterfaceWindow.getValue());

        this.multiplayerLeaderboardWindowCtrl = multiplayerLeaderboardWindow.getKey();
        this.multiplayerLeaderboardWindow = new Scene(multiplayerLeaderboardWindow.getValue());

        this.multiCompQWindowCtrl = multiPlayerComparisonQWindow.getKey();
        this.multiCompQWindow =  new Scene(multiPlayerComparisonQWindow.getValue());

        this.multiGuessQWindowCtrl = multiPlayerGuessQWindow.getKey();
        this.multiGuessQWindow = new Scene(multiPlayerGuessQWindow.getValue());

        this.multiPreciseQWindowCtrl = multiPlayerPreciseQWindow.getKey();
        this.multiPreciseQWindow = new Scene(multiPlayerPreciseQWindow.getValue());

//        primaryStage.initStyle(StageStyle.TRANSPARENT);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("@../../assets/filler1.png"));
        showMenu();
        primaryStage.show();
//        primaryStage.setMaximized(true);
//        primaryStage.setResizable(false);
    }

    /**
     * Jumps back to the menu window
     */
    public void showMenu() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(menu);
    }

    /**
     * Shows the lobby window for the multiplayer interface, where
     * the player can see the players who are waiting in the same lobby
     * and from this page can start the game
     */
    public void showLobby() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(lobby);
    }

    /**
     * Shows the lobby choice window for the multiplayer interface
     * where the player either chooses a lobby themselves or
     * joins a random one
     */
    public void showLobbyChoice(){
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(lobbyChoiceWindow);
    }

    /**
     * The name choice window is shown to the user, where the user
     * is prompted to enter a username by which they will be identified
     */
    public void showNameChoice() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(nameChoice);
    }

    /**
     * The single player name choice window is shown to the user, where the user
     * is prompted to enter a username by which they will be identified
     */
    public void showNameChoiceSP() {
        primaryStage.setTitle("Quizzzz");
        primaryStage.setScene(singlePlayerNameChoice);
    }

    /**
     * Shows a "Single Player Precise Question Window"
     * where the player chooses how much energy an activity
     * takes
     */
    public void showSinglePlayerPreciseQ() {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(singleplayerPreciseQGameWindow);
    }

    /**
     * Shows a "Single Player Guess Question Window"
     * where the player guesses how much energy an activity
     * takes
     */
    public void showSinglePlayerGuessQ() {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(singleplayerGuessQGameWindow);
    }

    /**
     * Shows a "Single Player Comparison Question Window"
     * where the player compares the energy usages between different
     * activities
     */
    public void showSinglePlayerComparisonQ() {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(singleplayerComparisonQGameWindow);
    }

    /**
     * Shows the "How To Play" window
     */
    public void jumpToHowToPlay() {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(howToPlayGameWindow);
    }

    /**
     * Shows the past games page, where the highest 7 scores from
     * all past single player games are listed in a sorted form
     */
    public void showPastGames() {
        primaryStage.setTitle(("Quizzzz"));

        primaryStage.setScene(pastGamesWindow);
    }

    /**
     * Shows the game ending window, where the score is shown
     * for a multiplayer game
     *
     * @param game the current game instance
     * @param player the current player
     * @param mainCtrl game mainctrl
     * @param multiUtils game multiutils
     *
     *
     */
    //Note that we currently have no way of accessing the end windows,
    //as the list of questions can not yet be generated
    public void showMultiplayerEndWindow(MultiPlayerGame game, Player player, MainGameCtrl mainCtrl, MultiplayerUtils multiUtils) {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(multiplayerEndWindow);

        Platform.runLater(() -> {
            try {
                multiplayerEndWindowCtrl.loadData(game,player,mainCtrl,multiUtils);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Shows the game ending window, where the score is shown
     * for a single player game
     */
    public void showSinglePlayerEndWindow() {
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(singlePlayerEndWindow);
    }

    /**
     * Shows the admin interface window, where the admin user
     * can load activities into the system
     */
    public void showAdminInterfaceWindow(){
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(adminInterfaceWindow);
    }

    boolean canShowLeaderboard;

    /**
     * Calls methods in the multiplayer leaderboard window controller class
     * to update and show the leaderboard for the multiplayer game at a point
     * in the game
     * @param gameid The unique ID for the particular game instance
     */
    public void showMultiplayerLeaderboardWindow(int gameid){
        canShowLeaderboard = false;
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(multiplayerLeaderboardWindow);
        Platform.runLater(() -> {
            try {
                multiplayerLeaderboardWindowCtrl.clearTable();
                multiplayerLeaderboardWindowCtrl.loadData(gameid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isLeaderboard() {
        return canShowLeaderboard;
    }

    /**
     *
     */
    public void showMultiGuessQWindow(){
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(multiGuessQWindow);
        multiplayerLeaderboardWindowCtrl.clearTable();
        canShowLeaderboard = true;
    }

    /**
     *
     */
    public void showMultiPreciseQWindow(){
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(multiPreciseQWindow);
        multiplayerLeaderboardWindowCtrl.clearTable();
        canShowLeaderboard = true;
    }

    /**
     *
     */
    public void showMultiCompQWindow(){
        primaryStage.setTitle(("Quizzzz"));
        primaryStage.setScene(multiCompQWindow);
        multiplayerLeaderboardWindowCtrl.clearTable();
        canShowLeaderboard = true;
    }

    /**
     * Method for getting the controller for the GuessQ for multiplayer
     * @return the controller
     */
    public MultiplayerGuessQGameWindowCtrl getMultiGuessQWindowCtrl(){
        return this.multiGuessQWindowCtrl;
    }
    /**
     * Method for getting the controller for the PreciseQ for multiplayer
     * @return the controller
     */
    public MultiplayerPreciseQGameWindowCtrl getMultiPreciseQWindowCtrl(){
        return this.multiPreciseQWindowCtrl;
    }
    /**
     * Method for getting the controller for the ComparisonQ for multiplayer
     * @return the controller
     */
    public MultiplayerComparisonQGameWindowCtrl getMultiCompQWindowCtrl(){
        return this.multiCompQWindowCtrl;
    }


    /**
     * @return returns the Name Choice Window controller
     */
    public NameChoiceWindowCtrl getNameChoiceCtrl() {
        return nameChoiceCtrl;
    }

    /**
     * @return returns the list of rounds for the particular game instance
     */
    public List<Round> getRoundList() {
        return roundList;
    }

    /**
     * Sets the list of rounds for the game
     * @param roundList The round list for the game
     */
    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    /**
     * @return returns the game ending window controller for the single player interface
     */
    public SinglePlayerEndWindowCtrl getSinglePlayerEndWindowCtrl() {
        return singlePlayerEndWindowCtrl;
    }

    public void updatePastGames() {
        pastGamesCtrl.updateLeaderBoard();
    }
}
