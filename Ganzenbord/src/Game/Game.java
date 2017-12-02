package Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Player host;
    private Player player2;
    private Board gameBoard;

    private int turnNumber = 0;
    private boolean gameEnded;

    public boolean allPlayersMoved(){
        return host.hasMoved() && player2.hasMoved();
    }


    public Game(Player host, Player player2){
        this.host = host;
        this.player2 = player2;

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = getTestBoard();

        host.setCurrentTile(gameBoard.getTileByType(TileType.Start));
        player2.setCurrentTile(gameBoard.getTileByType(TileType.Start));

        System.out.println("Initialized game");

        startTurn();
    }

    public void startTurn() {
        if(gameEnded){
            return;
        }

        turnNumber++;
        System.out.println("start turn " + turnNumber);
        host.setMoved(false);
        player2.setMoved(false);

    }

    public void startMove(Player player) {

        if(gameEnded){
            return;
        }

        Random rndm = new Random();

        int newIndex = player.getCurrentTile().getTileIndex() + rndm.nextInt(7);
        if(newIndex >= gameBoard.getTileByType(TileType.End).getTileIndex()){
            //player has reached the end

            player.MoveToTile(gameBoard.getTileByType(TileType.End));
            player.setMoved(true);

            System.out.println(player.getName() + " moved to the end tile!");
            System.out.println(player.getName() + " won!");

            gameEnded = true;

            return;
        }
        Tile newTile = gameBoard.getTileAtIndex(newIndex);
        player.MoveToTile(newTile);
        player.setMoved(true);

        System.out.println(player.getName() + " moved to tile " + newIndex);



    }

    private Board getTestBoard(){
        List<Tile> tiles = new ArrayList<Tile>();

        int maxTiles = 20;
        for(int i = 0; i < maxTiles; i++) {

            if (i == 0) {
                tiles.add(new Tile(i, TileType.Start));
            } else if (i == maxTiles -1) {
                tiles.add(new Tile(i, TileType.End));
            } else {
                tiles.add(new Tile(i, TileType.Default));
            }
        }
        return new Board(tiles);
    }

}
