package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import utils.GameLogger;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.logging.Level;

public class LobbyScreenController implements Serializable {

    private transient IClient client;
    @FXML
    transient Label lblUsername;
    @FXML
    transient Label lblGameID;
    @FXML
    transient Label lblPlayer1;
    @FXML
    transient Label lblPlayer2;
    @FXML
    transient Label lblError;


    @FXML
    public void initialize() {



        lblUsername.setText(UITools.loggedInUser.getUsername());

    }


    boolean createGame(){


        try {

            //connect to server and host a game
            client = new GanzenbordClient(true);

            if(!client.checkIfServerIsRunning()){
                return false;
            }

            client.connectToServer(SharedData.IP_ADRESS, 1099);
            client.setLobbyController(this);
            lblGameID.setText(String.valueOf(client.hostGame()));

            //set username in lobbyscreen
            lblPlayer1.setText(UITools.loggedInUser.getUsername());


            return true;
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
            return false;
        }
    }

    void joinLobby(IClient client){
        this.client = client;


        try{
            client.setLobbyController(this);
            client.requestUsernamePush();
        }
        catch (RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }

    public void btnBackOnClick(){
        leaveLobbyScreen();

        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    public void btnLogoutOnClick(){
        leaveLobbyScreen();

        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    private void leaveLobbyScreen(){
        try {
            if(!client.isHost()){
                client.leaveGame(true);
            }
            else{
                client.requestTerminateGame();
            }
        } catch (RemoteException ex) {
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }
    }


    public void btnStartGameOnclick(){

        try{
            if(!client.checkIfGameIsFull()){
                lblError.setText("Not enough players to start game!");
            }
            else{
                client.requestStartGame();
            }
        }
        catch(RemoteException ex){
            GameLogger.logMessage(ex.getMessage(), Level.SEVERE);
        }

    }

    void setUsernames(String host, String guest){

        if(!host.equals("")){
            Platform.runLater(() -> lblPlayer1.setText(host));
        }
        if(!guest.equals("")){
            Platform.runLater(() -> lblPlayer2.setText(guest));
        }
    }

    void returnToMainMenu() {

        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadFXML("MainMenuScreen.fxml"));
    }

    void startGame() {

        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadMainGameScreen(client));
    }
}
