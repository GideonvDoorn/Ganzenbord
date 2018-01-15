package client;

import login.ILoginManager;
import shared.IGame;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginClient {
    // Set binding name for Effectenbeurs
    private static final String bindingName = "login";

    // References to registry and Effectenbeurs
    private Registry registry = null;
    public ILoginManager loginManager = null;

    // Constructor
    public LoginClient(String ipAddress, int portNumber) {

        // Print IPData address and port number for registry
        System.out.println("Client: IPData Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IPData address and port number
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            System.out.println("Client: Registry located");
        } else {
            System.out.println("Client: Cannot locate registry");
            System.out.println("Client: Registry is null pointer");
        }


        // Bind Effectenbeurs using registry
        if (registry != null) {
            try {
                loginManager = (ILoginManager) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind loginManager");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                loginManager = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind loginManager");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                loginManager = null;
            }
        }

        // Print result binding Effectenbeurs
        if (loginManager != null) {
            System.out.println("Client: loginManager bound");
        } else {
            System.out.println("Client: loginManager is null pointer");
        }
    }
}
