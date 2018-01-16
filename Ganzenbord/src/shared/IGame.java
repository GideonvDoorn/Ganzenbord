package shared;

import client.IClient;
import server.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {

    boolean allPlayersMoved() throws RemoteException;
    void startTurn() throws RemoteException;
    Tile startMove(int clientID, int currentTile) throws RemoteException;
    int registerUser(IClient client) throws RemoteException;
//    int getGameID();
//    void JoinGame();
}
