package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterScreenController {

    @FXML
    public Button btnRegister;
    public Button btnCancel;
    public TextField tfUsername;
    public TextField tfPassword;
    public TextField tfRepeatPassword;
    public Label lblRegisterError;


    public void btnRegisterOnClick(){

        if(tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty() || tfRepeatPassword.getText().isEmpty()){
            lblRegisterError.setText("Please fill in all fields");
            return;
        }
        if(!tfPassword.getText().equals(tfRepeatPassword.getText())){
            lblRegisterError.setText("Passwords do not match");
            return;
        }


        //TODO: -Database, loginserver- Create user


        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }

    public void btnCancelOnClick(){
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }
}
