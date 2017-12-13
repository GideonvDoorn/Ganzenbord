package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("LoginScreen.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
