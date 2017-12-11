package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Game;
import shared.Player;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {


    Game testGame;
    Player testHost;
    Player testGuest;
    @BeforeEach
    void game(){
        testHost = new Player("testHost");
        testGuest = new Player("testGuest");
        testGame = new Game(testHost, testGuest );
    }

    @Test
    void allPlayersMovedTrue() {
        testHost.setMoved(true);
        testGuest.setMoved(true);

        assertEquals(true, testGame.allPlayersMoved());
    }

    @Test
    void allPlayersMovedFalse() {
        testHost.setMoved(false);
        testGuest.setMoved(true);
        assertEquals(false, testGame.allPlayersMoved());

        testHost.setMoved(true);
        testGuest.setMoved(false);
        assertEquals(false, testGame.allPlayersMoved());

        testHost.setMoved(false);
        testGuest.setMoved(false);
        assertEquals(false, testGame.allPlayersMoved());
    }

    @Test
    void startTurn() {
        testGame.startTurn();
    }

    @Test
    void getGameEnded() {
        assertEquals(false, testGame.getGameEnded());
    }

    @Test
    void startMove() {


        while(true){
            //Simulate game flow
            testGame.startMove(testHost);
            testGame.startMove(testGuest);

            if(testGame.getGameEnded()){
                break;
            }

            if(testGame.allPlayersMoved()){
                testGame.startTurn();
            }
        }

    }

}