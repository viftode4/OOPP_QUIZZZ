/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.utils;

import commons.Activity;
import commons.Player;
import commons.Round;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    /**
     * Gets the singleplayer leaderboard
     * @return the leaderboard for all singleplayer players
     */
    public List<Player> getLeaderBoard() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/singleplayer/allplayers") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Player>>() {});
    }

    public Player addSinglePlayer(Player player) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/singleplayer/players") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(player, APPLICATION_JSON), Player.class);
    }

    //send chosen answer's value to the server with idenfitication of player and game

    // not sure about the answer being int or String might cause errors

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    public Player sendAnswer(String answer,Player player, int gameid){
        System.out.println("sent answer");
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/question/answer/send")
                .queryParam("answersent",Integer.parseInt(answer))
                .queryParam("gameid",gameid)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player,APPLICATION_JSON),Player.class);
    }

    public List<Round> pullActivities() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/singleplayer/games/rounds") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Round>>() {});
    }

    public void stop(){
        EXEC.shutdownNow();
    }

    /**
     *
     * @return list of activities from database
     */
    public List<Activity> getActivities(){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/admin/getactivities")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<List<Activity>>() {});
    }

    /**
     *
     * @param activity - Activity to update in the database
     * @return String telling it was updated with activity id for reference
     */
    public String updateActivity(Activity activity){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/admin/updateactivity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(activity,APPLICATION_JSON),String.class);
    }

    public Player updatePlayerScore(Player player) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path("/singleplayer/players/update")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(player,APPLICATION_JSON),Player.class);
    }


    /**
     * Deletes the activity from the database with the given id
     * @param aid long value for the id of the activity to delete
     * @return void
     *
     */
    public String deleteActivity(Long aid){
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("/admin/deleteactivity")
                .queryParam("id",aid)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }


    /**
     * Retrieves the leaderboards for a specific game
     * @param gameid the id for the game
     * @return the players in the game
     */
    public List<Player> getMPLeaderboardByID(int gameid) throws Exception {
        try {
            return ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("/multiplayer/" + gameid + "/leaderboard") //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<List<Player>>() {});
        } catch (Exception e) {
            throw new Exception("Failed to get player list!");
        }
    }
}