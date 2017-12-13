package client;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenuScreen extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        UITools.UIManager uiManager = new UITools.UIManager();
        uiManager.loadFXML("MainMenuScreen.fxml");
    }
}
