package client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import login.User;

import java.io.IOException;
import java.util.Map;

public class UITools {

    public static User loggedInUser;
    public static class UIManager {


        /**
         * The current window
         */
        private static Stage stage = null;
        /**
         * The current root Pane that is on screen
         */
        private static Pane currentRoot = null;
        /**
         * The FXMLLoader used to load all FXML Files. This is also used to get the current gameScreenController.
         */
        private FXMLLoader loader = null;


        public void loadMainGameScreen(){
            loader = new FXMLLoader(getClass().getResource("LobbyScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            currentRoot = root;

            Scene scene = new Scene(root);
            LobbyScreenController controller = loader.<LobbyScreenController>getController();
            controller.createGame();
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
        }


        public void loadLobbyScreenAsHost(){
            loader = new FXMLLoader(getClass().getResource("LobbyScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            currentRoot = root;

            Scene scene = new Scene(root);
            LobbyScreenController controller = loader.<LobbyScreenController>getController();
            controller.createGame();
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
        }

        public void loadLobbyScreenAsClient(IClient client){
            loader = new FXMLLoader(getClass().getResource("LobbyScreen.fxml"));
            Pane root  = null;
            try{
                root = loader.load();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            currentRoot = root;
            LobbyScreenController controller = loader.<LobbyScreenController>getController();
            controller.joinLobby(client);
            Scene scene = new Scene(root);
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
                e.printStackTrace();
            }
            currentRoot = root;
            Scene scene = new Scene(root);
            if(stage == null){
                stage = new Stage();
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();
                stage.setResizable(false);
            }
            stage.setScene(scene);
            //setNickname();
        }


        /**
         * Returns the current gameScreenController from the FXMLLoader.
         *
         * @return the Controller as an Object. Needs to be cast to the right class.
         */
        public Object getController() {
            return loader.getController();
        }

        /**
         * Get the root of the current window
         *
         * @return the root node from the current stage. Can return NULL when no fxml file was loaded
         */
        public Node getRoot() {
            return currentRoot;
        }

        /**
         * Gets the current stage
         *
         * @return the current stage, can be NULL when there is no FXML file loaded
         */
        public Stage getStage() {
            return stage;
        }

        /**
         * Returns the current namespace of the FXML Loader
         *
         * @return a Map of String and Object, String being the fx:id and Object being the Node.
         * Can throw a NullPointerException when there is no FXML File
         */
        public Map<String, Object> getNamespace() throws NullPointerException {
            return loader.getNamespace();
        }

        /**
         * Closes the current window.
         */
        public void closeStage() {
            stage.close();
        }
    }
}