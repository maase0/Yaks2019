package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.sql.SQLException;

public class Estimator_ViewProjectEstimate extends Application {
    @Override
    public void start(Stage primaryStage) throws SQLException {
        try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("Estimator_ViewProjectEstimate.fxml"));

            primaryStage.setTitle("Estimation Suite - Estimator - Projects");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
