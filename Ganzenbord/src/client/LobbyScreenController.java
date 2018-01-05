package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LobbyScreenController {

    @FXML
    public Label lblUsername;
    public Label lblGameID;
    public Label lblPlayer1;
    public Label lblPlayer2;

    @FXML
    public void initialize() {
        //TODO: -Database, loginserver- get current user name, and put it in this label
        lblUsername.setText(UITools.loggedInUser.getUsername());
        lblPlayer1.setText(UITools.loggedInUser.getUsername());


        //TODO: -Server- get a game + id and put it into this label
        lblGameID.setText("1010 -test");
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
}
