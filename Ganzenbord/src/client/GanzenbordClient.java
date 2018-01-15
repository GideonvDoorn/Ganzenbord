package client;

import shared.IGame;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GanzenbordClient implements IClient {

    // Set binding name for Effectenbeurs
    private static final String bindingName = "game";

    // References to registry and Effectenbeurs
    private Registry registry = null;
    public IGame game = null;

    // Constructor
    public GanzenbordClient(String ipAddress, int portNumber, MainGameScreenController controller) {

        // Print IP address and port number for registry
        System.out.println("Client: IP Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at IP address and port number
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
                game = (IGame) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind game");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                game = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind game");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                game = null;
            }
        }

        // Print result binding Effectenbeurs
        if (game != null) {
            System.out.println("Client: game bound");
        } else {
            System.out.println("Client: game is null pointer");
        }
    }

    @Override
    public void setNewState(int player1, int player2) {

    }
}
