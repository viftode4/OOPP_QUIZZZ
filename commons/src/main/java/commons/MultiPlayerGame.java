package commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static commons.GameState.NEW;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * The type Multi player game.
 */
public class MultiPlayerGame extends Game{

    private List<Player> playerList;

    /**
     * The Emoji chat.
     */
    List<EmojiEntry> emojiChat;

    private GameState status;

    private double timervalue;

    private int totalJokersUsed;

    /**
     * Instantiates a new Multi player game.
     *
     * @param id         the id
     * @param roundList  the round list
     * @param playerList the player list
     */
    public MultiPlayerGame(Long id, List<Round> roundList, List<Player> playerList){
        super(id, roundList);
        this.emojiChat = new ArrayList<>();
        this.playerList = playerList;
        this.totalJokersUsed = 0;
    }

    /**
     * Instantiates a new Multi player game.
     */
    public MultiPlayerGame(){
        super();
        this.playerList = new ArrayList<>();
        this.emojiChat = new ArrayList<>();
        this.totalJokersUsed = 0;
    }

    /**
     * Gets player list.
     *
     * @return the player list
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Sets player list.
     *
     * @param playerList the player list
     */
    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    /**
     * Gets emoji chat.
     *
     * @return the emoji chat
     */
    public List<EmojiEntry> getEmojiChat() {
        return emojiChat;
    }

    /**
     * Sets emoji chat.
     *
     * @param emojiChat the emoji chat
     */
    public void setEmojiChat(List<EmojiEntry> emojiChat) {
        this.emojiChat = emojiChat;
    }

    /**
     * Update scores.
     *
     * @param playerList the player list
     */
    public void updateScores(List<Player> playerList){
        int sum = 0;
        for(Player p : playerList){
            sum += p.getScore();
        }
        setTotalScore(sum);
    }

    /**
     * Sort scores list.
     *
     * @param playerList the player list
     * @return the list
     */
    public List<Player> sortScores(List<Player> playerList){
        List<Player> leaderboard = new ArrayList<>();
        leaderboard = playerList.stream()
                .sorted((f,s)->s.getScore()-f.getScore())
                .collect(Collectors.toList());
        return leaderboard;
    }

    /**
     * Increases the score of the player inside the game
     * @param player the player
     * @throws Exception if the player is not in the game
     */
    public void increaseScore(Player player) throws Exception {
        int index = playerList.indexOf(player);

        if(index == -1)
            throw new Exception("Player not found in game " + getGameId());

        playerList.get(index).increaseScore(1000 * (int)timervalue / 8);
        playerList.get(index).incCorrectQNo();
    }

    /**
     * Sets the answered flag inside the game
     * @param player the player
     * @param flag the value of tha flag
     * @throws Exception if the player is not in the game
     */
    public void setAnsFlag(Player player, boolean flag) throws Exception {
        int index = playerList.indexOf(player);

        if(index == -1)
            throw new Exception("Player not found in game " + getGameId());
        playerList.get(index).setFlagAns(flag);
    }

    /**
     * Adds the player to the game
     * @param player the player
     */
    public void addPlayer(Player player)
    {
        this.playerList.add(player);
    }

    /**
     * Sets the status of the game
     * @param status
     */
    public void setStatus(GameState status)
    {
        this.status = status;
    }

    /**
     * Gets the status of the game
     * @return the status
     */
    public GameState getStatus(){
        return status;
    }

    /**
     * returns the updated player object inside of the game
     * @param oldPLayer the old player object
     * @return the new player
     */
    public Player returnUpdatedPlayer(Player oldPLayer)
    {
        for(Player x : playerList)
            if(x.getUsername().equals(oldPLayer.getUsername()))
                return x;

        return oldPLayer;
    }

    /**
     * This method checks if the game is valid for entry
     * @return true if is valid, false if not
     */
    public boolean isValid() {
        if(!status.equals(NEW))
            return false;
        return true;
    }

    /**
     * This method checks if all the players in the game have answered
     * @return true if they did, false if not
     */
    public boolean allPlayersAnswered() {
        for(Player x : playerList)
            if(!x.hasAnswered())
                return false;
        return true;
    }

    /**
     *
     * @return timervalue
     */
    public double getTimervalue(){
        return this.timervalue;
    }

    /**
     *
     * @param value the timevalue to set timervalue to
     */
    public void setTimervalue(double value){
        this.timervalue=value;
    }


    /**
     * Compares two objects and returns a boolean as a conclusion
     * @param o the other game/object to compare to
     * @return returns a boolean, true if the object o and "this" is equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MultiPlayerGame that = (MultiPlayerGame) o;
        return Objects.equals(playerList, that.playerList) && Objects.equals(emojiChat, that.emojiChat) && status == that.status;
    }

    /**
     * @return returns the calculated hash code of the object "this"
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), playerList, emojiChat, status);
    }

    @Override
    public String toString() {
        return "MultiPlayerGame: " +
                super.toString() +
                ", playerList: " + playerList +
                ", status=" + status;
    }

    public int getTotalJokersUsed() {
        return totalJokersUsed;
    }

    public void setTotalJokersUsed(int totalJokersUsed) {
        this.totalJokersUsed = totalJokersUsed;
    }

    public void incrementJokers(){
        this.setTotalJokersUsed(this.getTotalJokersUsed() + 1);
    }
}


