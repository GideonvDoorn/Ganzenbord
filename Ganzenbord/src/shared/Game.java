package shared;

import client.IClient;
import utils.GameLogger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;


public class Game extends UnicastRemoteObject implements IGame  {

    private transient IClient host;
    private transient IClient guest;
    private boolean hostTurn = true;
    private int roomCode;
    private boolean gameEnded;


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
    }


    @Override
    public IClient getHost() throws RemoteException {
        return host;
    }

    @Override
    public IClient getGuest() throws RemoteException {
        return guest;
    }


    public boolean getGameEnded(){
        return  gameEnded;
    }

    public int rollDice(IClient client, int currentTile) {

        try{
            if (client.isHost() && !hostTurn || !client.isHost() && hostTurn) {
                return -1;
            }
        }

        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(),Level.SEVERE);
        }

        //flip turns
        hostTurn = !hostTurn;

        Random random = new Random();
        return  currentTile + ( 1 + random.nextInt(6));
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
