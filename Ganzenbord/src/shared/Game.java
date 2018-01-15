package shared;

import client.IClient;
import server.Board;
import server.Tile;
import server.TileType;
import utils.GameLogger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;


public class Game extends UnicastRemoteObject implements IGame  {

    private Player host;
    private Player guest;
    private Board gameBoard;

    private int turnNumber = 0;
    private boolean gameEnded;

    public boolean allPlayersMoved(){
        return host.hasMoved() && guest.hasMoved();
    }


    List<IClient> users = new ArrayList<>();

    public void registerUser(IClient client){
        users.add(client);
    }

    public Game(Player host, Player guest) throws RemoteException {
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

    public Tile startMove(int clientID, int currentTile) {
        // starts moving player
        if(gameEnded){
            return gameBoard.getTileByType(TileType.END);
        }



        Random rndm = new Random();

        int newIndex = currentTile + ( 1 + rndm.nextInt(6));

        if(newIndex >= gameBoard.getTileByType(TileType.END).getTileIndex()) {
            gameEnded = true;
            return gameBoard.getTileByType(TileType.END);


        }


        if(clientID == 1){
            host.moveToTile(gameBoard.getTileAtIndex(newIndex));
        }
        else{
            guest.moveToTile(gameBoard.getTileAtIndex(newIndex));

        }
        System.out.println("Server values: " + gameBoard.getTileAtIndex(newIndex) + " - " + gameBoard.getTileAtIndex(newIndex));

        //push state to all clients
        for(IClient client : users){
            client.setNewState(6, 6);
        }


        //get the player a new position

            //player has reached the end

            //send player its new position
//            GameLogger.logMessage(String.format("%s moved to the end tile!" , player.getName()), Level.INFO);
//            GameLogger.logMessage(String.format("%s won!", player.getName()), Level.INFO);

//
//            player.moveToTile(gameBoard.getTileByType(TileType.END));




//        GameLogger.logMessage(String.format("%s moved to tile %d !", player.getName(), newIndex), Level.INFO);



        return gameBoard.getTileAtIndex(newIndex);


    }

//    @Override
//    public int getGameID() {
//        return 0;
//    }
//
//    @Override
//    public void JoinGame() {
//
//    }
}
