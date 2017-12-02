package UI;

import javafx.fxml.FXML;
import Game.*;
import javafx.scene.control.Button;

import javax.xml.bind.annotation.XmlElement;

public class MainGameScreenController {


    @FXML
    public Button btnRollDie;


    private Game activeGame;
    private Player player;
    private Player player2;

    public MainGameScreenController(){
        player = new Player("Pietje");
        player2 = new Player("Henkie");
        activeGame = new Game(player, player2);
    }

    @FXML
    public void RollDieBtnClick(){

        //Simulate game flow
        activeGame.startMove(player);
        activeGame.startMove(player2);

        if(activeGame.allPlayersMoved()){
            activeGame.startTurn();
        }

    }

}
