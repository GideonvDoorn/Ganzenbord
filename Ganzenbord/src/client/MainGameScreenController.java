package client;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import server.Tile;
import shared.Game;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGameScreenController {

    private static final int NORMAL_STEP = 30;
    private static final int CORNER_STEP = 50;

    private Game activeGame;
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
        activeGame = new Game(player, player2);

        panels = new ArrayList<>(Arrays.asList(pnlStart, pnl1, pnl2, pnl3, pnl4, pnl5, pnl6, pnl7, pnl8, pnl9, pnl10, pnl11, pnl12, pnl13, pnl14, pnl15, pnl16, pnl17, pnl18, pnl19, pnl20, pnl21, pnl22, pnl23, pnl24, pnlEnd));


        animatePlayerToTile();

        //TODO: -Database, loginserver- get current user name, and put it in this label
        lblUsername.setText("Gideon -test");
    }

    @FXML
    public void btnRollDieOnClick(){

        //Simulate game flow
        activeGame.startMove(player);
        activeGame.startMove(player2);

        if(activeGame.getGameEnded()){
            return;
        }

        if(activeGame.allPlayersMoved()){
            activeGame.startTurn();
        }

    }


    private void animatePlayerToTile(){
        circlePlayer1.setCenterX(circlePlayer1.getCenterX() + NORMAL_STEP);


//        circlePlayer1.setCenterY(circlePlayer1.getCenterY() + 50);
    }

    public void btnQuitOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

}
