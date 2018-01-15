package client;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import server.Tile;
import server.TileType;
import shared.Game;
import shared.IGame;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utils.GameLogger;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class MainGameScreenController implements IClient {

    private static final int NORMAL_STEP = 32;

    int directionState = 0;
    int directionState2 = 0;

    private IGame activeGame;
    private Player player;
    private Player player2;

    @FXML
    public Button btnRollDie;
    public Label lblUsername;

    public Circle circlePlayer1;
    public Circle circlePlayer2;

    public Pane pnlGamePanel;

    public Pane pnlStart;
    public Pane pnl1;
    public Pane pnl2;
    public Pane pnl3;
    public Pane pnl4;
    public Pane pnl5;
    public Pane pnl6;
    public Pane pnl7;
    public Pane pnl8;
    public Pane pnl9;
    public Pane pnl10;
    public Pane pnl11;
    public Pane pnl12;
    public Pane pnl13;
    public Pane pnl14;
    public Pane pnl15;
    public Pane pnl16;
    public Pane pnl17;
    public Pane pnl18;
    public Pane pnl19;
    public Pane pnl20;
    public Pane pnl21;
    public Pane pnl22;
    public Pane pnl23;
    public Pane pnl24;
    public Pane pnlEnd;

    private List<Pane> panels;


    @FXML
    public void initialize() {




        player = new Player("host");
        player2 = new Player("guest");
//        activeGame = new Game(player, player2);

        panels = new ArrayList<>(Arrays.asList(pnlStart, pnl1, pnl2, pnl3, pnl4, pnl5, pnl6, pnl7, pnl8, pnl9, pnl10, pnl11, pnl12, pnl13, pnl14, pnl15, pnl16, pnl17, pnl18, pnl19, pnl20, pnl21, pnl22, pnl23, pnl24, pnlEnd));

        //TODO: -Database, loginserver- get current user name, and put it in this label
        UITools.UIManager uiManager = new UITools.UIManager();

        lblUsername.setText(UITools.loggedInUser.getUsername());
    }

    @FXML
    public void btnConnect() {

        GanzenbordClient client = new GanzenbordClient("145.93.76.47", 1099);

        activeGame = client.game;
    }

    @FXML
    public void btnRollDieOnClick(){

        //Simulate game flow
        try{
            Tile currentTile = player.getCurrentTile();
            Tile newTile = null;

            if(currentTile == null){
                newTile = activeGame.startMove(0);
            }
            else{
                newTile = activeGame.startMove(currentTile.getTileIndex());
            }

            if(newTile.getType() == TileType.END){
                GameLogger.logMessage("Player won", Level.INFO);
            }

            player.moveToTile(newTile);

            animatePlayerToTile(newTile, currentTile, false);

//            if(activeGame.getGameEnded()){
//                return;
//            }

            if(activeGame.allPlayersMoved()){
                activeGame.startTurn();
            }
        }
        catch(RemoteException ex){
            ex.printStackTrace();
        }

    }


    private void animatePlayerToTile(Tile tileToMoveTo, Tile currentTile, boolean player2){

        int currentIteratorIndex = 0;
        if(currentTile == null){

        }
        else{
            currentIteratorIndex = currentTile.getTileIndex();
        }

        if(!player2){

            for(int i = 0; i < tileToMoveTo.getTileIndex() - currentIteratorIndex; i++){



                if(currentIteratorIndex == 10){
                    directionState = 1;
                }

                if(currentIteratorIndex == 15){
                    directionState = 2;
                }
                if(directionState == 0){
                    circlePlayer1.setCenterX(circlePlayer1.getCenterX() + NORMAL_STEP);
                }
                else if(directionState == 1){
                    circlePlayer1.setCenterY(circlePlayer1.getCenterY() + NORMAL_STEP * -1);
                }
                else if(directionState == 2){
                    circlePlayer1.setCenterX(circlePlayer1.getCenterX() + NORMAL_STEP * -1);
                }
                currentIteratorIndex++;

            }
        }
        else{

            for(int i = 0; i < tileToMoveTo.getTileIndex() - currentTile.getTileIndex(); i++){

                if(currentIteratorIndex == 9){
                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP);

                }
                if(currentIteratorIndex == 10){
                    directionState2 = 1;
                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);

                }
                if(currentIteratorIndex == 14){
                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);

                }
                if(currentIteratorIndex == 15){
                    directionState2 = 2;
                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP * -1);

                }

                if(directionState2 == 0){
                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP);
                }
                else if(directionState2 == 1){
                    circlePlayer2.setCenterY(circlePlayer2.getCenterY() + NORMAL_STEP * -1);
                }
                else if(directionState2 == 2){
                    circlePlayer2.setCenterX(circlePlayer2.getCenterX() + NORMAL_STEP * -1);
                }
                currentIteratorIndex++;
            }
        }
    }

    public void btnQuitOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    @Override
    public void setNewState(int player1, int player2) {

    }
}
