package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameServer  extends Remote {

    /**
     * Hosts a game on the serveras the given client,
     * and geenrates a roomcode.
     * @param client the client who will host the game
     * @return The roomCode of the hosted game
     * @throws RemoteException
     */
    int hostGame(IClient client) throws RemoteException;


    /**
     * Joins a game on the server with the specified roomcode.
     * @param roomCode the roomcode of the game
     * @param client the client who will join
     * @return a boolean that specifies if the joining process has succeeded
     * @throws RemoteException
     */
    boolean joinGame(int roomCode, IClient client) throws RemoteException;

    /**
     * pushes the usernames from all clients that are in the game, to all clients.
     * @throws RemoteException
     */
    void pushLobbyUsernames() throws RemoteException;

    /**
     * generates a random number between 1 and 6 and adds it to the
     * cuurent location to generate a new location for the specified client
     * to move to.
     * @param client the client who needs a new location
     * @param currentLocation the current location of the client
     * @throws RemoteException
     */
    void rollDice(IClient client, int currentLocation) throws RemoteException;

    /**
     * checks if the hosted game is full
     * @return a boolean that represents if the game is full or not.
     * @throws RemoteException
     */
    boolean checkIfGameIsFull() throws RemoteException;

    /**
     * Leave the hosted game with a specified client
     * and pushes this to the other clients to let them know
     * someone has left the game
     * @param client client that wants to leave the game
     * @param inLobby specifis if this client wants to leave the lobby
     * @throws RemoteException
     */
    void leaveGame(IClient client, boolean inLobby) throws RemoteException;

    /**
     * Pushes to all clients that the game must me terminated, after which
     * all clients will be sent back to the main menu
     * @throws RemoteException
     */
    void terminateGame() throws RemoteException;

    /**
     * Pushes to all client that the game will start, after which
     * they will be redirected to the game screen
     * @throws RemoteException
     */
    void startGame() throws RemoteException;
}
