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

public class EstimateProjectController implements Initializable {

	private ProjectVersion project;
	private boolean cameFromEstimator;

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

	public void submitApproval(ActionEvent event) {

	}

	public void saveNewChanges(ActionEvent event) {

	}

	public void discardChanges(ActionEvent event) {
		try {
			Stage pmProjectsStage;
			Parent root;
			if (cameFromEstimator) {
				root = FXMLLoader.load(getClass().getResource("Estimator_Projects.fxml"));

				pmProjectsStage = new Stage();
				pmProjectsStage.setTitle("Estimation Suite - Estimator - Projects");
			} else {
				root = FXMLLoader.load(getClass().getResource("PM_Projects.fxml"));

				pmProjectsStage = new Stage();
				pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
			}
			pmProjectsStage.setScene(new Scene(root));
			pmProjectsStage.show();

			Stage stage = (Stage) discardButton.getScene().getWindow();

			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void estimateCLIN(ActionEvent event) {
		try {

			CLIN clin = clinEstimateListView.getSelectionModel().getSelectedItem();
			System.out.println(clin);
			//TODO: ADD NULL CHECK

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN_Estimate.fxml"));
			Parent root = fxmlLoader.load();

			CLIN_EstimateController controller = fxmlLoader.getController();
			controller.setProjectVersion(project);
			controller.setCLIN(clin);

			Stage clinEstimateStage = new Stage();
			clinEstimateStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			clinEstimateStage.setScene(new Scene(root));

			clinEstimateStage.show();
			clinEstimateStage.setResizable(true);
			clinEstimateStage.sizeToScene();

			Stage stage = (Stage) estCLINButton.getScene().getWindow();
			stage.close();

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

	public void setCameFromEstimator(boolean flag) {
		this.cameFromEstimator = flag;
	}
}
