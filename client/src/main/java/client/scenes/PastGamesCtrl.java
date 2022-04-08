package client.scenes;

import client.utils.ServerUtils;
import commons.Player;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.inject.Inject;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

import java.util.*;
import java.util.List;

public class PastGamesCtrl implements Initializable {
    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    private ObservableList<Player> data;

    @FXML
    private Button backToMain;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView left;
    @FXML
    private ImageView right;
    @FXML
    private TableView<Player> table;
    @FXML
    private TableColumn<Player, String> id;
    @FXML
    private TableColumn<Player, String> score;
    @FXML
    private TableColumn<Player, String> playerName;

    /**
     * This is the constructor method for the controller class of the
     * past games window
     * @param server
     * @param mainCtrl
     */
    @Inject
    public PastGamesCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;

        this.left = new javafx.scene.image.ImageView();
        this.right = new javafx.scene.image.ImageView();
    }

    /**
     * Sets some properties of the past games page, including some images
     * that are shown within it, and the data presented in the leaderboard table
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.left.setImage(new Image("@../../assets/filler2.png"));
        this.right.setImage(new Image("@../../assets/filler2.png"));

        id.setCellValueFactory(q -> new SimpleStringProperty(Long.toString(q.getValue().getId())));
        score.setCellValueFactory(q -> new SimpleStringProperty(Integer.toString(q.getValue().getScore())));
        playerName.setCellValueFactory(q -> new SimpleStringProperty(q.getValue().getUsername()));
        List<Player> leaderboard = server.getLeaderBoard();
        sortPlayersOnScore(leaderboard);
        List<Player> editedLeaderBoard = cutOffLeaderBoard(leaderboard);
        System.out.println(editedLeaderBoard.size());
        data = FXCollections.observableList(editedLeaderBoard);
        table.setItems(data);
    }

    /**
     * Updates the leaderboard after the game has ended
     */
    public void updateLeaderBoard() {
        List<Player> leaderboard = server.getLeaderBoard();
        sortPlayersOnScore(leaderboard);
        List<Player> editedLeaderBoard = cutOffLeaderBoard(leaderboard);
        System.out.println(editedLeaderBoard.size());
        data = FXCollections.observableList(editedLeaderBoard);
        table.setItems(data);
    }

    /**
     * Sorts the returned list of players from the server side into decreasing order
     * according to their recorded score in the database
     * @param players
     */
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
     * Cuts the player list that is presented in the leaderboard to 7 players,
     * to represent the 7 highest scores of all time
     * @param players
     * @return returns a list of players with maximum size of 7
     */
    public List<Player> cutOffLeaderBoard(List<Player> players) {
        List<Player> edited = new ArrayList<>();
        if (players.size() > 7) {
            for (int i = 0; i < 7; i++) {
                edited.add(players.get(i));
            }
        }
        else {
            edited = players;
        }
        return edited;
    }
    public void jumpToMenu() {
        mainCtrl.showMenu();
    }

}
