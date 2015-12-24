package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Alexander Vlasov
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Sea-Battle.fxml"));

        Scene scene = new Scene(root);
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.setScene(scene);
        stage.show();
    }

}
