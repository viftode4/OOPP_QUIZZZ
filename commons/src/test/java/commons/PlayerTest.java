package commons;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void getGameTest(){
        Player player = new Player();
        player.setGameid(3);

        assertEquals(3, player.getGame());
    }

    @Test
    public void setGameTest(){
        Player player = new Player();
        int game = 5;
        player.setGameid(game);

        assertEquals(5, player.getGame());
    }

    @Test
    public void setFlagAnsTest(){
        Player player = new Player();
        player.setFlagAns(true);
        assertTrue(player.hasAnswered());

        player.setFlagAns(false);
        assertFalse(player.hasAnswered());
    }

    @Test
    public void getFlagAnsTest(){
        Player player = new Player();
        player.setFlagAns(true);
        assertTrue(player.hasAnswered());

        player.setFlagAns(false);
        assertFalse(player.hasAnswered());
    }

    @Test
    public void setScoreTest(){
        Player player = new Player();
        player.setScore(150);
        assertEquals(150, player.getScore());

        player.setScore(300);
        assertEquals(300, player.getScore());
    }

    @Test
    public void getScoreTest(){
        Player player = new Player();
        player.setScore(150);
        assertEquals(150, player.getScore());

        player.setScore(300);
        assertEquals(300, player.getScore());
    }

    @Test
    public void updateScoreTest(){
        Player player = new Player();
        player.setScore(150);
        player.updateScore(30);

        assertEquals(180, player.getScore());
    }

    @Test
    public void getIDTest(){
        Player player = new Player(1L, "mike");

        assertEquals(1L, player.getId());
    }

    @Test
    public void setIDTest(){
        Player player = new Player(1L, "mike");
        player.setId(3L);

        assertEquals(3L, player.getId());
    }

    @Test
    public void setUsernameTest(){
        Player player = new Player(1L, "mike");
        player.setUsername("john");

        assertEquals("john", player.getUsername());
    }

    @Test
    public void getUsernameTest(){
        Player player = new Player(1L, "mike");
        player.setUsername("john");

        assertEquals("john", player.getUsername());
    }

    @Test
    public void getCorrectQNoTest(){
        Player player = new Player();
        player.setCorrectQNo(5);
        assertEquals(5, player.getCorrectQNo());

        player.setCorrectQNo(6);
        assertEquals(6, player.getCorrectQNo());
    }

    @Test
    public void setCorrectQNoTest(){
        Player player = new Player();
        player.setCorrectQNo(5);
        assertEquals(5, player.getCorrectQNo());

        player.setCorrectQNo(6);
        assertEquals(6, player.getCorrectQNo());
    }

    @Test
    public void incCorrectQNoTest(){
        Player player = new Player();
        player.setCorrectQNo(5);
        player.incCorrectQNo();

        assertEquals(6, player.getCorrectQNo());
    }

    @Test
    public void getEmojisUsed(){
        Player player = new Player();
        player.setEmojisUsed(3);
        assertEquals(3, player.getEmojisUsed());

        player.setEmojisUsed(6);
        assertEquals(6,player.getEmojisUsed());
    }

    @Test
    public void setEmojisUsed(){
        Player player = new Player();
        player.setEmojisUsed(3);
        assertEquals(3, player.getEmojisUsed());

        player.setEmojisUsed(6);
        assertEquals(6,player.getEmojisUsed());
    }

    @Test
    public void isJoker1Test(){
        Player a = new Player();
        a.setJoker1(true);
        assertTrue(a.isJoker1());

        a.setJoker1(false);
        assertFalse(a.isJoker1());
    }

    @Test
    public void setJoker1Test(){
        Player a = new Player();
        a.setJoker1(true);
        assertTrue(a.isJoker1());

        a.setJoker1(false);
        assertFalse(a.isJoker1());
    }

    @Test
    public void isJoker2Test(){
        Player a = new Player();
        a.setJoker2(true);
        assertTrue(a.isJoker2());

        a.setJoker2(false);
        assertFalse(a.isJoker2());
    }

    @Test
    public void setJoker2Test(){
        Player a = new Player();
        a.setJoker2(true);
        assertTrue(a.isJoker2());

        a.setJoker2(false);
        assertFalse(a.isJoker2());
    }

    @Test
    public void isJoker3Test(){
        Player a = new Player();
        a.setJoker3(true);
        assertTrue(a.isJoker3());

        a.setJoker3(false);
        assertFalse(a.isJoker3());
    }

    @Test
    public void setJoker3Test(){
        Player a = new Player();
        a.setJoker3(true);
        assertTrue(a.isJoker3());

        a.setJoker3(false);
        assertFalse(a.isJoker3());
    }

    @Test
    void equalsTest() {
        Player a = new Player(1L, "mike");
        Player b = new Player(1L, "mike");

        assertEquals(a, b);
    }

    @Test
    void notEqualsTest() {
        Player a = new Player(1L, "mike");
        Player b = new Player(1L, "john");

        assertNotEquals(a, b);
    }

    @Test
    public void equalsHashCode(){
        Player a = new Player(1L, "mike");
        Player b = new Player(1L, "mike");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        Player a = new Player(1L,"mike");
        Player b = new Player(2L,"mick");

        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testToString(){
        Player player = new Player(1L, "mike");
        player.setScore(3);
        player.setGameid(6);
        player.setEmojisUsed(5);
        player.setCorrectQNo(7);
        player.setJoker1(true);
        player.setJoker2(false);
        player.setJoker3(false);

        assertEquals("User id: 1, Username: 'mike', Score: 3, Emojis used: 5" +
                ", Correct answers: 7, joker1 used: true, joker2 used: false, joker3 used: false" +
                ", game id: 6", player.toString());

    }
}
