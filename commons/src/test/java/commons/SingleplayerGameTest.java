package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SingleplayerGameTest {

    @Test
    public void setPlayerTest(){
        Player a = new Player(1L, "mike");
        SinglePlayerGame game = new SinglePlayerGame();
        game.setPlayer(a);

        assertEquals(a, game.getPlayer());

        Player b = new Player(2L, "john");
        game.setPlayer(b);

        assertEquals(b, game.getPlayer());
    }

    @Test
    public void getPlayerTest(){
        Player a = new Player(1L, "mike");
        SinglePlayerGame game = new SinglePlayerGame();
        game.setPlayer(a);

        assertEquals(a, game.getPlayer());

        Player b = new Player(2L, "john");
        game.setPlayer(b);

        assertEquals(b, game.getPlayer());
    }

    @Test
    public void increaseScoreTest(){
        SinglePlayerGame game = new SinglePlayerGame();
        game.setTotalScore(10);
        assertEquals(10,game.getTotalScore());

        game.increaseScore(10);
        assertEquals(20,game.getTotalScore());
    }

    @Test
    public void equalsTest(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        SinglePlayerGame game = new SinglePlayerGame(1L, roundList, player);
        SinglePlayerGame game2 = new SinglePlayerGame(1L, roundList, player);

        assertEquals(game, game2);
    }

    @Test
    public void notEqualsTest(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        SinglePlayerGame game = new SinglePlayerGame(1L, roundList, player);
        SinglePlayerGame game2 = new SinglePlayerGame(2L, roundList, player);

        assertNotEquals(game, game2);
    }

    @Test
    public void equalsHashCode(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        SinglePlayerGame game = new SinglePlayerGame(1L, roundList, player);
        SinglePlayerGame game2 = new SinglePlayerGame(1L, roundList, player);

        assertEquals(game, game2);
        assertEquals(game.hashCode(), game2.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        SinglePlayerGame game = new SinglePlayerGame(1L, roundList, player);
        SinglePlayerGame game2 = new SinglePlayerGame(2L, roundList, player);

        assertNotEquals(game, game2);
        assertNotEquals(game.hashCode(), game2.hashCode());
    }
}
