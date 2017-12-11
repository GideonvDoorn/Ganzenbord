package server;

import java.util.List;

public class Board {

    List<Tile> tiles;

    public Board(List<Tile> tiles){
        this.tiles = tiles;
    }

    public Tile getTileAtIndex(int index){

        for (Tile tile : tiles){
            if(tile.getTileIndex() == index){
                return tile;
            }
        }

        return null;
    }

    public Tile getTileByType(TileType type){
        for (Tile tile : tiles){
            if(tile.getType() == type){
                return tile;
            }
        }

        return null;
    }


}
