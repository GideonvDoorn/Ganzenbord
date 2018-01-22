package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.GameLogger;
import utils.SharedData;

import java.rmi.RemoteException;
import java.util.logging.Level;

public class JoinGameScreenController {

    IClient client;

    @FXML
    Label lblUsername;
    @FXML
    Label lblError;
    @FXML
    TextField tfGameCode;


    @FXML
    public void initialize() {
        lblUsername.setText(UITools.loggedInUser.getUsername());

        try{
            client = new GanzenbordClient(false);
            client.connectToServer(SharedData.IP_ADRESS, 1099);

        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }


    public void btnJoinGameOnClick(){
        if(tfGameCode.getText().isEmpty()){
            lblError.setText("Please fill in a game code");
            return;
        }
        if(tfGameCode.getText().length() !=  4){
            lblError.setText("Game code should be 4 digits long");
            return;
        }

        //connect to server and join a game
        try{
            if(!client.checkIfServerIsRunning()){
                lblError.setText("Server error, try again later!");
                return;
            }


            if(!client.joinGame(Integer.parseInt(tfGameCode.getText()))){
                lblError.setText("Room does not exist!");
                return;
            }
        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return;
        }



        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadLobbyScreenAsClient(client);

    }

    public void btnBackOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    public void btnLogoutOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

}
