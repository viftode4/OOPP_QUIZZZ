package client.scenes.multiplayer;

import client.scenes.MainGameCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;


public class MultiplayerGameWindowCtrl implements Initializable {

    @FXML
    private ButtonBar EmojiBar;

    @FXML
    private ListView<?> EmoteDisplay;

    @FXML
    private Label NoQuestion;

    @FXML
    private Label QuestionText;

    @FXML
    private Hyperlink emote1;

    @FXML
    private Hyperlink emote2;

    @FXML
    private Hyperlink emote3;

    @FXML
    private Hyperlink emote4;

    @FXML
    private Button joker1;

    @FXML
    private Button joker2;

    @FXML
     private Button joker3;

    @FXML
    private Button leaveGame;

    @FXML
    private Label timer;

    @FXML
    private Label score;

    @FXML
    private Button answer1;

    @FXML
    private Button answer2;

    @FXML
    private Button answer3;

    //contains which answerbutton is displaying the correct answer

    private int correctAnswerPlace;

    private final ServerUtils server;

    private final MainGameCtrl mainCtrl;

    @Inject
    public MultiplayerGameWindowCtrl(ServerUtils server, MainGameCtrl mainCtrl){
        this.server = server;
        this.mainCtrl= mainCtrl;
        this.NoQuestion = new Label();
        this.emote1= new Hyperlink();
        this.emote2= new Hyperlink();
        this.emote3= new Hyperlink();
        this.emote4 = new Hyperlink();
        this.answer1 = new Button();
        this.answer2 = new Button();
        this.answer3 = new Button();
        this.correctAnswerPlace = 1;


    }
    @Override
    public void initialize(URL location, ResourceBundle resources){
        Font font  = Font.loadFont("file:client/src/main/resources/StyleSheets/OpenSansEmoji.ttf",24.0);
        this.emote1.setFont(font);
        this.emote2.setFont(font);
        this.emote3.setFont(font);
        this.emote4.setFont(font);

        this.emote1.setText(String.format("%c",0x1F606));
        this.emote2.setText(String.format("%c",0x1F618));
        this.emote3.setText(String.format("%c",0x1F605));
        this.emote4.setText(String.format("%c",0x1F602));
        this.answer1.setText("100");
        this.answer2.setText("200");
        this.answer3.setText("300");

    }
    public void quitGame(){
        mainCtrl.showMenu();
    }

    //send these to backend
    public void answerbox1(){
        //upon answering check which answer was given
        //not sure about answer format yet
        String answer = this.answer1.getText();

        //set player received after answer since it may have updated score

        mainCtrl.setPlayer(server.sendAnswer(answer, mainCtrl.getPlayer(), mainCtrl.getPlayer().getGame()));
        answerChecker();
    }
    public void answerbox2(){
        //upon answering check which answer was given
        //not sure about answer format yet
        String answer = this.answer2.getText();

        //set player received after answer since it may have updated score

        mainCtrl.setPlayer(server.sendAnswer(answer, mainCtrl.getPlayer(), mainCtrl.getPlayer().getGame()));
        answerChecker();
    }
    public void answerbox3(){
        //upon answering check which answer was given
        //not sure about answer format yet
        String answer = this.answer3.getText();

        //for testing purposes - hardcoded gameid for now
        mainCtrl.showMultiplayerLeaderboardWindow(0);
        //

        //set player received after answer since it may have updated score

        mainCtrl.setPlayer(server.sendAnswer(answer, mainCtrl.getPlayer(), mainCtrl.getPlayer().getGame()));
        answerChecker();
    }


    /**
     * Checks if the player clicked the right answer
     */
    public void answerChecker(){
        if(this.correctAnswerPlace==1){
            this.answer1.setStyle("-fx-background-color: green");
            this.answer2.setStyle("-fx-background-color: red");
            this.answer3.setStyle("-fx-background-color: red");
        }
        else if(this.correctAnswerPlace==2){
            this.answer2.setStyle("-fx-background-color: green");
            this.answer1.setStyle("-fx-background-color: red");
            this.answer3.setStyle("-fx-background-color: red");
        }else{
            this.answer3.setStyle("-fx-background-color: green");
            this.answer2.setStyle("-fx-background-color: red");
            this.answer1.setStyle("-fx-background-color: red");
        }
    }
}

