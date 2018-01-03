package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import login.ILoginServer;
import login.LoginServer;
import login.User;

public class LoginScreenController {

    private final ILoginServer loginServer = new LoginServer();

    @FXML
    public Button btnLogin;
    public Button btnRegister;
    public TextField tfUsername;
    public TextField tfPassword;
    public Label lblLoginError;



    @FXML
    public void Initialize(){

    }

    public void btnLoginOnClick(){

        //Check if fields are filled in
        if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty()){
            lblLoginError.setText("Please fill in all fields");
            return;
        }

        //TODO: -Database, loginserver- check if login matches an user

        User u = loginServer.loginUser(tfUsername.getText(), tfPassword.getText());

        if(u == null){
            lblLoginError.setText("Incorrect username or password");
        }
        else{
            //Switch to MainMenuPanel
            UITools.loggedInUser = u;

            UITools.UIManager uiManager = new UITools.UIManager();
            uiManager.loadFXML("MainMenuScreen.fxml");

        }



    }

    public void btnRegisterOnClick(){
        //Switch to MainMenuPanel
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("RegisterScreen.fxml");
    }

}
