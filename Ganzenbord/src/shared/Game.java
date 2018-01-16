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
    private int turnID = 1;

    public boolean allPlayersMoved(){
        return host.hasMoved() && guest.hasMoved();
    }


    List<IClient> users = new ArrayList<>();

    public int registerUser(IClient client){

        System.out.println("users: " +users.size());
        if(users.size() > 1){
            return 0;
        }
        else if(users.size() > 0){
            users.add(client);
            return 2;
        }
        else{
            users.add(client);
            return 1;
        }
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


        if(clientID != turnID){
            return null;
        }

        Random rndm = new Random();

        int newIndex = currentTile + ( 1 + rndm.nextInt(6));

        if(newIndex >= gameBoard.getTileByType(TileType.END).getTileIndex()) {
            gameEnded = true;

            if(clientID == 1){
                host.moveToTile(gameBoard.getTileByType(TileType.END));
                for(IClient client : users){
                    try{
                        client.setNewState(gameBoard.getTileByType(TileType.END).getTileIndex(), guest.getCurrentTile().getTileIndex());

                    }
                    catch(RemoteException ex){

                    }
                }
            }
            else{
                guest.moveToTile(gameBoard.getTileByType(TileType.END));
                for(IClient client : users){
                    try{
                        client.setNewState(host.getCurrentTile().getTileIndex(), gameBoard.getTileByType(TileType.END).getTileIndex());

                    }
                    catch(RemoteException ex){

                    }
                }

            }

            for(IClient client : users){
                try{
                    client.setGameEnd(clientID);

                }
                catch(RemoteException ex){

                }
            }
            return gameBoard.getTileByType(TileType.END);
        }


        if(clientID == 1){
            host.moveToTile(gameBoard.getTileAtIndex(newIndex));
            for(IClient client : users){
                try{
                    client.setNewState(host.getCurrentTile().getTileIndex(), guest.getCurrentTile().getTileIndex());

                }
                catch(RemoteException ex){

                }
            }
        }
        else{
            guest.moveToTile(gameBoard.getTileAtIndex(newIndex));
            for(IClient client : users){
                try{
                    client.setNewState(host.getCurrentTile().getTileIndex(), guest.getCurrentTile().getTileIndex());

                }
                catch(RemoteException ex){

                }
            }

        }

        if(turnID == 1){
            turnID++;
        }
        else{
            turnID--;
        }



        return gameBoard.getTileAtIndex(newIndex);


    }
}
