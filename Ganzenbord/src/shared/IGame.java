package shared;

import server.Tile;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGame extends Remote {

    boolean allPlayersMoved() throws RemoteException;
    void startTurn() throws RemoteException;
    Tile startMove(int currentTile) throws RemoteException;
//    int getGameID();
//    void JoinGame();
}
