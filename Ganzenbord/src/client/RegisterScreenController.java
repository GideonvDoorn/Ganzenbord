package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import login.ILoginManager;
import login.LoginManager;

import java.rmi.RemoteException;

public class RegisterScreenController {

    ILoginManager loginServer = new LoginManager();

    @FXML
    public Button btnRegister;
    public Button btnCancel;
    public TextField tfUsername;
    public TextField tfPassword;
    public TextField tfRepeatPassword;
    public Label lblRegisterError;

    public RegisterScreenController() throws RemoteException {
    }


    public void btnRegisterOnClick(){

        if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty() || tfRepeatPassword.getText().isEmpty()){
            lblRegisterError.setText("Please fill in all fields");
            return;
        }
        if(tfUsername.getText().length() > 12){
            lblRegisterError.setText("Username too long");
            return;
        }
        if(!tfPassword.getText().equals(tfRepeatPassword.getText())){
            lblRegisterError.setText("Passwords do not match");
            return;
        }


        //TODO: -Database, loginserver- Create user

        try{
            if(loginServer.registerUser(tfUsername.getText(), tfPassword.getText())){
                lblRegisterError.setText("Succesfully created a user!");
            }
            else{
                lblRegisterError.setText("ERROR, creating user failed!");
            }
        }
        catch(RemoteException ex){

        }


        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    public void btnCancelOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }
}
