package login;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILoginServer extends Remote {

    User loginUser(String username, String password) throws RemoteException;
    boolean registerUser(String username, String password) throws RemoteException;
    boolean logoutUser(String username) throws RemoteException;


}
