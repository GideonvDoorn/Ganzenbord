package shared;

import client.IClient;
import server.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {

    IClient getHost() throws RemoteException;
    IClient getGuest() throws RemoteException;
    boolean allPlayersMoved() throws RemoteException;
    void startTurn() throws RemoteException;
    int rollDice(IClient client, int currentTile) throws RemoteException;
    void registerUser(IClient client) throws RemoteException;
    int getRoomCode()throws RemoteException;

}
