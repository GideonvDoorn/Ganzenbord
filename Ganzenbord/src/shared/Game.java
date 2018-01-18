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
    private boolean hostTurn = true;

    private Board gameBoard;

    private int roomCode;

    private int turnNumber = 0;
    private boolean gameEnded;
    private int turnID = 1;


    public boolean checkIfGameIsFull() {
        return host != null && guest != null;
    }

    public void registerUser(IClient client){
        if(guest != null){
            return;
        }

        guest = client;
    }

    @Override
    public void unRegisterUser(IClient client) throws RemoteException {

        guest = null;
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

    public int rollDice(IClient client, int currentTile) {

        try{
            if(client.isHost() && !hostTurn){
                return -1;
            }
            else if(!client.isHost() && hostTurn){
                return -1;
            }
        }

        catch(RemoteException ex){
            ex.printStackTrace();
        }

        //flip turns
        hostTurn = !hostTurn;

        Random random = new Random();
        return  currentTile + ( 1 + random.nextInt(6));
    }
}
