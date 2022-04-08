package commons;


import java.util.List;
import java.util.Objects;


/**
 * The type Game.
 */
public abstract class Game {


    private Long gameId;

    private int totalScore;

    private List<Round> roundList;

    private int roundNumber;

    /**
     * Instantiates a new Game.
     *
     * @param id        the id
     * @param roundList the round list
     */
    public Game(Long id, List<Round> roundList) {
        this.gameId = id;
        this.roundList = roundList;
        this.totalScore = 0;
        this.roundNumber = 0;
    }

    /**
     * Instantiates a new Game.
     */
    public Game(){
        this.roundNumber = 0;
    }

    /**
     * Set id.
     *
     * @param gameId the id
     */
    public void setGameId(Long gameId){
        this.gameId = gameId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     * Gets roundNumber.
     *
     * @return the roundNumber
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * Sets roundNumber.
     *
     * @param roundNumber the roundNumber
     */
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    /**
     * Increases the round number by 1
     */
    public void increaseRoundNumber(){
        this.roundNumber++;
    }
    /**
     * Gets total score.
     *
     * @return the total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * Sets total score.
     *
     * @param totalScore the total score
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * Gets round list.
     *
     * @return the round list
     */
    public List<Round> getRoundList() {
        return roundList;
    }

    /**
     * Sets round list.
     *
     * @param roundList the round list
     */
    public void setRoundList(List<Round> roundList) {
        this.roundList = roundList;
    }

    /**
     * Compares two games to check if they are equal
     * @param o the other game/object to compare to
     * @return whether they are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return totalScore == game.totalScore && roundNumber == game.roundNumber && Objects.equals(gameId, game.gameId) && Objects.equals(roundList, game.roundList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameId, totalScore, roundList, roundNumber);
    }

    /**
     * Creates a string representation for a Game
     * @return a Game with properties in string form
     */
    @Override
    public String toString() {
        return "Game id: " + gameId +
                ", totalScore: " + totalScore +
                ", roundList: " + roundList +
                ", round number: " + roundNumber;
    }
}
