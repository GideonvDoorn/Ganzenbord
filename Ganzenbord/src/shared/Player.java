package shared;

import server.Tile;

import java.io.Serializable;

public class Player implements Serializable {

    public String getName() {
        return name;
    }

    private String name;
    private Tile currentTile;

    public void setCurrentLoc(int currentLoc) {
        this.currentLoc = currentLoc;
    }

    public int getCurrentLoc() {
        return currentLoc;
    }

    private int currentLoc;

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
        currentLoc = 0;
    }

    public void moveToTile(Tile newTile){

        currentTile = newTile;
        hasMoved = true;
    }
}
