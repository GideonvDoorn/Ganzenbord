package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Tile;
import server.TileType;
import shared.Player;

import static org.junit.jupiter.api.Assertions.*;

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
    void hasMoved() {

        assertEquals(false, testPlayer.hasMoved());
    }

    @Test
    void setMoved() {
        testPlayer.setMoved(true);
        assertEquals(true, testPlayer.hasMoved());
    }

    @Test
    void getCurrentTile() {
        assertEquals(null, testPlayer.getCurrentTile());
    }

    @Test
    void moveToTile() {
        Tile testTile = new Tile(1, TileType.DEFAULT);
        testPlayer.moveToTile(testTile);
        assertEquals(testTile, testPlayer.getCurrentTile());
    }

}