package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuScreenController {

    @FXML
    public Label lblUsername;
    public Label lblError;

    @FXML
    public void initialize() {
        lblUsername.setText(UITools.loggedInUser.getUsername());
    }

    public void btnLogoutOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    public void btnStartGameOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        if(!uiManager.loadLobbyScreenAsHost()){
            lblError.setText("Server error, please try again later!");
        }
    }

    public void btnJoinGameOnClick(){


        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("JoinGameScreen.fxml");
    }
}
