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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PM_NewProjectController implements Initializable{
	@FXML
	private Button saveButton;
	@FXML
	private Button discardButton;
	@FXML
	private Button submitButton;
	@FXML
	private TextField projectNameText;
	@FXML
	private TextField pmText;
	@FXML
	private TextField labelText;
	@FXML
	private TextField versionText;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void saveChanges(ActionEvent event) {
		System.out.println("Save Changes Button");
		System.out.println("Project Name: " + projectNameText.getText());
		System.out.println("Project Manager: " + pmText.getText());
		System.out.println("Project Label: " + labelText.getText());
		System.out.println("Version Number: " + versionText.getText());
	}
	
	public void discardChanges(ActionEvent event) {
		System.out.println("Discard Changes Button");
	}
	
	public void submitForEstimation(ActionEvent event) {
		System.out.println("Submit Button");
	}
	
}
