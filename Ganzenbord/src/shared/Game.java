package shared;

import server.Board;
import server.Tile;
import server.TileType;
import utils.GameLogger;

import java.util.Random;
import java.util.logging.Level;


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

        GameLogger.setLoggerLevel(Level.INFO);

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = new Board();

        host.moveToTile(gameBoard.getTileByType(TileType.START));
        guest.moveToTile(gameBoard.getTileByType(TileType.START));

        GameLogger.logMessage("Initialized game!", Level.INFO);

        startTurn();
    }

    public void startTurn() {
        turnNumber++;

        GameLogger.logMessage(String.format("start turn %s", turnNumber), Level.INFO);

        host.setMoved(false);
        guest.setMoved(false);

    }

    public boolean getGameEnded(){
        return  gameEnded;
    }

    public Tile startMove(Player player) {
        // starts moving player
        if(gameEnded){
            return gameBoard.getTileByType(TileType.END);
        }

        Random rndm = new Random();


        //get the player a new position
        int newIndex = player.getCurrentTile().getTileIndex() + ( 1 + rndm.nextInt(6));
        if(newIndex >= gameBoard.getTileByType(TileType.END).getTileIndex()){
            //player has reached the end

            //send player its new position
            GameLogger.logMessage(String.format("%s moved to the end tile!" , player.getName()), Level.INFO);
            GameLogger.logMessage(String.format("%s won!", player.getName()), Level.INFO);
            gameEnded = true;
            return gameBoard.getTileByType(TileType.END);
//
//            player.moveToTile(gameBoard.getTileByType(TileType.END));
        }

        GameLogger.logMessage(String.format("%s moved to tile %d !", player.getName(), newIndex), Level.INFO);
        return gameBoard.getTileAtIndex(newIndex);
    }
}
