package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import login.ILoginManager;
import login.User;
import utils.SharedData;

import java.rmi.RemoteException;

public class LoginScreenController {

    @FXML
    Button btnLogin;
    @FXML
    Button btnRegister;
    @FXML
    TextField tfUsername;
    @FXML
    TextField tfPassword;
    @FXML
    Label lblLoginError;

    public LoginScreenController() throws RemoteException {

    }

    public void btnLoginOnClick(){

        LoginClient loginClient = new LoginClient(SharedData.IP_ADRESS, 1100);
        ILoginManager loginManager = loginClient.loginManager;

        if(loginManager == null){
            lblLoginError.setText("Server error, try again later");
            return;
        }

        //Check if fields are filled in
        if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty()){
            lblLoginError.setText("Please fill in all fields");
            return;
        }


        User u = null;
        try{
            u = loginManager.loginUser(tfUsername.getText(), tfPassword.getText());

        }
        catch (RemoteException ex){
            lblLoginError.setText("Server error, try again later");

        }

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
