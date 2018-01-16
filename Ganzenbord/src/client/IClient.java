package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface IClient extends Remote {

    void setNewState(int player1, int player2) throws RemoteException;
    void setGameEnd(int playerWhoWonID) throws  RemoteException;
}
