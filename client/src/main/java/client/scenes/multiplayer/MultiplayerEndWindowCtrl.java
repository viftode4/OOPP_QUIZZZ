package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.Game;
import commons.MultiPlayerGame;
import commons.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MultiplayerEndWindowCtrl implements Initializable {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    @FXML
    private Button enqueueAgain;

    @FXML
    private Button returnToMenu;

//    @FXML
//    private Button closeGame;

    @FXML
    private ImageView left;

    @FXML
    private ImageView right;

    @FXML
    private Label placement;

    @FXML
    private Label pointsScored;

    @FXML
    private Label averageScore;

    @FXML
    private BarChart barChart;


    private final MultiplayerUtils multiUtils;

    /**
     * initializes the images for the title
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.left.setImage(new Image("@../../assets/filler1.png"));
        this.right.setImage(new Image("@../../assets/filler1.png"));
    }

    /**
     * Constructor for the controller class for the multiplayer game end window
     * @param server
     * @param mainCtrl
     * @param multiUtils
     */
    @Inject
    public MultiplayerEndWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl,MultiplayerUtils multiUtils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiUtils = multiUtils;
    }

    /**
     * Gets the data for the table
     * @param game the current game instance
     * @param player the current player
     * @param mainCtrl game mainctrl
     * @param multiUtils game multiutils
     */
    public void loadData(Game game, Player player, MainGameCtrl mainCtrl,MultiplayerUtils multiUtils) {

        MultiPlayerGame mpGame = (MultiPlayerGame) game;

        pointsScored.setText("Points Scored: "+ player.getScore());
        placement.setText("Placement: " + determinePlacement(player,mpGame));
        averageScore.setText("Average Score: " + calculateAvgScore(mpGame));


        XYChart.Series<String,Integer> series1 = new XYChart.Series<String,Integer>();
        series1.setName("Graphic Distribution of Scores");
        //series1.nodeProperty().

        for (int i = 0; i < (mpGame.getPlayerList().size()); i++) {

            String name = (mpGame.getPlayerList().get(i).getUsername());

//            if(mpGame.getPlayerList().get(i).getUsername().equals(player.getUsername())){
//                name = "YOU";
//            }


            series1.getData().add(new XYChart.Data<>(name
                    ,(mpGame.getPlayerList().get(i).getScore())));
        }

//        barChart.setBarGap(5);
//        barChart.setCategoryGap(5);
        barChart.getData().add(series1);


    }

    private int calculateAvgScore(MultiPlayerGame mpGame) {

        return (int) mpGame.getPlayerList().stream().mapToInt(Player::getScore).average().getAsDouble();
    }

    /**
     * This method leads the player enqueue in a new game and join a new lobby
     * and hence leads back to the lobby page. Stops game thread
     */
    public void enqueueAgain() {
        mainCtrl.showLobbyChoice();
        mainCtrl.stopGameThread();
    }

//    public void closeGame(){
//        server.stop();
//    }

    /**
     * This method leads the player back to the game menu
     * Stops game thread
     */
    public void jumpToMenu() {
        mainCtrl.showMenu();
        mainCtrl.stopGameThread();
    }

    public String determinePlacement(Player player, MultiPlayerGame game){
        List<Integer> collect = game.getPlayerList().stream()
                .map(Player::getScore).sorted(Integer::compareTo)
                .collect(Collectors.toList());

        for (int i = 0; i < collect.size(); i++) {
            if(player.getScore() == collect.get(i)){
                System.out.println("Placed: " + (i+1));
                System.out.println("Or: " + (collect.size() + i));
                return (collect.size()-i) + " / " + collect.size();
            }
        }

        return -1 + " / " + collect.size();


    }
}
