package tests;

import shared.Tile;
import shared.TileType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


class TileTest {

    private Tile testTile;

    @BeforeEach
    void tile(){
        testTile = new Tile(1, TileType.DEFAULT);
    }

    @Test
    void getTileIndex() {
        assertEquals(1, testTile.getTileIndex());
    }

    @Test
    void getType() {
        assertEquals(TileType.DEFAULT, testTile.getType());
    }

}