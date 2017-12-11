package shared;

import server.Tile;

public class Player {

    public String getName() {
        return name;
    }

    private String name;
    private Tile currentTile;

    private boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }


    public Player(String name){
        this.name = name;
    }

    public void moveToTile(Tile newTile){

        currentTile = newTile;
        hasMoved = true;
    }
}
