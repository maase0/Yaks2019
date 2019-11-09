package application;

import java.net.URL;
import java.util.ResourceBundle;

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
	            pmProjectsStage.setResizable(false);
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
			System.out.println("Select a role");
		}
	}
	public void Exit(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();
	}
}
