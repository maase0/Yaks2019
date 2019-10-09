package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class LoginController implements Initializable{

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
			//Open Product Manager page here
			System.out.println("Product Manager Selected, this works!");
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
