package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import shared.IGame;
import utils.SharedData;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LobbyScreenController implements Serializable {

    private IClient client;
    @FXML
    public transient Label lblUsername;
    public transient Label lblGameID;
    public transient Label lblPlayer1;
    public transient Label lblPlayer2;
    public transient Label lblError;

    @FXML
    public void initialize() {



        lblUsername.setText(UITools.loggedInUser.getUsername());

    }


    public boolean createGame(){


        try {

            //connect to server and host a game
            client = new GanzenbordClient(true);

            if(!client.checkIfServerIsRunning()){
                return false;
            }

            client.connectToServer(SharedData.ip, 1099);
            client.setLobbyController(this);
            lblGameID.setText(String.valueOf(client.hostGame()));

            //set username in lobbyscreen
            lblPlayer1.setText(UITools.loggedInUser.getUsername());


            return true;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void joinLobby(IClient client){
        this.client = client;


        try{
            client.setLobbyController(this);
            client.requestUsernamePush();
        }
        catch (RemoteException ex){
           ex.printStackTrace();
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

    public void leaveLobbyScreen(){
        try {
            if(!client.isHost()){
                client.leaveGame(true);
            }
            else{
                client.requestTerminateGame();
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }


    public void btnStartGameOnclick(){

        try{
            if(!client.checkIfGameIsFull()){
                lblError.setText("Not enough players to start game!");
                return;
            }
            else{
                client.requestStartGame();
            }
        }
        catch(RemoteException ex){
            ex.printStackTrace();
        }

    }

    public void setUsernames(String host, String guest){

        if(!host.equals("")){
            Platform.runLater(() -> lblPlayer1.setText(host));
        }
        if(!guest.equals("")){
            Platform.runLater(() -> lblPlayer2.setText(guest));
        }
    }

    public void returnToMainMenu() {

        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadFXML("MainMenuScreen.fxml"));
    }

    public void startGame() {

        UITools.UIManager uiManager = new UITools.UIManager();
        Platform.runLater(() -> uiManager.loadMainGameScreen(client));
    }
}
