package client;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utils.GameLogger;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;

public class MainGameScreenController implements Serializable{

    private static final int NORMAL_STEP = 32;

    private IClient client;
    private Player player;
    private Player player2;
    private double player1Startpos;
    private double player2Startpos;

    @FXML
    public transient Button btnRollDie;
    public transient Label lblUsername;
    public transient Circle circlePlayer1;
    public transient Circle circlePlayer2;





    @FXML
    public void initialize() {

        lblUsername.setText(UITools.loggedInUser.getUsername());

        player = new Player("host");
        player2 = new Player("guest");

        player1Startpos = circlePlayer1.getCenterX();
        player2Startpos = circlePlayer2.getCenterX();


    }

    public void setClient(IClient client){
        this.client = client;

        try{
            client.setGameScreenController(this);

        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @FXML
    public void btnRollDieOnClick(){

        try {

            if(client.isHost()){
                client.rollDice(client, player.getCurrentLoc());
            }
            else{
                client.rollDice(client, player2.getCurrentLoc());
            }


        }
        catch(RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
        animatePlayerToTile(player.getCurrentLoc(), player2.getCurrentLoc());
    }


    void animatePlayerToTile(int tilePlayer1, int tilePlayer2){
        if(tilePlayer1 != -1){
            circlePlayer1.setCenterX(player1Startpos + (tilePlayer1 * NORMAL_STEP));
            this.player.setCurrentLoc(tilePlayer1);

        }
        if(tilePlayer2 != -1){
            circlePlayer2.setCenterX(player2Startpos + (tilePlayer2 * NORMAL_STEP));
            this.player2.setCurrentLoc(tilePlayer2);
        }
    }

    void setGameEnd(int playerWhoWonID){
//        if(playerWhoWonID == clientID){
//            GameLogger.logMessage("Player won!", Level.INFO);
//        }
//        else{
//            GameLogger.logMessage("Player lost", Level.INFO);
//
//        }
    }


    public void btnQuitOnClick(){
        try {
            client.requestTerminateGame();
        } catch (RemoteException e) {
            GameLogger.logMessage(e.getMessage(), Level.SEVERE);
        }
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    void returnToMainMenu() {
        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadFXML("MainMenuScreen.fxml"));
    }
}
