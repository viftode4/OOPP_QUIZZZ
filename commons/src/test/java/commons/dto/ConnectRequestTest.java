package commons.dto;


import commons.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ConnectRequestTest {

    private ConnectRequest connectRequest;

    @BeforeEach
    public void setup() {
        this.connectRequest = new ConnectRequest(new Player(2L,"mecho"),3);
    }

    @Test
    void getGameId() {
        Assertions.assertEquals(3,connectRequest.getGameId());
    }

    @Test
    void getPlayer() {
        Assertions.assertEquals(new Player(2L,"mecho"),connectRequest.getPlayer());
    }

    @Test
    void setGameId() {
        connectRequest.setGameId(56);
        Assertions.assertEquals(56,connectRequest.getGameId());
    }

    @Test
    void setPlayer() {
        connectRequest.setPlayer(new Player(2L,"moro"));
        Assertions.assertEquals(new Player(2L,"moro"),connectRequest.getPlayer());
    }
}