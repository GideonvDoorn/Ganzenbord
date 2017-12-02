package Game;

public class Player {

    public String getName() {
        return name;
    }

    private String name;
    private Tile currentTile;

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    private boolean hasMoved;

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }


    public Player(String name){
        this.name = name;
    }

    public void MoveToTile(Tile newTile){

        currentTile = newTile;
    }
}
