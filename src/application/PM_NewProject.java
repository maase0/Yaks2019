package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class PM_NewProject extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("PM_NewProject.fxml"));

            // TODO editing this setTitle doesn't seem to be affecting anything, can't figure out why
            primaryStage.setTitle("Estimation Suite - Product Manager - New Project");
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