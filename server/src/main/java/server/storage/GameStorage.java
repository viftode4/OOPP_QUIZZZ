package server.storage;

import commons.MultiPlayerGame;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {

    private static Map<Integer, MultiPlayerGame> games;
    private static GameStorage instance;

    public GameStorage(){
        games = new HashMap<>();
    }

    //this implementation makes sure there is always a gamestorage for the games
    public static synchronized GameStorage getInstance()
    {
        if(instance == null)
            instance = new GameStorage();
        return instance;
    }

    public Map<Integer, MultiPlayerGame> getGames(){
        return games;
    }
    public void setGame(MultiPlayerGame game)
    {
        games.put(Math.toIntExact(game.getGameId()), game);
    }
}
