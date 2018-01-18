package client;

import server.IGameServer;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GanzenbordClient extends UnicastRemoteObject implements IClient, Serializable {



    // Set binding name for server
    private static final String BINDING_NAME = "server";

    // References to registry and server
    private Registry registry = null;
    private IGameServer gameServer = null;


    private boolean isHost;
    private LobbyScreenController lobbyController;
    private MainGameScreenController gameScreenController;

    // Constructor
    public GanzenbordClient(boolean isHost) throws RemoteException {
        this.isHost = isHost;
    }


    @Override
    public void connectToServer(String ipAddress, int portNumber) {

        if(gameServer != null){
            return;
        }
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
                gameServer = (IGameServer) registry.lookup(BINDING_NAME);
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
    public String getUsername() throws RemoteException {
        return UITools.loggedInUser.getUsername();
    }

    @Override
    public int hostGame() {
        if(gameServer == null){
            return -1;
        }

        try{
            return gameServer.hostGame(this);
        }
        catch(RemoteException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean joinGame(int roomCode) {
        if(gameServer == null){
            return false;
        }

        try{
            return gameServer.joinGame(roomCode, this);
        }
        catch(RemoteException ex){
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public void pushNewState(int newLocationPlayer1, int newLocationPlayer2) {
        System.out.println("Client values: " + newLocationPlayer1 + " - " + newLocationPlayer2);
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

    @Override
    public void rollDice(IClient client, int currentLocation) throws RemoteException {

        gameServer.rollDice(this, currentLocation);
        return;
    }

    @Override
    public boolean isHost() {
        return isHost;
    }

    @Override
    public boolean checkIfServerIsRunning() throws RemoteException {
        connectToServer(SharedData.ip,1099);
        return gameServer != null;
    }

    @Override
    public boolean checkIfGameIsFull() throws RemoteException {
        return gameServer.checkIfGameIsFull();
    }

    @Override
    public void leaveGame(boolean inLobby) {

        try {
            gameServer.leaveGame(this, inLobby);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pushTerminateGame() throws RemoteException {
        if(gameScreenController == null){
            lobbyController.returnToMainMenu();
        }
        else{
            gameScreenController.returnToMainMenu();
        }
    }

    @Override
    public void requestTerminateGame() throws RemoteException {
        gameServer.terminateGame();
    }

    @Override
    public void requestStartGame() throws RemoteException {
        gameServer.startGame();
    }

    @Override
    public void pushStartGame() throws RemoteException {
        lobbyController.startGame();
    }
}
