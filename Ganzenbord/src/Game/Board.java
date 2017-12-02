package Game;

import java.util.List;

public class Board {

    List<Tile> tiles;

    public Board(List<Tile> tiles){
        this.tiles = tiles;
    }

    public Tile getTileAtIndex(int index){

        for (Tile tile : tiles){
            if(tile.tileIndex == index){
                return tile;
            }
        }

        return null;
    }

}
