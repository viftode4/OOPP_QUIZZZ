package commons;

import commons.questions.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GameTest {

    @Test
    public void setGameIDTest(){
        Game game = new MultiPlayerGame();
        game.setGameId(3L);
        assertEquals(3L, game.getGameId());

        game.setGameId(4L);
        assertEquals(4L, game.getGameId());
    }

    @Test
    public void getGameIDTest(){
        Game game = new MultiPlayerGame();
        game.setGameId(3L);
        assertEquals(3L, game.getGameId());

        game.setGameId(4L);
        assertEquals(4L, game.getGameId());
    }

    @Test
    public void setRoundNumTest(){
        Game game = new MultiPlayerGame();
        game.setRoundNumber(3);
        assertEquals(3, game.getRoundNumber());

        game.setRoundNumber(4);
        assertEquals(4, game.getRoundNumber());
    }

    @Test
    public void getRoundNumTest(){
        Game game = new MultiPlayerGame();
        game.setRoundNumber(3);
        assertEquals(3, game.getRoundNumber());

        game.setRoundNumber(4);
        assertEquals(4, game.getRoundNumber());
    }

    @Test
    public void increaseRoundNumTest(){
        Game game = new MultiPlayerGame();
        game.setRoundNumber(3);

        game.increaseRoundNumber();
        assertEquals(4, game.getRoundNumber());
    }

    @Test
    public void getTotalScoreTest(){
        Game game = new MultiPlayerGame();
        game.setTotalScore(500);
        assertEquals(500, game.getTotalScore());

        game.setTotalScore(1000);
        assertEquals(1000, game.getTotalScore());
    }

    @Test
    public void setTotalScoreTest(){
        Game game = new MultiPlayerGame();
        game.setTotalScore(500);
        assertEquals(500, game.getTotalScore());

        game.setTotalScore(1000);
        assertEquals(1000, game.getTotalScore());
    }

    @Test
    public void setRoundListTest(){
        Game game = new MultiPlayerGame();
        List<Round> roundList = new ArrayList<>();
        Round a = new Round();
        roundList.add(a);
        game.setRoundList(roundList);
        assertEquals(roundList, game.getRoundList());

        roundList.add(new Round());
        game.setRoundList(roundList);
        assertEquals(roundList, game.getRoundList());
    }

    @Test
    public void getRoundListTest(){
        Game game = new MultiPlayerGame();
        List<Round> roundList = new ArrayList<>();
        Round a = new Round();
        roundList.add(a);
        game.setRoundList(roundList);
        assertEquals(roundList, game.getRoundList());

        roundList.add(new Round());
        game.setRoundList(roundList);
        assertEquals(roundList, game.getRoundList());
    }

    @Test
    public void equalsTest(){
        Game game = new MultiPlayerGame();
        game.setGameId(3L);
        game.setTotalScore(300);

        Game game1 = new MultiPlayerGame();
        game1.setGameId(3L);
        game1.setTotalScore(300);

        assertEquals(game, game1);
    }

    @Test
    public void notEqualsTest(){
        Game game = new MultiPlayerGame();
        game.setGameId(4L);
        game.setTotalScore(300);

        Game game1 = new MultiPlayerGame();
        game1.setGameId(3L);
        game1.setTotalScore(300);

        assertNotEquals(game, game1);
    }

    @Test
    public void hashCodeEqualsTest(){
        Game game = new MultiPlayerGame();
        game.setGameId(3L);
        game.setTotalScore(300);

        Game game1 = new MultiPlayerGame();
        game1.setGameId(3L);
        game1.setTotalScore(300);

        assertEquals(game, game1);
        assertEquals(game.hashCode(), game1.hashCode());
    }

    @Test
    public void hashCodeNotEquals(){
        Game game = new MultiPlayerGame();
        game.setGameId(4L);
        game.setTotalScore(300);

        Game game1 = new MultiPlayerGame();
        game1.setGameId(3L);
        game1.setTotalScore(300);

        assertNotEquals(game, game1);
        assertNotEquals(game.hashCode(), game1.hashCode());
    }

    @Test
    public void toStringTest(){
        List<Round> list = new ArrayList<>();
        Round round = new Round(3, new Question() {
            @Override
            public String getBaseActivity() {
                return super.getBaseActivity();
            }
        });
        round.setTime(8);
        list.add(round);
        Game game = new MultiPlayerGame();
        game.setGameId(3L);
        game.setTotalScore(300);
        game.setRoundList(list);
        game.setRoundNumber(3);

        assertEquals("MultiPlayerGame: Game id: 3, totalScore: 300, " +
                "roundList: [Round{roundNum=3, question=null, time=8, game=null}], " +
                "round number: 3, playerList: [], status=null", game.toString());
    }
}

