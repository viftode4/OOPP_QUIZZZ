package client.scenes;

import client.utils.ServerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HowToPlayWindowCtrlTest {

    ServerUtils server;
    MainGameCtrl mainCtrl;
    HowToPlayWindowCtrl howToPlayCtrlTest;

    @Test
    @BeforeEach
    public void constructorTest() {
        this.server = new ServerUtils();
        this.mainCtrl = new MainGameCtrl();
        this.howToPlayCtrlTest = new HowToPlayWindowCtrl(server, mainCtrl);
        assertNotNull(howToPlayCtrlTest);
    }

}
