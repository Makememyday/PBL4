//import Controller.ClientController;
import Model.ClientModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ClientView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/MainFunctionalGUI.fxml"));
        HBox root = loader.load();

        // Create a Scene with the loaded root node
        Scene scene = new Scene(root, 1000, 600);

        // Set the scene for the primaryStage (main window)
        primaryStage.setScene(scene);
        primaryStage.setTitle("Convert Number");

        // Show the primaryStage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
