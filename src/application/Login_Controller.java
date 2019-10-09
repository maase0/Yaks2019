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
	private ComboBox<String> loginBox = new ComboBox<String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginBox.getItems().addAll("Product Manager", "Estimator");
		
	}
	public void Login(ActionEvent event) {
		if (loginBox.getValue() == "Product Manager") {
			System.out.println("Product Manager Selected, this works!");
			try {
	            // Opens Product Manager page
	            Parent root = FXMLLoader.load(getClass()
	                    .getResource("PM - Projects.fxml"));
	            
	            Stage pmProjectsStage = new Stage();
	            pmProjectsStage.setTitle("Estimation Suite - Product Manager - Project Page");
	            pmProjectsStage.setScene(new Scene(root));
	            pmProjectsStage.show();
	            
	            //Closes Login Page
	            Stage stage = (Stage) loginBtn.getScene().getWindow();
	            stage.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		else if (loginBox.getValue() == "Estimator") {
			//Open Estimator page here
			System.out.println("Estimator selected, this works!");
		}
		else {
			System.out.println("Select a role");
		}
	}
}
