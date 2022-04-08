package commons.dto;

import commons.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class PlayerAnswerTest {

    private PlayerAnswer playerAnswer;

    @BeforeEach
    public void init() {
        this.playerAnswer = new PlayerAnswer(new Player(1L,"gosho"),2,8);
    }

    @Test
    void getPlayer() {
        Assertions.assertEquals(new Player(1L,"gosho"),playerAnswer.getPlayer());
    }

    @Test
    void getAnswer() {
        Assertions.assertEquals(2,playerAnswer.getAnswer());
    }

    @Test
    void getGameId() {
        Assertions.assertEquals(8,playerAnswer.getGameId());
    }

    @Test
    void setPlayer() {
        playerAnswer.setPlayer(new Player(1L,"tosho"));
        Assertions.assertEquals(new Player(1L,"tosho"),playerAnswer.getPlayer());
    }

    @Test
    void setAnswer() {
        playerAnswer.setAnswer(89);
        Assertions.assertEquals(89,playerAnswer.getAnswer());
    }

    @Test
    void setGameId() {
        playerAnswer.setGameId(89);
        Assertions.assertEquals(89,playerAnswer.getGameId());
    }
}