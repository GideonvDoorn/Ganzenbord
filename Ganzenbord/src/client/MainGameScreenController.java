package client;

import com.sun.deploy.util.SessionState;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import server.Tile;
import server.TileType;
import shared.IGame;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utils.GameLogger;
import utils.IPData;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;

public class MainGameScreenController implements IClient, Serializable {

    private static final int NORMAL_STEP = 32;

    int clientID;

    int directionState = 0;
    int directionState2 = 0;

    public boolean readyToUpdate = false;


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

        player = new Player("host");
        player2 = new Player("guest");

        //TODO: -Database, loginserver- get current user name, and put it in this label
        UITools.UIManager uiManager = new UITools.UIManager();

        lblUsername.setText(UITools.loggedInUser.getUsername());

        //TODO: sessies
        clientID = 1;
    }

    @FXML
    public void btnConnect() {

        player1Startpos = circlePlayer1.getCenterX();
        player2Startpos = circlePlayer2.getCenterX();

        GanzenbordClient client = new GanzenbordClient(IPData.ip, 1099, this);

        activeGame = client.game;

        try{
            activeGame.registerUser(client);

        }
        catch (RemoteException ex){
            ex.printStackTrace();
        }
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
            player.moveToTile(newTile);
        }
        catch(RemoteException ex) {
            ex.printStackTrace();
        }



//        //Simulate game flow
//        try{
//            Tile currentTile = player.getCurrentTile();
//            Tile newTile = null;
//
//            if(currentTile == null){
//                newTile = activeGame.startMove(0);
//            }
//            else{
//                newTile = activeGame.startMove(currentTile.getTileIndex());
//            }
//
//            if(newTile.getType() == TileType.END){
//                GameLogger.logMessage("Player won", Level.INFO);
//            }
//
//            player.moveToTile(newTile);
//
//
//            animatePlayerToTile(newTile, currentTile, false);
//
////            if(activeGame.getGameEnded()){
////                return;
////            }
//
//            if(activeGame.allPlayersMoved()){
//                activeGame.startTurn();
//            }
//        }
//        catch(RemoteException ex){
//            ex.printStackTrace();
//        }

    }
    @FXML
    public void btnMove() {

        animatePlayerToTile(player1finalTile, player2finalTile);
    }
//    private void animatePlayerToTile(Tile tileToMoveTo, Tile currentTile, boolean player2){
//
//        int currentIteratorIndex = 0;
//        if(currentTile == null){
//
//        }
//        else{
//            currentIteratorIndex = currentTile.getTileIndex();
//        }
//
//        if(!player2){
//
//            for(int i = 0; i < tileToMoveTo.getTileIndex() - currentIteratorIndex; i++){
//
//
//
//                if(currentIteratorIndex == 10){
//                    directionState = 1;
//                }
//
//                if(currentIteratorIndex == 15){
//                    directionState = 2;
//                }
//                if(directionState == 0){
//                    circlePlayer1.setCenterX(circlePlayer1.getCenterX() + NORMAL_STEP);
//                }
//                else if(directionState == 1){
//                    circlePlayer1.setCenterY(circlePlayer1.getCenterY() + NORMAL_STEP * -1);
//                }
//                else if(directionState == 2){
//                    circlePlayer1.setCenterX(circlePlayer1.getCenterX() + NORMAL_STEP * -1);
//                }
//                currentIteratorIndex++;
//
//            }
//        }
//        else{
//
//            for(int i = 0; i < tileToMoveTo.getTileIndex() - currentTile.getTileIndex(); i++){
//
//                if(currentIteratorIndex == 9){
//                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP);
//
//                }
//                if(currentIteratorIndex == 10){
//                    directionState2 = 1;
//                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);
//
//                }
//                if(currentIteratorIndex == 14){
//                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);
//
//                }
//                if(currentIteratorIndex == 15){
//                    directionState2 = 2;
//                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP * -1);
//
//                }
//
//                if(directionState2 == 0){
//                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP);
//                }
//                else if(directionState2 == 1){
//                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);
//                }
//                else if(directionState2 == 2){
//                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP * -1);
//                }
//                currentIteratorIndex++;
//            }
//        }
//    }

    private void animatePlayerToTile(int tilePlayer1, int tilePlayer2){

        circlePlayer1.setCenterX(30);
        circlePlayer1.setCenterX(player1Startpos + (tilePlayer1 * NORMAL_STEP));
        circlePlayer2.setCenterX(player2Startpos + (tilePlayer1 * NORMAL_STEP));
        System.out.println("Circles moved!");
        System.out.println("Circles moved!");
        System.out.println("Circles moved!-");
    }


    public void btnQuitOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    @Override
    public void setNewState(int player1, int player2) {



        player1finalTile = player1;
        player2finalTile = player2;
        System.out.println("STATE PUSHED");
    }
}
