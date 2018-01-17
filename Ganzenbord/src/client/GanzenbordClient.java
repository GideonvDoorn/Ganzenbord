package client;

import server.IGameServer;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GanzenbordClient extends UnicastRemoteObject implements IClient, Serializable {

    private int clientID;

    // Set binding name for Effectenbeurs
    private static final String bindingName = "server";

    // References to registry and game
    private Registry registry = null;
    public IGameServer gameServer = null;

    LobbyScreenController lobbyController;
    MainGameScreenController gameScreenController;

    // Constructor
    public GanzenbordClient() throws RemoteException {

    }


    @Override
    public void connectToServer(String ipAddress, int portNumber) {

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
                gameServer = (IGameServer) registry.lookup(bindingName);
            } catch (RemoteException ex) {
                System.out.println("Client: Cannot bind game");
                System.out.println("Client: RemoteException: " + ex.getMessage());
                gameServer = null;
            } catch (NotBoundException ex) {
                System.out.println("Client: Cannot bind game");
                System.out.println("Client: NotBoundException: " + ex.getMessage());
                gameServer = null;
            }
        }

        // Print result binding Effectenbeurs
        if (gameServer != null) {
            System.out.println("Client: game bound");
        } else {
            System.out.println("Client: game is null pointer");
        }
    }

    @Override
    public void setGameScreenController(MainGameScreenController ctrl) {
        this.gameScreenController = ctrl;
    }

    @Override
    public void setLobbyController(LobbyScreenController ctrl) throws RemoteException {
        this.lobbyController = ctrl;
    }

    @Override
    public int getClientID() {
        return 0;
    }

    @Override
    public String getUsername() throws RemoteException {
        return UITools.loggedInUser.getUsername();
    }

    @Override
    public void hostGame() {
        if(gameServer == null){
            return;
        }

        try{
            gameServer.hostGame(this);
        }
        catch(RemoteException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void joinGame(int roomCode) {
        if(gameServer == null){
            return;
        }

        try{
            gameServer.joinGame(roomCode, this);
        }
        catch(RemoteException ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void setNewState(int newLocationPlayer1, int newLocationPlayer2) {
        System.out.println("Client values: " + newLocationPlayer1 + " - " + newLocationPlayer2);
        gameScreenController.setNewState(newLocationPlayer1, newLocationPlayer2);
        gameScreenController.animatePlayerToTile(newLocationPlayer1, newLocationPlayer2);
    }


    @Override
    public void setUsernames(String host, String guest) {
        lobbyController.setUsernames(host, guest);
    }


    @Override
    public void setGameEnd(int playerWhoWonID) {
gameScreenController.setGameEnd(playerWhoWonID);
    }

    @Override
    public void requestUsernamePush() throws RemoteException {
        gameServer.pushLobbyUsernames();
    }
}
