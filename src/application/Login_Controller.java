package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class Login_Controller implements Initializable{

	@FXML
	private Button loginBtn;

	@FXML
	private Button exitBtn;
	
	@FXML
	private ComboBox<String> loginBox = new ComboBox<String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBox.getItems().addAll("Product Manager", "Estimator");
		
	}
	public void Login(ActionEvent event) {
		if (loginBox.getValue() == "Product Manager") {
			try {
	            // Opens Product Manager page
	            Parent root = FXMLLoader.load(getClass()
	                    .getResource("PM_Projects.fxml"));
	            
	            Stage pmProjectsStage = new Stage();
	            pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
	            pmProjectsStage.setScene(new Scene(root));
	            pmProjectsStage.show();
	            pmProjectsStage.centerOnScreen();
	            //pmProjectsStage.setMaximized(true);
	            pmProjectsStage.setResizable(true);
	            pmProjectsStage.sizeToScene();
	            
	            
	            //Closes Login Page
	            Stage stage = (Stage) loginBtn.getScene().getWindow();
	            stage.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if (loginBox.getValue() == "Estimator") {
			//Open Estimator page here
			try {
	            // Opens Estimator page
	            Parent root = FXMLLoader.load(getClass()
	                    .getResource("Estimator_Projects.fxml"));
	            
	            Stage estimatorProjectsStage = new Stage();
	            estimatorProjectsStage.setTitle("Estimation Suite - Estimator - Projects");
	            estimatorProjectsStage.setScene(new Scene(root));
	            estimatorProjectsStage.show();
	            estimatorProjectsStage.setResizable(false);
	            estimatorProjectsStage.sizeToScene();
	            
	            //Closes Login Page
	            Stage stage = (Stage) loginBtn.getScene().getWindow();
	            stage.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
		//previous error handling was
		//	System.out.println("Select a role");
			try {
			Parent root = FXMLLoader.load(getClass()
					.getResource("Error_Window.fxml"));
			Stage errorStage = new Stage();
            errorStage.setTitle("ERROR");
            //trying to figure out how to change the text on load
//            errorStage.setText("ERROR!";) 
			errorStage.setScene(new Scene(root));
			errorStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
			}
		}
	}
	public void Exit(ActionEvent event) throws SQLException {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}
}
