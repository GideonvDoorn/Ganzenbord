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
    }

}