package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreenController {

    @FXML
    public Button btnLogin;
    public Button btnRegister;
    public TextField tfUsername;
    public TextField tfPassword;
    public Label lblLoginError;

    public void btnLoginOnClick(){

        //Check if fields are filled in
        if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty()){
            lblLoginError.setText("Please fill in all fields");
            return;
        }

        //TODO: -Database- check if login matches an user

        //Switch to MainMenuPanel
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");

    }

    public void btnRegisterOnClick(){
        //Switch to MainMenuPanel
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("RegisterScreen.fxml");
    }

}
