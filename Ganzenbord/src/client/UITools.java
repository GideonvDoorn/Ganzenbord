package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import login.User;
import utils.GameLogger;

import java.io.IOException;
import java.util.logging.Level;

public class UITools {

    private UITools(){

    }

    public static User loggedInUser;
    public static class UIManager {


        /**
         * The current window
         */
        private static Stage stage = null;

        /**
         * The FXMLLoader used to load all FXML Files. This is also used to get the current gameScreenController.
         */
        private FXMLLoader loader = null;


        void loadMainGameScreen(IClient client){
            loader = new FXMLLoader(getClass().getResource("MainGameScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                GameLogger.logMessage(e.getMessage(), Level.SEVERE);
            }

            Scene scene = null;
            if (root != null) {
                scene = new Scene(root);
            }
            MainGameScreenController controller = loader.getController();
            controller.setClient(client);

            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
        }


        boolean loadLobbyScreenAsHost(){

            loader = new FXMLLoader(getClass().getResource("LobbyScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                GameLogger.logMessage(e.getMessage(), Level.SEVERE);
            }
            Scene scene = null;
            if (root != null) {
                scene = new Scene(root);
            }
            LobbyScreenController controller = loader.getController();
            if(!controller.createGame()){
                return false;
            }
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);

            return true;
        }

        void loadLobbyScreenAsClient(IClient client){
            loader = new FXMLLoader(getClass().getResource("LobbyScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                GameLogger.logMessage(e.getMessage(), Level.SEVERE);
            }
            LobbyScreenController controller = loader.getController();
            controller.joinLobby(client);
            Scene scene = null;
            if (root != null) {
                scene = new Scene(root);
            }
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
        }




        /**
         * Loads the next FXML into the current BorderPane.
         *
         * @param resource   The FXML FileName that needs to be loader
         */

        public void loadFXML(String resource){
            loader = new FXMLLoader(getClass().getResource(resource));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                GameLogger.logMessage(e.getMessage(), Level.SEVERE);
            }
            Scene scene = null;
            if (root != null) {
                scene = new Scene(root);
            }
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
        }

    }
}