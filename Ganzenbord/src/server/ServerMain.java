package server;

import client.IClient;
import shared.Game;
import shared.IGame;
import utils.SharedData;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerMain extends UnicastRemoteObject implements IGameServer, Serializable{

    // Set port number
    private static final int PORT_NUMBER = 1099;

    // Set binding name for server
    private static final String BINDING_NAME = "server";

    // References to registry and game
    private Registry registry = null;
    private IGame activeGame = null;



    public ServerMain() throws RemoteException{
        // Print port number for registry
        System.out.println("Server: Port number " + PORT_NUMBER);

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(PORT_NUMBER);
            System.out.println("Server: Registry created on SharedData address :" + registry.toString());
            System.out.println("Server: Registry created on port number : " + PORT_NUMBER);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind game using registry
        try {
            registry.rebind(BINDING_NAME, this);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind game");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }


    // Print SharedData addresses and network interfaces
    public static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: SharedData Address: " + localhost.getHostAddress());
            // Just in case this host has multiple SharedData addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of SharedData addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Can not get SharedData address of local host");
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
            System.out.println("Server: Can not retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
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
        printIPAddresses();

        // Create server
        try{
            new ServerMain();

        }
        catch(RemoteException ex){

        }


        while(true){

        }
    }

    @Override
    public int hostGame(IClient client) {

        try{
            activeGame = new Game(client);
            System.out.println("created game");
        }
        catch(RemoteException ex){
            ex.printStackTrace();
        }

        try{
            return activeGame.getRoomCode();

        }
        catch (RemoteException ex){
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
            ex.printStackTrace();
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
}
