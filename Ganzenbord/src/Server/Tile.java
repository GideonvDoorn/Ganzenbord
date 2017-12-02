package Server;

public class Tile {


    private int tileIndex;
    private TileType type;

    public int getTileIndex() {
        return tileIndex;
    }

    public TileType getType() {
        return type;
    }

    public Tile(int index, TileType type){
        this.type = type;
        tileIndex = index;
    }
}
