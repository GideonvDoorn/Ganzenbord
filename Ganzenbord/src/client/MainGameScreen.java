package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGameScreen extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainGameScreen.fxml");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
