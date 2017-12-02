package Game;

public class Player {

    private Tile currentTile;

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }


    public Player(Tile currentTile){
        this.currentTile = currentTile;
    }

    public void MoveToTile(Tile newTile){

        currentTile = newTile;
    }
}
