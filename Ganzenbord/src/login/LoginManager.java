package login;

import utils.GameLogger;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.Level;

public class LoginManager extends UnicastRemoteObject implements ILoginManager {

    private transient Connection myConn = null;

    LoginManager() throws RemoteException {
    }

    @Override
    public User loginUser(String username, String password) {

        User u = getUserFromdb(username);

        if(u == null){
            return null;

        }
        else{
            if(u.getPassword().equals(password)){
                return u;
            }
            else{
                return null;
            }
        }
    }

    @Override
    public boolean registerUser(User userToAdd) {
        initConnection();

        try {

            try (PreparedStatement insertStatement = myConn.prepareStatement("INSERT INTO user (username, password) VALUES(?,?);")) {

                insertStatement.setString(1, userToAdd.getUsername());
                insertStatement.setString(2, userToAdd.getPassword());

                insertStatement.execute();


                myConn.close();


                return true;
            }
        }
        catch (Exception ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return false;

        }
    }

    @Override
    public boolean logoutUser(String username) {
        return false;
    }

    private void initConnection(){

        try {

            // Get a connection to database
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ganzenbord?useSSL=false", "student" , "student");
            GameLogger.logMessage("Database connection successful!\n", Level.INFO);




        }
        catch (Exception ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }


    private User getUserFromdb(String name){

        initConnection();

        try {

            // Create a statement
            User u;
            try (PreparedStatement myStmt = myConn.prepareStatement("select * from user where username = ?;")) {

                myStmt.setString(1, name);

                try(ResultSet myRs = myStmt.executeQuery()) {

                    if (myRs.next()) {
                        String username = myRs.getString("username");
                        String password = myRs.getString("password");
                        u = new User(username, password);

                    } else {
                        return null;
                    }

                }

            }

            // return the result
            return u;
        }
        catch (Exception ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }

        return null;
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
