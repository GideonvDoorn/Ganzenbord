package client;

import shared.IGame;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GanzenbordClient extends UnicastRemoteObject implements IClient, Serializable {

    // Set binding name for Effectenbeurs
    private static final String bindingName = "game";

    // References to registry and game
    private Registry registry = null;
    public IGame game = null;


    MainGameScreenController controller;

    // Constructor
    public GanzenbordClient(String ipAddress, int portNumber, MainGameScreenController controller) throws RemoteException {
        this.controller = controller;
        // Print SharedData address and port number for registry
        System.out.println("Client: SharedData Address: " + ipAddress);
        System.out.println("Client: Port number " + portNumber);

        // Locate registry at SharedData address and port number
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


        // Bind game using registry
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
        System.out.println("Client values: " + player1 + " - " + player2);
        controller.setNewState(player1, player2);
        controller.animatePlayerToTile(player1, player2);
    }

    @Override
    public void setGameEnd(int playerWhoWonID) {
        controller.setGameEnd(playerWhoWonID);
    }
}
