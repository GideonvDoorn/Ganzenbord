package client;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import server.Tile;
import shared.IGame;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

public class MainGameScreenController implements Serializable{

    private static final int NORMAL_STEP = 32;

    private IClient client;


    int clientID;

    private IGame activeGame;
    private Player player;
    private Player player2;
    private double player1Startpos;
    private double player2Startpos;

    private int player1finalTile;
    private int player2finalTile;

    @FXML
    public transient Button btnRollDie;
    public transient Label lblUsername;


    public transient Circle circlePlayer1;
    public transient Circle circlePlayer2;

    public transient Pane pnlGamePanel;



    private List<Pane> panels;



    @FXML
    public void initialize() {



        //TODO: -Database, loginserver- get current user name, and put it in this label
        UITools.UIManager uiManager = new UITools.UIManager();

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
            ex.printStackTrace();
        }
    }


    @FXML
    public void btnConnect() {


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
            ex.printStackTrace();
        }
        animatePlayerToTile(player.getCurrentLoc(), player2.getCurrentLoc());
    }

    @FXML
    public void btnMove() {

        animatePlayerToTile(player.getCurrentLoc(), player2.getCurrentLoc());
    }

    public void animatePlayerToTile(int tilePlayer1, int tilePlayer2){
        if(tilePlayer1 != -1){
            circlePlayer1.setCenterX(player1Startpos + (tilePlayer1 * NORMAL_STEP));
            this.player.setCurrentLoc(tilePlayer1);

        }
        if(tilePlayer2 != -1){
            circlePlayer2.setCenterX(player2Startpos + (tilePlayer2 * NORMAL_STEP));
            this.player2.setCurrentLoc(tilePlayer2);
        }

        System.out.println("Circles should have moved!-");
    }

    public void setGameEnd(int playerWhoWonID){
        if(playerWhoWonID == clientID){
            System.out.println("Player won");
        }
        else{
            System.out.println("Player lost");

        }
    }


    public void btnQuitOnClick(){
        try {
            client.requestTerminateGame();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    public void returnToMainMenu() {
        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadFXML("MainMenuScreen.fxml"));
    }
}
