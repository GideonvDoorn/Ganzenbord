package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {

    void connectToServer(String ipAddress, int port) throws RemoteException;
    void setGameScreenController(MainGameScreenController ctrl) throws RemoteException;
    void setLobbyController(LobbyScreenController ctrl) throws RemoteException;
    String getUsername() throws RemoteException;
    int hostGame() throws RemoteException;
    void setUsernames(String host, String guest) throws RemoteException;
    boolean joinGame(int roomCode) throws RemoteException;
    void pushNewState(int newLocationPlayer1, int newLocationPlayer2) throws RemoteException;
    void setGameEnd(int playerWhoWonID) throws  RemoteException;
    void requestUsernamePush() throws RemoteException;
    void rollDice(IClient client, int currentLocation) throws RemoteException;
    boolean isHost() throws RemoteException;
    boolean checkIfServerIsRunning() throws RemoteException;
    boolean checkIfGameIsFull() throws RemoteException;
    void leaveGame(boolean inLobby) throws RemoteException;
    void pushTerminateGame() throws RemoteException;
    void requestTerminateGame() throws RemoteException;
    void requestStartGame() throws RemoteException;
    void pushStartGame() throws RemoteException;
}
