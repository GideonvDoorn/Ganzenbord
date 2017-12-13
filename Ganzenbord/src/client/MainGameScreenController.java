package client;

import javafx.scene.control.Label;
import shared.Game;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainGameScreenController {

    private Game activeGame;
    private Player player;
    private Player player2;

    @FXML
    public Button btnRollDie;
    public Label lblUsername;



    @FXML
    public void initialize() {

        player = new Player("host");
        player2 = new Player("guest");
        activeGame = new Game(player, player2);

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

    public void btnQuitOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

}
