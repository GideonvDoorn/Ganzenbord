package client;

import shared.Game;
import shared.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainGameScreenController {


    @FXML
    public Button btnRollDie;


    private Game activeGame;
    private Player player;
    private Player player2;

    public MainGameScreenController(){
        player = new Player("host");
        player2 = new Player("guest");
        activeGame = new Game(player, player2);
    }

    @FXML
    public void rollDieBtnClick(){

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

}
