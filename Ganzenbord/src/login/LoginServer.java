package login;

import utils.GameLogger;
import utils.SharedData;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;

public class LoginServer {

    // Set port number
    private static final int PORT_NUMBER = 1100;

    // Set binding name for loginServer
    private static final String BINDING_NAME = "LOGIN";

    public LoginServer() throws RemoteException{
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
                ILoginManager loginManager = new LoginManager();
                registry.rebind(BINDING_NAME, loginManager);
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
            new LoginServer();

        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }
}
