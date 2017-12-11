package Shared;

import Server.Board;
import Server.Tile;
import Server.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Player host;
    private Player quest;
    private Board gameBoard;

    private int turnNumber = 0;
    private boolean gameEnded;

    public boolean allPlayersMoved(){
        return host.hasMoved() && quest.hasMoved();
    }


    public Game(Player host, Player quest){
        this.host = host;
        this.quest = quest;

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = getTestBoard();

        host.setCurrentTile(gameBoard.getTileByType(TileType.Start));
        quest.setCurrentTile(gameBoard.getTileByType(TileType.Start));

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
        quest.setMoved(false);

    }

    public void startMove(Player player) {
        // starts moving player
        if(gameEnded){
            return;
        }

        Random rndm = new Random();


        //get the player a new position
        int newIndex = player.getCurrentTile().getTileIndex() + ( 1 + rndm.nextInt(6));
        if(newIndex >= gameBoard.getTileByType(TileType.End).getTileIndex()){
            //player has reached the end

            //send player its new position
            player.moveToTile(gameBoard.getTileByType(TileType.End));

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
