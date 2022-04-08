package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

public class HowToPlayWindowCtrl implements Initializable {

    private final ServerUtils server;
    private final MainGameCtrl mainCtrl;

    @FXML
    private Button backToMain;

    @FXML
    private ImageView joker1;

    @FXML
    private ImageView joker2;

    @FXML
    private ImageView joker3;

    @FXML
    private ImageView left;

    @FXML
    private ImageView right;

    /**
     * This is the constructor method for the controller class of the
     * "How To Play Window"
     * @param server
     * @param mainCtrl
     */
    @Inject
    public HowToPlayWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.joker1 = new ImageView();
        this.joker2 = new ImageView();
        this.joker3 = new ImageView();
        this.left = new ImageView();
        this.right = new ImageView();
    }

    /**
     * This method runs when the program is first executed.
     * It loads some images and icons into the page.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.joker1.setImage(new Image("@../../assets/2x_joker.png"));
        this.joker2.setImage(new Image("@../../assets/speed_joker.png"));
        this.joker3.setImage(new Image("@../../assets/answer_joker.png"));
        this.left.setImage(new Image("@../../assets/filler3.png"));
        this.right.setImage(new Image("@../../assets/filler3.png"));
    }

    public void jumpToMenu() {
        mainCtrl.showMenu();
    }



}
