package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import shared.IGame;
import utils.SharedData;

import java.rmi.RemoteException;

public class LobbyScreenController {

    boolean createGame = false;

    private IClient client;
    @FXML
    public Label lblUsername;
    public Label lblGameID;
    public Label lblPlayer1;
    public Label lblPlayer2;

    @FXML
    public void initialize() {



        lblUsername.setText(UITools.loggedInUser.getUsername());
//        if(createGame){
//            System.out.println("create game");
//            //set username in lobbyscreen
//            lblPlayer1.setText(UITools.loggedInUser.getUsername());
//
//            createGame();
//        }

    }

    public void setCreateGame(){
        createGame = true;
    }

    public void createGame(){


        try {

            //connect to server and host a game
            GanzenbordClient client = new GanzenbordClient();

            client.connectToServer(SharedData.ip, 1099);
            client.setLobbyController(this);
            lblGameID.setText(String.valueOf(client.gameServer.hostGame(client)));

            //set username in lobbyscreen
            lblPlayer1.setText(UITools.loggedInUser.getUsername());

//            clientID = activeGame.registerUser(client);
        } catch (RemoteException ex) {
            ex.printStackTrace();
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
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }

    public void btnLogoutOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    public void btnStartGameOnclick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainGameScreen.fxml");
    }

    public void setUsernames(String host, String guest){

        if(!host.equals("")){
            Platform.runLater(() -> lblPlayer1.setText(host));
        }
        if(!guest.equals("")){
            Platform.runLater(() -> lblPlayer2.setText(guest));
        }
    }
}
