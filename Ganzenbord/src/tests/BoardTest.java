package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Board;
import server.Tile;
import server.TileType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board testBoard;
    private Tile tile0;
    private Tile tile1;



    @BeforeEach
    void board() {

        List<Tile> tiles = new ArrayList<>();

        tile0 = new Tile(0, TileType.START);
        tile1 = new Tile(1, TileType.DEFAULT);

        tiles.add(tile0);
        tiles.add(tile1);


        testBoard = new Board(tiles);
    }


    @Test
    void getTileAtIndex() {

        assertEquals(tile1, testBoard.getTileAtIndex(1));
    }

    @Test
    void getTileAtIndexNull() {

        assertEquals(null, testBoard.getTileAtIndex(2));
    }

    @Test
    void getTileByType() {
        assertEquals(tile0, testBoard.getTileByType(TileType.START));
    }

    @Test
    void getTileByTypeNull() {
        assertEquals(null, testBoard.getTileByType(TileType.END));
    }

}