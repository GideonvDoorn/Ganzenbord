package client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class JoinGameScreenController {

    @FXML
    public Label lblUsername;
    public Label lblError;
    public TextField tfGameCode;




    @FXML
    public void initialize() {
        //TODO: -Database, loginserver- get current user name, and put it in this label
        lblUsername.setText("Gideon -test");
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

        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LobbyScreen.fxml");

    }

    public void btnLogoutOnClick(){
        //TODO: -Database, loginserver- logout user
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

}
