package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameServer  extends Remote {

    int hostGame(IClient client) throws RemoteException;
    boolean joinGame(int roomCode, IClient client) throws RemoteException;
    void pushLobbyUsernames() throws RemoteException;
    void rollDice(IClient client, int currentLocation) throws RemoteException;
    boolean checkIfGameIsFull() throws RemoteException;
    void leaveGame(IClient client, boolean inLobby) throws RemoteException;
    void terminateGame() throws RemoteException;
    void startGame() throws RemoteException;
}
