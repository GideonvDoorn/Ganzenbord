package client;

import server.IGameServer;
import utils.GameLogger;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class GanzenbordClient extends UnicastRemoteObject implements IClient, Serializable {



    // Set binding name for server
    private static final String BINDING_NAME = "server";

    private transient IGameServer gameServer = null;


    private boolean isHost;
    private LobbyScreenController lobbyController;
    private MainGameScreenController gameScreenController;

    // Constructor
    GanzenbordClient(boolean isHost) throws RemoteException{
        this.isHost = isHost;
    }


    @Override
    public void connectToServer(String ipAddress, int portNumber) {

        if(gameServer != null){
            return;
        }

        // Locate registry at SharedData address and port number
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
        } catch (RemoteException ex) {
            GameLogger.logMessage("Client: Cannot locate registry" + ex.getMessage() , Level.SEVERE);
            registry = null;
        }

        // Print result locating registry
        if (registry != null) {
            GameLogger.logMessage("Client: Registry located" , Level.INFO);
        } else {
            GameLogger.logMessage("Client: Cannot locate registry" , Level.SEVERE);
        }


        // Bind game using registry
        if (registry != null) {
            try {
                gameServer = (IGameServer) registry.lookup(BINDING_NAME);
            } catch (RemoteException | NotBoundException ex) {
                GameLogger.logMessage("Client: Cannot bind game" + ex.getMessage(), Level.SEVERE);
                gameServer = null;
            }
        }

        // Print result binding server
        if (gameServer != null) {
            GameLogger.logMessage("Client: game bound", Level.INFO);
        } else {
            GameLogger.logMessage("Client: game is null pointer", Level.SEVERE);
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
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
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
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return false;
        }

    }

    @Override
    public void pushNewState(int newLocationPlayer1, int newLocationPlayer2) {
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
    }

    @Override
    public boolean isHost() {
        return isHost;
    }

    @Override
    public boolean checkIfServerIsRunning() throws RemoteException {
        connectToServer(SharedData.IP_ADRESS,1099);
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
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
