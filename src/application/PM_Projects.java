package application;
	
import DB.DBUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.WindowEvent;

import java.sql.SQLException;


public class PM_Projects extends Application {
	@Override
	public void start(Stage primaryStage) throws SQLException {
		try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("PM - Projects.fxml"));
 
            primaryStage.setTitle("Estimation Suite - Product Manager - Projects");
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