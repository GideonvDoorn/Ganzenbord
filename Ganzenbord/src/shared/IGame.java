package shared;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {

    IClient getHost() throws RemoteException;
    IClient getGuest() throws RemoteException;
    int rollDice(IClient client, int currentTile) throws RemoteException;
    boolean registerUser(IClient client) throws RemoteException;
    void unRegisterUser(IClient client) throws RemoteException;
    int getRoomCode()throws RemoteException;
    boolean checkIfGameIsFull() throws RemoteException;

}
