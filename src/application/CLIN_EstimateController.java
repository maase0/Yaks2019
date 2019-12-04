package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CLIN_EstimateController implements Initializable, Refreshable {

	@FXML
	private Button closeButton;
	@FXML
	private Button addOrgButton;
	@FXML
	private Button editOrgButton;
	@FXML
	private Button removeOrgButton;
	@FXML
	private Label clinName;
	@FXML
	private ListView<OrganizationBOE> organizationBOEListView;
	private ObservableList<OrganizationBOE> organizationBOEObservableList;

	private ProjectVersion project;
	private CLIN clin;
	
	private Refreshable prevController;
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		organizationBOEObservableList = FXCollections.observableArrayList();
		organizationBOEListView.setItems(organizationBOEObservableList);
	}
	
	public void refresh() {
		
	}

	public void addOrganization(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrganizationBOE.fxml"));
			Parent root = fxmlLoader.load();

			OrganizationBOE_Controller controller = fxmlLoader.getController();
			
			controller.setPreviousController(this);
			controller.setOrganizationList(organizationBOEObservableList);
			Stage addOrgStage = new Stage();
			addOrgStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addOrgStage.setScene(new Scene(root));

			addOrgStage.show();
			addOrgStage.setResizable(true);
			addOrgStage.sizeToScene();

			StageHandler.addStage(addOrgStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editOrganization(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrganizationBOE.fxml"));
			Parent root = fxmlLoader.load();

			OrganizationBOE_Controller controller = fxmlLoader.getController();
			
			//Give all necessary information to the OrgController
			controller.setPreviousController(this);
			controller.setOrganizationList(organizationBOEObservableList);
			controller.setOrganiztion(organizationBOEListView.getSelectionModel().getSelectedItem());
			
			Stage addOrgStage = new Stage();
			addOrgStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addOrgStage.setScene(new Scene(root));

			addOrgStage.show();
			addOrgStage.setResizable(true);
			addOrgStage.sizeToScene();

			StageHandler.addStage(addOrgStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeOrganization(ActionEvent event) {

	}
	
	public void save() {
		clin.setOrganizations(new ArrayList<OrganizationBOE>(organizationBOEObservableList));
	}
	
	public void saveAndClose() {
		save();
		close();
	}

	public void close() {
				closeCurrent();
	}

	public void setProjectVersion(ProjectVersion project) {
		this.project = project;
	}
	public void setCLIN(CLIN clin) {
		this.clin = clin;
		//if(clin == null) System.out.println("ohno");
		clinName.setText(clin.getIndex());
		if(clin.getOrganizations() != null)
		organizationBOEObservableList.addAll(clin.getOrganizations());
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
