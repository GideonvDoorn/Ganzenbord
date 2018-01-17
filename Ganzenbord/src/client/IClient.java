package client;

import shared.IGame;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface IClient extends Remote {

    void connectToServer(String ipAddress, int port) throws RemoteException;
    void setGameScreenController(MainGameScreenController ctrl) throws RemoteException;
    void setLobbyController(LobbyScreenController ctrl) throws RemoteException;
    int getClientID() throws RemoteException;
    String getUsername() throws RemoteException;
    void hostGame() throws RemoteException;
    void setUsernames(String host, String guest) throws RemoteException;
    void joinGame(int roomCode) throws RemoteException;
    void setNewState(int newLocationPlayer1, int newLocationPlayer2) throws RemoteException;
    void setGameEnd(int playerWhoWonID) throws  RemoteException;
    void requestUsernamePush() throws RemoteException;
}
