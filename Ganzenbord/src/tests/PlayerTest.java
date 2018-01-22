package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private Player testPlayer;

    @BeforeEach
    void player(){
        testPlayer = new Player("testName");
    }

    @Test
    void getName() {
       assertEquals("testName", testPlayer.getName());
    }

    @Test
    void getCurrentLoc(){
        Assertions.assertEquals(0, testPlayer.getCurrentLoc());
    }
    @Test
    void setCurrentLoc(){
        testPlayer.setCurrentLoc(1);
        Assertions.assertEquals(1, testPlayer.getCurrentLoc());
    }

}