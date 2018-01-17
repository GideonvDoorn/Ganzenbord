package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameServer  extends Remote {

    int hostGame(IClient client) throws RemoteException;
    void joinGame(int roomCode, IClient client) throws RemoteException;
    void pushLobbyUsernames() throws RemoteException;
    int rollDice() throws RemoteException;
}
