package shared;

import server.Board;
import server.Tile;
import server.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Player host;
    private Player guest;
    private Board gameBoard;

    private int turnNumber = 0;
    private boolean gameEnded;

    public boolean allPlayersMoved(){
        return host.hasMoved() && guest.hasMoved();
    }


    public Game(Player host, Player guest){
        this.host = host;
        this.guest = guest;

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = getTestBoard();

        host.moveToTile(gameBoard.getTileByType(TileType.START));
        guest.moveToTile(gameBoard.getTileByType(TileType.START));

        System.out.println("Initialized game");

        startTurn();
    }

    public void startTurn() {
        turnNumber++;
        System.out.println("start turn " + turnNumber);
        host.setMoved(false);
        guest.setMoved(false);

    }

    public boolean getGameEnded(){
        return  gameEnded;
    }

    public void startMove(Player player) {
        // starts moving player
        if(gameEnded){
            return;
        }

        Random rndm = new Random();


        //get the player a new position
        int newIndex = player.getCurrentTile().getTileIndex() + ( 1 + rndm.nextInt(6));
        if(newIndex >= gameBoard.getTileByType(TileType.END).getTileIndex()){
            //player has reached the end

            //send player its new position
            player.moveToTile(gameBoard.getTileByType(TileType.END));

            System.out.println(player.getName() + " moved to the end tile!");
            System.out.println(player.getName() + " won!");

            gameEnded = true;

            return;
        }
        Tile newTile = gameBoard.getTileAtIndex(newIndex);


        //send player its new position
        player.moveToTile(newTile);


        System.out.println(player.getName() + " moved to tile " + newIndex);



    }

    private Board getTestBoard(){
        List<Tile> tiles = new ArrayList<>();

        int maxTiles = 20;
        for(int i = 0; i < maxTiles; i++) {

            if (i == 0) {
                tiles.add(new Tile(i, TileType.START));
            } else if (i == maxTiles -1) {
                tiles.add(new Tile(i, TileType.END));
            } else {
                tiles.add(new Tile(i, TileType.DEFAULT));
            }
        }
        return new Board(tiles);
    }

}
