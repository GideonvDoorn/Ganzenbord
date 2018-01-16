package login;

import server.ServerMain;
import utils.SharedData;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LoginServer {

    // Set port number
    private static final int portNumber = 1100;

    // Set binding name for Effectenbeurs
    private static final String bindingName = "login";

    // References to registry and Effectenbeurs
    private Registry registry = null;
    private ILoginManager loginManager = null;

    public LoginServer(){
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        //create a loginmanager
        try {
            loginManager = new LoginManager();

            System.out.println("Server: loginManager created");
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create Game");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            loginManager = null;
        }

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on SharedData address :" + registry.toString());
            System.out.println("Server: Registry created on port number : " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind game using registry
        try {
            registry.rebind(bindingName, loginManager);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind loginManager");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }

    public static void main(String[] args){
        final String ipAddress = SharedData.ip;

        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        System.out.println("[before] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));

        // RMI on distinct SharedData address
        System.setProperty("java.rmi.server.hostname", ipAddress );

        System.out.println("[after] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));


        // Print SharedData addresses and network interfaces
        ServerMain.printIPAddresses();

        // Create server
        new LoginServer();
    }
}
