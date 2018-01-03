package login;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.Properties;

public class LoginServer implements ILoginServer {

    private Properties props;
    private PreparedStatement InsertStatement = null;
    private Statement myStmt = null;
    Connection myConn = null;

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
    public boolean registerUser(String username, String password) {
        initConnection();

        try {



            // Create a statement
            InsertStatement = myConn.prepareStatement("INSERT INTO user (username, password) VALUES(?,?);");


            InsertStatement.setString (1, username);
            InsertStatement.setString   (2, password);

            InsertStatement.execute();


            myConn.close();


            return true;
        }
        catch (Exception exc) {
            exc.printStackTrace();
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

            System.out.println("Database connection successful!\n");




        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }


    private User getUserFromdb(String name){

        initConnection();
        ResultSet myRs = null;

        try {

            // Create a statement
            myStmt = myConn.createStatement();

            // Execute SQL query
            myRs = myStmt.executeQuery("select * from user where username = '" + name + "'");

            // Process the result set
            User u = null;

            if(myRs.next()) {
                int id = myRs.getInt("id");
                String username = myRs.getString("username");
                String password = myRs.getString("password");
                u = new User(username, password);

            }
            else{
                return null;
            }

            myConn.close();


            // return the result
            return u;
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;
    }
}
