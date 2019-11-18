package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class EstimateProject_Controller implements Initializable {

	private ProjectVersion project;
	
    @FXML
    private Button discardButton;
    @FXML private Label projectName;
    @FXML private Label projectManager;
    @FXML private Label propNumber;
    @FXML private Label versionNumber;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    
    @FXML private ListView<CLIN> clinListView;
    @FXML private ListView<SDRL> sdrlListView;
    @FXML private ListView<SOW> sowListView;

    private ObservableList<CLIN> clinObservableList;
    private ObservableList<SDRL> sdrlObservableList;
    private ObservableList<SOW> sowObservableList;
    
    public EstimateProject_Controller() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	clinObservableList = FXCollections.observableArrayList();
		clinListView.setItems(clinObservableList);

		sdrlObservableList = FXCollections.observableArrayList();
		sdrlListView.setItems(sdrlObservableList);

		sowObservableList = FXCollections.observableArrayList();
		sowListView.setItems(sowObservableList);

    }

    public void submitApproval(ActionEvent event) {

    }

    public void saveNewChanges(ActionEvent event) {

    }

    public void discardChanges(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Estimator_Projects.fxml"));

            Stage pmProjectsStage = new Stage();
            pmProjectsStage.setTitle("Estimation Suite - Estimator - Projects");
            pmProjectsStage.setScene(new Scene(root));
            pmProjectsStage.show();

            Stage stage = (Stage) discardButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void setProjectVersion(ProjectVersion proj) {
    	this.project = proj;
    	setAllFields();
    }
    
    private void setAllFields () {
		clinObservableList.addAll(project.getCLINList());
		sowObservableList.addAll(project.getSOWList());
		sdrlObservableList.addAll(project.getSDRLList());
		
		versionNumber.setText(project.getVersionNumber());
		projectName.setText(project.getName());
		projectManager.setText(project.getProjectManager());
		propNumber.setText(project.getProposalNumber());

		startDate.setValue(project.getPopStart());
		endDate.setValue(project.getPopEnd());
	}
}
