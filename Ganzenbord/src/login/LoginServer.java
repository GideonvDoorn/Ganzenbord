package login;

import server.ServerMain;
import shared.Player;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

public class LoginServer {

    // Set port number
    private static final int portNumber = 1099;

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
            System.out.println("Server: Registry created on IP address :" + registry.toString());
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
        final String ipAddress = "145.93.76.47";

        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        System.out.println("[before] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));

        // RMI on distinct IP address
        System.setProperty("java.rmi.server.hostname", ipAddress );

        System.out.println("[after] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));


        // Print IP addresses and network interfaces
        ServerMain.printIPAddresses();

        // Create server
        new LoginServer();
    }
}
