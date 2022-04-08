package commons;

import java.util.List;
import java.util.Objects;


/**
 * The type Single player game.
 */
public class SinglePlayerGame extends Game{

    private Player player;

    /**
     * Instantiates a new Single player game.
     *
     * @param id        the id
     * @param roundList the round list
     * @param player    the player
     */
    public SinglePlayerGame(Long id, List<Round> roundList, Player player){
        super(id, roundList);
        this.player = player;
    }

    /**
     * Instantiates a new Single player game.
     */
    public SinglePlayerGame(){

    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Increase score.
     *
     * @param points the points
     */
    public void increaseScore(int points){
        setTotalScore(getTotalScore() + points);
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
        SinglePlayerGame that = (SinglePlayerGame) o;
        return player.equals(that.player);
    }

    /**
     * @return returns the calculated hash code of the object "this"
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), player);
    }
}
