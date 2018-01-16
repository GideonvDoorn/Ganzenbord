package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainMenuScreenController {

    @FXML
    public Label lblUsername;


    @FXML
    public void initialize() {
        //TODO: -Database, loginserver- get current user name, and put it in this label
        lblUsername.setText(UITools.loggedInUser.getUsername());
    }

    public void btnLogoutOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    public void btnStartGameOnClick(){


        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainGameScreen.fxml");
    }

    public void btnJoinGameOnClick(){


        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainGameScreen.fxml");
    }
}
