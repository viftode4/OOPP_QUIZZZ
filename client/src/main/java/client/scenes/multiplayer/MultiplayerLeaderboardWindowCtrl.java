package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.MultiplayerUtils;
import client.utils.ServerUtils;
import commons.MultiPlayerGame;
import commons.Player;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MultiplayerLeaderboardWindowCtrl implements Initializable {

    @FXML
    private TableView<Player> leaderboardTableView;

    @FXML
    private TableColumn<Player, String> playerColumn;

    @FXML
    private TableColumn<Player, Number> scoreColumn;

    @FXML
    private Button leaveGame;

    private ServerUtils server;
    private MultiplayerUtils multiUtils;
    private MainGameCtrl mainCtrl;

    /**
     * Constructor for leaderboard window controller
     * @param server the ServerUtils
     * @param mainCtrl the main game controller
     * @param multiUtils the MultiplayerUtils
     */
    @Inject
    public MultiplayerLeaderboardWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl, MultiplayerUtils multiUtils) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.multiUtils = multiUtils;
        this.playerColumn = new TableColumn<>("Player");
        this.scoreColumn = new TableColumn<>("Score");
        this.leaderboardTableView = new TableView<>();
    }

    /**
     * Overrides standard initialize method by setting values for the table
     * @param location uhh
     * @param resources these are necessary I think
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initialize table
        playerColumn.prefWidthProperty().bind(leaderboardTableView.widthProperty().multiply(0.6));
        scoreColumn.prefWidthProperty().bind(leaderboardTableView.widthProperty().multiply(0.4));
        playerColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    /**
     * Gets the data for the table
     * @param gameid the gameid for which the data should be collected
     */
    public void loadData(int gameid) {
        List<MultiPlayerGame> games = multiUtils.getAllGames();
        List<MultiPlayerGame> gameWithID = games
                .stream()
                .filter(g -> g.getGameId() == gameid)
                .collect(Collectors.toList());
        List<Player> players = gameWithID.get(0).getPlayerList();
        sortPlayersOnScore(players);
        var data = FXCollections.observableArrayList(players);
        leaderboardTableView.setItems(data);
        leaderboardTableView.getColumns().addAll(playerColumn, scoreColumn);
    }
    public void sortPlayersOnScore(List<Player> players) {
        for (int i = 0; i < players.size(); i++) {
            Player max = players.get(i);
            int max_index = i;
            for (int j = i; j < players.size(); j++) {
                if (players.get(j).getScore() > max.getScore()) {
                    max = players.get(j);
                    max_index = j;
                }
            }
            Player temp = players.get(i);
            players.set(i, max);
            players.set(max_index, temp);
        }
    }

    /**
     * Clears the table
     */
    public void clearTable() {
        leaderboardTableView.getColumns().removeAll(playerColumn, scoreColumn);
    }

    /**
     * Exits the game and returns to the main menu
     */
    public void leaveGame() {
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to leave the game?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            mainCtrl.stopGameThread();
            mainCtrl.showMenu();
            leaderboardTableView.getColumns().removeAll(playerColumn, scoreColumn);
        }


    }
}
