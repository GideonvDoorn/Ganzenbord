package login;

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


    // Print IP addresses and network interfaces
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }
    public static void main(String[] args){
        final String ipAddress = "192.168.2.47";

        // Welcome message
        System.out.println("SERVER USING REGISTRY");

        System.out.println("[before] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));

        // RMI on distinct IP address
        System.setProperty("java.rmi.server.hostname", ipAddress );

        System.out.println("[after] java.rmi.server.hostname=" + System.getProperty("java.rmi.server.hostname"));


        // Print IP addresses and network interfaces
        printIPAddresses();

        // Create server
        new LoginServer();
    }
}
