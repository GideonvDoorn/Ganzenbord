package tests;

import client.LoginClient;
import login.LoginServer;
import login.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.GameLogger;
import utils.SharedData;

import java.rmi.RemoteException;
import java.util.logging.Level;

class LoginServerTest {

    LoginClient client;

    @BeforeEach
    void connect(){
        try {
            new LoginServer();
            client = new LoginClient(SharedData.IP_ADRESS, 1100);

        } catch (RemoteException e) {
            GameLogger.logMessage(e.getMessage(), Level.SEVERE);
        }
    }



    @Test
    void register(){
        try {
            Assertions.assertTrue(client.getLoginManager().registerUser(new User("test", "test")));
        } catch (RemoteException e) {
            GameLogger.logMessage(e.getMessage(), Level.SEVERE);
        }
    }

    @Test
    void login(){
        try {
            Assertions.assertNull(client.getLoginManager().loginUser("", ""));
            Assertions.assertNotNull(client.getLoginManager().loginUser("test", "test"));

        } catch (RemoteException e) {
            GameLogger.logMessage(e.getMessage(), Level.SEVERE);
        }
    }
    @Test
    void logout(){
        try {
            client.getLoginManager().loginUser("test", "test");
        } catch (RemoteException e) {
            GameLogger.logMessage(e.getMessage(), Level.SEVERE);
        }
    }
}
