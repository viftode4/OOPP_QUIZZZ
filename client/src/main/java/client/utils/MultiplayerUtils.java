package client.utils;

import commons.MultiPlayerGame;
import commons.Player;
import commons.dto.ConnectRequest;
import commons.dto.PlayerAnswer;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class MultiplayerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Sends post request to server and makes a new game and putting player inside
     * @param player the player
     * @return the game
     */
    public MultiPlayerGame makeNewGame(Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/make")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), MultiPlayerGame.class);
    }

    /**
     * Connects a player to a game to a game with a gameID
     * @param request the connection request containing the player and the gameID
     * @return the game
     */
    public MultiPlayerGame connectToGameWithID(ConnectRequest request){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/connect")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(request, APPLICATION_JSON), MultiPlayerGame.class);
    }

    /**
     * Connects a player to a random game
     * @param player the player
     * @return the game
     */
    public MultiPlayerGame connectToRandomGame(Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/connect/random")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), MultiPlayerGame.class);
    }

    /**
     * Starts the game a player is in
     * @param player the player
     * @return the game
     */
    public MultiPlayerGame startGame(Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/start")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), MultiPlayerGame.class);
    }

    /**
     * Method for returning the game a player is connected to
     * @param player the player
     * @return the game
     */
    public MultiPlayerGame getGame(Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/get")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player, APPLICATION_JSON), MultiPlayerGame.class);
    }

    /**
     * Method for sending a players answer to the server
     * @param move the players answer information - player, the game it sends the answer to and the answer
     * @return the game
     */
    public MultiPlayerGame sendAnswer(PlayerAnswer move){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/answer")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(move, APPLICATION_JSON), MultiPlayerGame.class);
    }

    public List<MultiPlayerGame> getAllGames() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/games")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<MultiPlayerGame>>() {});
    }

    /**
     * @param player player who wants to leave the lobby
     * @return returns the player who left the lobby
     */
    public Player leaveLobby(Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/leaveLobby")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player,APPLICATION_JSON),Player.class);
    }


    /**
     * Deletes the game when the game ends
     * @param game the game that has ended
     * @return returns the game that has ended
     */
    public MultiPlayerGame gameCleanup(MultiPlayerGame game){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/gameCleanup")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(game,APPLICATION_JSON),MultiPlayerGame.class);
    }


    /**
     * halves the time for every player in that game other than the player who used the joker
     * @param game the game of the player who used the joker
     * @param player the player who used the joker
     * @return returns the game of the player who used the joker
     */
    public MultiPlayerGame haste(MultiPlayerGame game, Player player){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/multiplayer/haste")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(game,APPLICATION_JSON),MultiPlayerGame.class);
    }

}
