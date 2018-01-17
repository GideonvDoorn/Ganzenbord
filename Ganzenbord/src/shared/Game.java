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
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;


public class Game extends UnicastRemoteObject implements IGame  {

    private IClient host;
    private IClient guest;

    private Board gameBoard;

    private int roomCode;

    private int turnNumber = 0;
    private boolean gameEnded;
    private int turnID = 1;

    List<IClient> users = new ArrayList<>();

    public void registerUser(IClient client){
        if(guest != null){
            return;
        }

        guest = client;

//        System.out.println("users: " +users.size());
//        if(users.size() > 1){
//            return 0;
//        }
//        else if(users.size() > 0){
//            users.add(client);
//            return 2;
//        }
//        else{
//            users.add(client);
//            return 1;
//        }
    }

    @Override
    public int getRoomCode() {
        return roomCode;
    }

    public Game(IClient host) throws RemoteException {
        this.host = host;

        roomCode = ThreadLocalRandom.current().nextInt(1000, 9999);

        initializeGame();
    }

    private void initializeGame(){
        gameBoard = new Board();

//        host.moveToTile(gameBoard.getTileByType(TileType.START));
//        guest.moveToTile(gameBoard.getTileByType(TileType.START));


        startTurn();
    }


    @Override
    public IClient getHost() throws RemoteException {
        return host;
    }

    @Override
    public IClient getGuest() throws RemoteException {
        return guest;
    }

    @Override
    public boolean allPlayersMoved() throws RemoteException {
        return false;
    }

    public void startTurn() {
        turnNumber++;

        GameLogger.logMessage(String.format("start turn %s", turnNumber), Level.INFO);

//        host.setMoved(false);
//        guest.setMoved(false);

    }

    public boolean getGameEnded(){
        return  gameEnded;
    }

    public Tile rollDice(IClient client, int currentTile) {
        return null;



//        // starts moving player
//        if(gameEnded){
//            return gameBoard.getTileByType(TileType.END);
//        }
//
//
//        if(clientID != turnID){
//            return null;
//        }
//
//        Random rndm = new Random();
//
//        int newIndex = currentTile + ( 1 + rndm.nextInt(6));
//
//        if(newIndex >= gameBoard.getTileByType(TileType.END).getTileIndex()) {
//            gameEnded = true;
//
//            if(clientID == 1){
//                host.moveToTile(gameBoard.getTileByType(TileType.END));
//                for(IClient client : users){
//                    try{
//                        client.setNewState(gameBoard.getTileByType(TileType.END).getTileIndex(), guest.getCurrentTile().getTileIndex());
//
//                    }
//                    catch(RemoteException ex){
//
//                    }
//                }
//            }
//            else{
//                guest.moveToTile(gameBoard.getTileByType(TileType.END));
//                for(IClient client : users){
//                    try{
//                        client.setNewState(host.getCurrentTile().getTileIndex(), gameBoard.getTileByType(TileType.END).getTileIndex());
//
//                    }
//                    catch(RemoteException ex){
//
//                    }
//                }
//
//            }
//
//            for(IClient client : users){
//                try{
//                    client.setGameEnd(clientID);
//
//                }
//                catch(RemoteException ex){
//
//                }
//            }
//            return gameBoard.getTileByType(TileType.END);
//        }
//
//
//        if(clientID == 1){
//            host.moveToTile(gameBoard.getTileAtIndex(newIndex));
//            for(IClient client : users){
//                try{
//                    client.setNewState(host.getCurrentTile().getTileIndex(), guest.getCurrentTile().getTileIndex());
//
//                }
//                catch(RemoteException ex){
//
//                }
//            }
//        }
//        else{
//            guest.moveToTile(gameBoard.getTileAtIndex(newIndex));
//            for(IClient client : users){
//                try{
//                    client.setNewState(host.getCurrentTile().getTileIndex(), guest.getCurrentTile().getTileIndex());
//
//                }
//                catch(RemoteException ex){
//
//                }
//            }
//
//        }
//
//        if(turnID == 1){
//            turnID++;
//        }
//        else{
//            turnID--;
//        }
//
//
//
//        return gameBoard.getTileAtIndex(newIndex);
//
    }


}
