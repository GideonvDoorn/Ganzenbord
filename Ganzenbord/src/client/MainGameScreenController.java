package client;

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

        //TODO: sessies

    }

    public void setClient(IClient client){
        this.client = client;
    }


    @FXML
    public void btnConnect() {

        player = new Player("host");
        player2 = new Player("guest");

        player1Startpos = circlePlayer1.getCenterX();
        player2Startpos = circlePlayer2.getCenterX();


//        try {
//            GanzenbordClient client = new GanzenbordClient(SharedData.ip, 1099, this);
//            activeGame = client.gameServer;
//            clientID = activeGame.registerUser(client);
//
//            System.out.println(clientID);
//
//
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        }


        Registry registry = null;

//        try {
//            registry = LocateRegistry.getRegistry(SharedData.ip, 1099);
//        } catch (RemoteException ex) {
//            System.out.println("Client: Cannot locate registry");
//            System.out.println("Client: RemoteException: " + ex.getMessage());
//            registry = null;
//        }
//
//        try {
//            activeGame = (IGame) registry.lookup("game");
//        } catch (RemoteException ex) {
//            System.out.println("Client: Cannot bind game");
//            System.out.println("Client: RemoteException: " + ex.getMessage());
//            activeGame = null;
//        } catch (NotBoundException ex) {
//            System.out.println("Client: Cannot bind game");
//            System.out.println("Client: NotBoundException: " + ex.getMessage());
//            activeGame = null;
//        }
//
//        try{
//            activeGame.registerUser(this);
//        }
//        catch (RemoteException ex){
//            ex.printStackTrace();
//        }
    }

    @FXML
    public void btnRollDieOnClick(){

        try {

            Tile currentTile = player.getCurrentTile();
            Tile newTile = null;

            if(currentTile == null){
                newTile = activeGame.startMove(clientID, 0);
            }
            else{
                newTile = activeGame.startMove(clientID, player.getCurrentTile().getTileIndex());
            }

            if(newTile == null){
                return;
            }
            player.moveToTile(newTile);
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

        circlePlayer1.setCenterX(player1Startpos + (tilePlayer1 * NORMAL_STEP));
        circlePlayer2.setCenterX(player2Startpos + (tilePlayer2 * NORMAL_STEP));

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
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    public void setNewState(int newLocationPlayer1, int newLocationPlayer2) {

        this.player.setCurrentLoc(newLocationPlayer1);
        this.player2.setCurrentLoc(newLocationPlayer2);

        System.out.println("STATE PUSHED");
    }
}
