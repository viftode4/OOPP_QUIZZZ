package commons.dto;

import commons.Player;

public class ConnectRequest {

    private Player player;
    private int gameId;

    public ConnectRequest(){

    }

    public ConnectRequest(Player player, int gameId){
        this.player = player;
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    public Player getPlayer() {
        return player;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
