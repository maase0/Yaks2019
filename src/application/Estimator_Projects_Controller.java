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
	
	public void selectUnsubmitted(ActionEvent event) {
		//Load unsubmitted projects from database
	}
	
	public void selectSubmitted(ActionEvent event) {
		//Load submitted projects from base
	}
	
	public void addNewProject(ActionEvent event) {
		try {
            // Opens Product Manager page
            Parent root = FXMLLoader.load(getClass()
                    .getResource("view/PM_NewProject.fxml"));
            
            Stage pmProjectsStage = new Stage();
            pmProjectsStage.setTitle("Estimation Suite - Estimator - New Project");
            pmProjectsStage.setScene(new Scene(root));
            pmProjectsStage.show();
            
            //Closes Login Page
            Stage stage = (Stage) newProjectBtn.getScene().getWindow();
            stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editProject(ActionEvent event) {
		
	}
	
	public void discardProject(ActionEvent event) {
		
	}
}
