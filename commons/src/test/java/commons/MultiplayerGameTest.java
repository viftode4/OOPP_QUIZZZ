package commons;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplayerGameTest {

    @Test
    public void getPlayerListTest(){
        Player player1 = new Player(1L,"john");
        Player player2 = new Player();

        List<Player> list = new ArrayList<>();
        list.add(player1);

        MultiPlayerGame game = new MultiPlayerGame();
        game.setPlayerList(list);
        assertEquals(list, game.getPlayerList());

        list.add(player2);
        game.setPlayerList(list);
        assertEquals(list, game.getPlayerList());
    }

    @Test
    public void setPlayerListTest(){
        Player player1 = new Player(1L,"john");
        Player player2 = new Player();

        List<Player> list = new ArrayList<>();
        list.add(player1);

        MultiPlayerGame game = new MultiPlayerGame();
        game.setPlayerList(list);
        assertEquals(list, game.getPlayerList());

        list.add(player2);
        game.setPlayerList(list);
        assertEquals(list, game.getPlayerList());
    }

    @Test
    public void getEmojiChatTest(){
        EmojiEntry e1 = new EmojiEntry("a", "b");
        List<EmojiEntry> list = new ArrayList<>();
        list.add(e1);

        MultiPlayerGame game = new MultiPlayerGame();
        game.setEmojiChat(list);
        assertEquals(list, game.getEmojiChat());

        list.add(new EmojiEntry("b", "c"));
        game.setEmojiChat(list);
        assertEquals(list, game.getEmojiChat());
    }

    @Test
    public void setEmojiChatTest(){
        EmojiEntry e1 = new EmojiEntry("a", "b");
        List<EmojiEntry> list = new ArrayList<>();
        list.add(e1);

        MultiPlayerGame game = new MultiPlayerGame();
        game.setEmojiChat(list);
        assertEquals(list, game.getEmojiChat());

        list.add(new EmojiEntry("b", "c"));
        game.setEmojiChat(list);
        assertEquals(list, game.getEmojiChat());
    }

    @Test
    public void updateScoresTest(){
        MultiPlayerGame game = new MultiPlayerGame();
        game.setTotalScore(3);

        Player player1 = new Player(1L,"john");
        Player player2 = new Player();
        player1.setScore(30);
        player2.setScore(50);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        game.updateScores(playerList);

        assertEquals(80, game.getTotalScore());
    }

    @Test
    public void sortScoresTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L,"john");
        Player player2 = new Player();
        player1.setScore(30);
        player2.setScore(50);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        game.updateScores(playerList);

        List<Player> list2 = new ArrayList<>();
        list2.add(player2);
        list2.add(player1);

        assertEquals(list2, game.sortScores(playerList));
    }

    @Test
    public void increaseScoreTest() throws Exception {
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L,"john");
        Player player2 = new Player();
        player1.setScore(30);
        player2.setScore(50);
        game.setTimervalue(8.0);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        game.setPlayerList(playerList);
        game.updateScores(playerList);

        game.increaseScore(player1);
        game.updateScores(playerList);

        assertEquals(1080, game.getTotalScore());


    }

    @Test
    public void setAnsFlagTest() throws Exception {
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L,"john");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);

        game.setPlayerList(playerList);
        game.setAnsFlag(player1, true);

        assertTrue(player1.hasAnswered());
    }

    @Test
    public void getStatusTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        game.setStatus(GameState.NEW);
        assertEquals(GameState.NEW, game.getStatus());

        game.setStatus(GameState.STARTED);
        assertEquals(GameState.STARTED, game.getStatus());
    }

    @Test
    public void setStatusTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        game.setStatus(GameState.NEW);
        assertEquals(GameState.NEW, game.getStatus());

        game.setStatus(GameState.STARTED);
        assertEquals(GameState.STARTED, game.getStatus());
    }

    @Test
    public void returnUpdatedPlayerTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L,"john");
        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);

        game.setPlayerList(playerList);

        assertEquals(player1, game.returnUpdatedPlayer(player1));
    }

    @Test
    public void isValidTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        game.setStatus(GameState.NEW);
        assertTrue(game.isValid());
    }

    @Test
    public void isNotValidTest(){
        MultiPlayerGame game = new MultiPlayerGame();

        game.setStatus(GameState.FINISHED);
        assertFalse(game.isValid());
    }

    @Test
    public void allPlayersAnsweredTest() {
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L, "john");
        Player player2 = new Player();

        player1.setFlagAns(true);
        player2.setFlagAns(true);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        game.setPlayerList(playerList);

        assertTrue(game.allPlayersAnswered());
    }

    @Test
    public void allPlayersNotAnsweredTest() {
        MultiPlayerGame game = new MultiPlayerGame();

        Player player1 = new Player(1L, "john");
        Player player2 = new Player();

        player1.setFlagAns(true);
        player2.setFlagAns(false);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        game.setPlayerList(playerList);

        assertFalse(game.allPlayersAnswered());
    }

    @Test
    void equalsTest() {
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        MultiPlayerGame a = new MultiPlayerGame(1L, roundList, playerList);
        MultiPlayerGame b = new MultiPlayerGame(1L, roundList, playerList);

        assertEquals(a, b);
    }

    @Test
    void notEqualsTest() {
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        MultiPlayerGame a = new MultiPlayerGame(2L, roundList, playerList);
        MultiPlayerGame b = new MultiPlayerGame(1L, roundList, playerList);

        assertNotEquals(a, b);
    }

    @Test
    public void equalsHashCode(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        MultiPlayerGame a = new MultiPlayerGame(1L, roundList, playerList);
        MultiPlayerGame b = new MultiPlayerGame(1L, roundList, playerList);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void notEqualsHashCode(){
        Round r = new Round();
        List<Round> roundList = new ArrayList<>();
        roundList.add(r);

        Player player = new Player();
        List<Player> playerList = new ArrayList<>();
        playerList.add(player);

        MultiPlayerGame a = new MultiPlayerGame(2L, roundList, playerList);
        MultiPlayerGame b = new MultiPlayerGame(1L, roundList, playerList);

        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

}

