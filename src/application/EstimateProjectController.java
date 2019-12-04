package application;

import DB.DBUtil;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EstimateProjectController implements Initializable, Refreshable {

	private ProjectVersion project;

	@FXML
	private Button discardButton;
	@FXML
	private Label projectName;
	@FXML
	private Label projectManager;
	@FXML
	private Label propNumber;
	@FXML
	private Label versionNumber;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private Button estCLINButton;
	@FXML
	private Button  submitApproval;
	@FXML
	private Button saveNewChanges;

	@FXML
	private ListView<CLIN> clinListView;
	@FXML
	private ListView<SDRL> sdrlListView;
	@FXML
	private ListView<SOW> sowListView;
	@FXML
	private ListView<CLIN> clinEstimateListView;

	private ObservableList<CLIN> clinObservableList;
	private ObservableList<SDRL> sdrlObservableList;
	private ObservableList<SOW> sowObservableList;

	private Refreshable prevController;
	
	
	public EstimateProjectController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clinObservableList = FXCollections.observableArrayList();
		clinListView.setItems(clinObservableList);
		clinEstimateListView.setItems(clinObservableList);

		sdrlObservableList = FXCollections.observableArrayList();
		sdrlListView.setItems(sdrlObservableList);

		sowObservableList = FXCollections.observableArrayList();
		sowListView.setItems(sowObservableList);

	}
	
	public void refresh() {
	}

	public void submitApproval(ActionEvent event) throws SQLException, ClassNotFoundException {
		saveNewChanges(); //currently doesn't function
		DBUtil.dbExecuteUpdate("CALL estimate_project(" + project.getProjectID() + ", '" + LocalDate.now().toString() + "')");
		closeCurrent();
	}

	public void saveNewChanges() {
		// TODO need to loop through CLIN_Estimate, get Organizations, Work Packages, and Tasks
		// on each thing, getDeleteList to remove deleted items from database, then deleteList.removeAll() 
		// 		will also need to go through and delete all sub-things, need to finish database procedures
	}

	public void discardChanges(ActionEvent event) {
		closeCurrent();
	}

	public void estimateCLIN(ActionEvent event) {
		try {

			CLIN clin = clinEstimateListView.getSelectionModel().getSelectedItem();
			System.out.println(clin);
			//TODO: ADD NULL CHECK

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN_Estimate.fxml"));
			Parent root = fxmlLoader.load();

			CLIN_EstimateController controller = fxmlLoader.getController();
			//controller.setProjectVersion(project);
			controller.setCLIN(clin);
			controller.setPreviousController(this);

			Stage clinEstimateStage = new Stage();
			clinEstimateStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			clinEstimateStage.setScene(new Scene(root));

			clinEstimateStage.show();
			clinEstimateStage.setResizable(true);
			clinEstimateStage.sizeToScene();

			
			StageHandler.addStage(clinEstimateStage);
			StageHandler.hidePreviousStage();
			//Stage stage = (Stage) estCLINButton.getScene().getWindow();
			//stage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setProjectVersion(ProjectVersion proj) {
		this.project = proj;
		setAllFields();
	}

	private void setAllFields() {
		clinObservableList.addAll(project.getCLINList());
		sowObservableList.addAll(project.getSOWList());
		sdrlObservableList.addAll(project.getSDRLList());

		versionNumber.setText(project.getVersionNumber());
		projectName.setText(project.getName());
		projectManager.setText(project.getProjectManager());
		propNumber.setText(project.getProposalNumber());

		startDate.setValue(project.getPopStart());
		startDate.setDisable(true);
		endDate.setValue(project.getPopEnd());
		endDate.setDisable(true);
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
