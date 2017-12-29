package server;

import java.util.ArrayList;
import java.util.List;

public class Board {

    List<Tile> tiles;

    public Board(){
        tiles = new ArrayList<>();
        generateBoard();

    }

    private void generateBoard(){

        int maxTiles = 26;
        for(int i = 0; i < maxTiles; i++) {

            if (i == 0) {
                tiles.add(new Tile(i, TileType.START));
            } else if (i == maxTiles -1) {
                tiles.add(new Tile(i, TileType.END));
            } else {
                tiles.add(new Tile(i, TileType.DEFAULT));
            }
        }
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
