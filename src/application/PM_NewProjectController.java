package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

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
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private Button addCLINButton;
	@FXML
	private TextField indexText;
	@FXML
	private TextField projectTypeText;
	@FXML
	private DatePicker clinStartDate;
	@FXML
	private DatePicker clinEndDate;
	@FXML
	private TextArea clinText;
	@FXML
	private Text saveCLINButton;
	@FXML
	private Text editCLINButton;
	@FXML
	private Text discardCLINButton;
	@FXML
	private TextField clinText2;
	@FXML
	private TextField clinStartDate2;
	@FXML
	private TextField clinEndDate2;
	@FXML
	private Pane clinPane;
	
	@FXML
	private ListView clinList;
	ObservableList observableList = FXCollections.observableArrayList();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	
	@FXML
	public void saveChanges(ActionEvent event) {
		System.out.println("Save Changes Button");
		System.out.println("Project Name: " + projectNameText.getText());
		System.out.println("Project Manager: " + pmText.getText());
		System.out.println("Project Label: " + labelText.getText());
		System.out.println("Version Number: " + versionText.getText());
		System.out.println("Start Date: " + startDate.getValue());
		System.out.println("End Date: " + endDate.getValue());
	}
	
	@FXML
	public void discardChanges(ActionEvent event) {
		System.out.println("Discard Changes Button");
		try {
            Parent root = FXMLLoader.load(getClass()
                    .getResource("PM_Projects.fxml"));
            
            Stage pmProjectsStage = new Stage();
            pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
            pmProjectsStage.setScene(new Scene(root));
            pmProjectsStage.show();
            
    		Stage stage = (Stage) discardButton.getScene().getWindow();
    		stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void submitForEstimation(ActionEvent event) {
		System.out.println("Submit Button");
	}
	
	public void addCLIN(ActionEvent event) {
		System.out.println("TEST");
		
		
		
		}

      
        
	
	
	//CLIN is now editable
	public void editCLIN(MouseEvent event) {
		indexText.setEditable(true);
		indexText.setStyle("-fx-background-color: white;");
		projectTypeText.setEditable(true);
		projectTypeText.setStyle("-fx-background-color: white;");
		
		clinStartDate.setDisable(false);
		clinStartDate.setVisible(true);
		clinStartDate2.setVisible(false);
		clinEndDate.setDisable(false);
		clinEndDate.setVisible(true);
		clinEndDate2.setVisible(false);
		
		clinText.setDisable(false);
		clinText.setVisible(true);
		clinText2.setVisible(false);
		
		saveCLINButton.setDisable(false);
		saveCLINButton.setVisible(true);
		editCLINButton.setDisable(true);
		editCLINButton.setVisible(false);
		
		clinPane.setStyle("-fx-border-color: black; -fx-background-color: #F4F4F4;");
	}
	
	//CLIN is no longer editable
	public void saveCLIN(MouseEvent event) {
		
		indexText.setEditable(false);
		indexText.setStyle("-fx-border-color: black; -fx-background-color: #8a8988;");
		projectTypeText.setEditable(false);
		projectTypeText.setStyle("-fx-border-color: black; -fx-background-color: #8a8988;");
		
		clinStartDate2.setText(clinStartDate.getValue().toString());
		clinStartDate.setDisable(true);
		clinStartDate.setVisible(false);
		clinStartDate2.setVisible(true);
		clinEndDate2.setText(clinEndDate.getValue().toString());
		clinEndDate.setDisable(true);
		clinEndDate.setVisible(false);
		clinEndDate2.setVisible(true);
		
		clinText2.setText(clinText.getText());
		clinText.setDisable(true);
		clinText.setVisible(false);
		clinText2.setVisible(true);
		
		saveCLINButton.setDisable(true);
		saveCLINButton.setVisible(false);
		editCLINButton.setDisable(false);
		editCLINButton.setVisible(true);
		
		clinPane.setStyle("-fx-border-color: black; -fx-background-color: #8a8988;");
	}
	
	public void discardCLIN(MouseEvent event) {
		
	}
	
}
