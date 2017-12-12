package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Board;
import server.Tile;
import server.TileType;


import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board testBoard;



    @BeforeEach
    void board() {
        testBoard = new Board();
    }


    @Test
    void getTileAtIndex() {

        Tile tile = new Tile(0, TileType.START);

        assertEquals(tile.getType(), testBoard.getTileAtIndex(0).getType());
        assertEquals(tile.getTileIndex(), testBoard.getTileAtIndex(0).getTileIndex());
    }

    @Test
    void getTileAtIndexNull() {

        assertEquals(null, testBoard.getTileAtIndex(-1));
    }

    @Test
    void getTileByType() {
        Tile tile = new Tile(0, TileType.START);

        assertEquals(tile.getType(), testBoard.getTileByType(TileType.START).getType());
        assertEquals(tile.getTileIndex(), testBoard.getTileByType(TileType.START).getTileIndex());
    }

    @Test
    void getTileByTypeNull() {
        assertEquals(null, testBoard.getTileByType(null));
    }

}