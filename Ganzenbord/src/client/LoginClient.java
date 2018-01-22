package client;

import login.ILoginManager;
import utils.GameLogger;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class LoginClient {
    // Set binding name for loginServer
    private static final String BINDING_NAME = "LOGIN";

    public ILoginManager getLoginManager() {
        return loginManager;
    }

    ILoginManager loginManager = null;

    // Constructor
    public LoginClient(String ipAddress, int portNumber) {



        // Locate registry at SharedData address and port number
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            registry = null;
        }

        // Bind loginServer using registry
        if (registry != null) {
            try {
                loginManager = (ILoginManager) registry.lookup(BINDING_NAME);
            } catch (RemoteException | NotBoundException ex) {

                loginManager = null;
            }
        }

        // Print result binding loginServer
        if (loginManager != null) {
            GameLogger.logMessage("Client: loginManager bound", Level.INFO);
        } else {
            GameLogger.logMessage("Client: loginManager is null", Level.SEVERE);
        }
    }
}
