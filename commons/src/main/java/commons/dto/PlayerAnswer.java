package commons.dto;

import commons.Player;

public class PlayerAnswer {
    private Player player;
    private int answer;
    private int gameId;

    public PlayerAnswer(){

    }
    public PlayerAnswer(Player player, int answer, int gameId){
        this.answer = answer;
        this.gameId = gameId;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getAnswer() {
        return answer;
    }

    public int getGameId() {
        return gameId;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
