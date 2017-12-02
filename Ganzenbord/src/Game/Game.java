package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Player host;
    private Player player2;
    private Board gameBoard;

    private int turnNumber = 0;


    public Game(Player host, Player player2){
        this.host = host;
        this.player2 = player2;

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = getTestBoard();

        System.out.println("Initialized game");

        startTurn();
    }

    public void startTurn() {
        turnNumber++;
        System.out.println("start turn " + turnNumber);



        startMove(host);
        startMove(player2);

    }

    private void startMove(Player player) {
        Random rndm = new Random();

        int newIndex = player.getCurrentTile().getTileIndex() + rndm.nextInt(7);
        Tile newTile = gameBoard.getTileAtIndex(newIndex);
        player.MoveToTile(newTile);

    }

    private Board getTestBoard(){
        List<Tile> tiles = new ArrayList<Tile>();
        for(int i = 0; i < 10; i++) {

            if (i == 0) {
                tiles.add(new Tile(i, TileType.Start));
            } else if (i == 9) {
                tiles.add(new Tile(i, TileType.End));
            } else {
                tiles.add(new Tile(i, TileType.Default));
            }

        }
        return new Board(tiles);
    }

}
