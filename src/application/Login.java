package application;
	
import DB.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.SQLException;


public class Login extends Application {
	@Override
	public void start(Stage primaryStage) throws SQLException {
		try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass()
                    .getResource("Login.fxml"));

            primaryStage.setTitle("Estimation Suite - Login Page");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		try {
			DBUtil.dbConnect();
			launch(args);
		}
		catch (Exception e) {}
		finally {
			DBUtil.dbDisconnect();
		}
	}
}
