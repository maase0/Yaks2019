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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Estimator_Projects_Controller implements Initializable{

	@FXML
	private Button logoutButton;

	@FXML
	private Button newProjectBtn;
	
	@FXML
	private TabPane projectTabPane;
	
	@FXML
	private Tab submittedTab;
	
	@FXML
	private Tab unsubmittedTab;
	
	@FXML
	private Text editBtn;
	
	@FXML 
	private Text discardBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	//Pull Unsubmitted Projects from database
	}

	public void logout(ActionEvent event) {
		try {
			// Opens Login page
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

			Stage loginStage = new Stage();
			loginStage.setTitle("Estimation Suite - Login Page");
			loginStage.setScene(new Scene(root));
			loginStage.show();

			// Closes PM Page
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
