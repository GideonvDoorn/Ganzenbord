package server;

import client.IClient;
import shared.Game;
import shared.IGame;
import utils.GameLogger;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;

public class ServerMain extends UnicastRemoteObject implements IGameServer, Serializable{

    // Set port number
    private static final int PORT_NUMBER = 1099;

    // Set binding name for server
    private static final String BINDING_NAME = "server";

    private transient IGame activeGame = null;



    private ServerMain() throws RemoteException{
        // Print port number for registry
        GameLogger.logMessage("Server: Port number " + PORT_NUMBER, Level.INFO);

        // Create registry at port number
        Registry registry;
        try {
            registry = LocateRegistry.createRegistry(PORT_NUMBER);
        } catch (RemoteException ex) {
            GameLogger.logMessage("Server: Cannot create registry" + ex.getMessage(), Level.SEVERE);
            registry = null;
        }

        // Bind game using registry
        try {
            if (registry != null) {
                registry.rebind(BINDING_NAME, this);
            }
        } catch (RemoteException ex) {
            GameLogger.logMessage("Server: Cannot bind server" + ex.getMessage(), Level.SEVERE);
        }
    }




    public static void main(String[] args){
        final String ipAddress = SharedData.IP_ADRESS;

        // RMI on distinct SharedData address
        System.setProperty("java.rmi.server.hostname", ipAddress );

        // Create server
        try{
            new ServerMain();

        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Override
    public int hostGame(IClient client) {

        try{
            activeGame = new Game(client);
        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }

        try{
            return activeGame.getRoomCode();

        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return 0;
        }
    }

    @Override
    public boolean joinGame(int roomCode, IClient client) {

        try{
            if(activeGame == null || activeGame.getHost() == null){
                return false;
            }

            if(activeGame.getRoomCode() == roomCode){

                activeGame.registerUser(client);
                return true;
            }
            return false;
        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return false;
        }

    }

    @Override
    public void pushLobbyUsernames() {
        //push usernames to show in lobby
        if(activeGame == null){
            return;
        }

        try{
            if(activeGame.getGuest() == null){
                activeGame.getHost().setUsernames(activeGame.getHost().getUsername(), " ");
            }
            else{
                activeGame.getHost().setUsernames(activeGame.getHost().getUsername(), activeGame.getGuest().getUsername());
                activeGame.getGuest().setUsernames(activeGame.getHost().getUsername(), activeGame.getGuest().getUsername());
            }
    }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }











    @Override
    public void rollDice(IClient client, int currentLocation) throws RemoteException {
        int newLocation = activeGame.rollDice(client, currentLocation);

        if(newLocation == -1){
            return;
        }

        //RMI PUSH: push state to all clients, locations that don't have to change are -1
        if(client.isHost()){
            activeGame.getHost().pushNewState(newLocation, -1);
            activeGame.getGuest().pushNewState(newLocation, -1);
        }
        else{
            activeGame.getHost().pushNewState(-1, newLocation);
            activeGame.getGuest().pushNewState(-1, newLocation);
        }
    }

    @Override
    public boolean checkIfGameIsFull() {
        try {
            return activeGame.checkIfGameIsFull();
        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return false;
        }
    }

    @Override
    public void leaveGame(IClient client, boolean inLobby) {
        try {
            activeGame.unRegisterUser(client);

            if(inLobby){
                activeGame.getHost().requestUsernamePush();
            }
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Override
    public void terminateGame() throws RemoteException {
        activeGame.getHost().pushTerminateGame();
        if(activeGame.getGuest() != null){
            activeGame.getGuest().pushTerminateGame();
        }
    }

    @Override
    public void startGame() throws RemoteException {
        activeGame.getHost().pushStartGame();
        activeGame.getGuest().pushStartGame();
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
