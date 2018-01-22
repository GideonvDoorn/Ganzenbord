package tests;

import client.GanzenbordClient;
import client.IClient;
import client.UITools;
import login.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ServerMain;
import utils.GameLogger;
import utils.SharedData;

import java.rmi.RemoteException;
import java.util.logging.Level;

class RMITest {


    private IClient host;
    private IClient guest;

    @BeforeEach
    void connect(){

        try {
            new ServerMain();
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }


        boolean serverRunning;

        try{
            UITools.loggedInUser = new User("host", "");
            host = new GanzenbordClient(true);
            guest = new GanzenbordClient(false);
            serverRunning = guest.checkIfServerIsRunning();
            if(serverRunning){
                host.connectToServer(SharedData.IP_ADRESS, 1099);
                guest.connectToServer(SharedData.IP_ADRESS, 1099);
            }
        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void getUsername(){
        try {
            Assertions.assertEquals(host.getUsername(), "host");
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void hostOrJoinOrLeaveGame(){
        try {
            int roomCode = host.hostGame();
            Assertions.assertTrue(roomCode > 999 && roomCode < 9999);
            Assertions.assertTrue(guest.joinGame(roomCode));

            guest.leaveGame(false);
            guest.joinGame(roomCode);
            guest.leaveGame(true);
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void checkIfGameIsFull(){
        try {
            host.connectToServer(SharedData.IP_ADRESS, 1099);
            int roomCode = host.hostGame();

            Assertions.assertTrue(!guest.checkIfGameIsFull());
            guest.joinGame(roomCode);
            Assertions.assertTrue(guest.checkIfGameIsFull());


        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }
    @Test
    void startOrTerminateGame() {
        try {
            host.connectToServer(SharedData.IP_ADRESS, 1099);
            int roomCode = host.hostGame();
            guest.joinGame(roomCode);
            host.pushStartGame();
            host.pushTerminateGame();

        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void cantJoinGame(){
        try {
            Assertions.assertTrue(!guest.joinGame(1111));

            IClient client = new GanzenbordClient(false);
            client.connectToServer(SharedData.IP_ADRESS, 1099);

            int roomCode = host.hostGame();
            Assertions.assertTrue(client.joinGame(roomCode));
            Assertions.assertTrue(!guest.joinGame(roomCode));

        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void rollDice(){
        try {
            host.connectToServer(SharedData.IP_ADRESS, 1099);
            int roomCode = host.hostGame();
            guest.joinGame(roomCode);
            host.rollDice(host, 0);
            host.rollDice(host, 0);
            guest.rollDice(guest, 1);
            guest.rollDice(guest, 1);
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }
}