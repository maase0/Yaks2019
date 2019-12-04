package application;

import DB.DBUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
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
	private Button submitApproval;
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

	// TODO Create boolean which will be set to true when a CLIN is currently open
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
		saveNewChanges(); // currently doesn't function
		DBUtil.dbExecuteUpdate(
				"CALL estimate_project(" + project.getProjectID() + ", '" + LocalDate.now().toString() + "')");
	}

	public void saveNewChanges() throws SQLException, ClassNotFoundException {
		// TODO need to loop through CLIN_Estimate, get Organizations, Work Packages,
		// and Tasks
		// on each thing, getDeleteList to remove deleted items from database, then deleteList.removeAll()
		// 		will also need to go through and delete all sub-things, need to finish database procedures
		//      if any thing has a new version, it is inserted new and all sub-things are inserted new
		//      otherwise they are updated, like with the CLINs etc in PM_EditProjectController
		//		each thing will have a version and an oldVersion, if they are different then the version changed
		//      also if thing.getID() is null, then insert instead of update
		
		for(CLIN c : clinObservableList) {
			for(OrganizationBOE org : c.getOrganizations()) {
				saveOrganization(org, c.getID());
			}
			
			for(OrganizationBOE org :c.getDeletedOrganizations()) {
				//DBUtil.dbExecuteUpdate("CALL delete_organization(" + org.getID() + ")");
			}
		}
		closeCurrent();
	}
	
	private void saveOrganization(OrganizationBOE org, String clinID) throws SQLException, ClassNotFoundException {
		if (org.getID() != null) {
			DBUtil.dbExecuteUpdate("CALL update_organization(" + org.getID() + ", " + clinID + ", '"
					+ org.getOrganization() + "', '" + org.getVersion() + "', '" + org.getProduct() + "')");
		} else {
			ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_organization(" + clinID + ", '" + org.getOrganization()
									+ "', '" + org.getVersion() + "', '" + org.getProduct() + "')");
			while(rs.next()) {
				org.setID(rs.getString("idOrganization"));
			}
			rs.close();
		}

		for(WorkPackage wp : org.getWorkPackages()) {
			saveWorkPackage(wp, org.getID());
		}
		
		/*for(WorkPackage wp : org.getDeletedWorkPackages()) {
			//DBUtil.dbExecuteUpdate("CALL delete_wp(" + wp.getID() + ")");
		}*/
	}
	
	private void saveWorkPackage(WorkPackage wp, String orgID) throws SQLException, ClassNotFoundException {
		if (wp.getID() != null) {
			DBUtil.dbExecuteUpdate("CALL update_WP(" + wp.getID() + ", " + orgID + ", '" + wp.getVersion()
					+ "', '" + wp.getName() + "', '" + wp.getAuthor() + "', '" + wp.getScope() + "', '"
					+ wp.getWorkPackageType() + "', '" + wp.getTypeOfWork() + "', '" + wp.getPopStart() + "', '"
					+ wp.getPopEnd() + "')");
		} else {
			ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_wp(" + orgID + ", '" + wp.getVersion() + "', '"
									+ wp.getName() + "', '" + wp.getAuthor() + "', '" + wp.getScope()
									+ "', '" + wp.getWorkPackageType() + "', '" + wp.getTypeOfWork() + "', '"
									+ wp.getPopStart() + "', '" + wp.getPopEnd() + "')");

			while (rs.next()) {
				wp.setID(rs.getString("idWP"));
			}
			rs.close();
		}

		for(Task task : wp.getTasks()) {
			saveTask(task, wp.getID());
		}
		
		/*for(Task task : wp.getDeletedTasks()) {
			//DBUtil.dbExecuteUpdate("CALL delete_task" + task.getID() + ")");
		}*/
	}

	private void saveTask(Task task, String wpID) throws SQLException, ClassNotFoundException {
		if (task.getID() != null) {
			DBUtil.dbExecuteUpdate("CALL update_task(" + task.getID() + ", " + wpID + ", '"
					+ task.getName() + "', '" + task.getFormula() + "', " + task.getStaffHours()
					+ ", '" + task.getDetails() + "', '" + task.getConditions() + "', '"
					+ task.getMethodology() + "', '" + task.getPopStart() + "', '" + task.getPopEnd() + "')");
		} else {
			DBUtil.dbExecuteUpdate("CALL insert_task(" + wpID + ", '" + task.getName() + "', '"
									+ task.getFormula() + "', " + task.getStaffHours() + ", '" + task.getDetails()
									+ "', '" + task.getConditions() + "', '" + task.getMethodology() + "', '"
									+ task.getPopStart() + "', '" + task.getPopEnd() + "')");
		}
	}
	
	
	private void loadOrganizations(CLIN clin) throws ClassNotFoundException, SQLException {
		//result set stuff
		//put each one in clin
		
		//
		
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM Organization WHERE idCLIN = " + clin.getID() + ";");
		while(rs.next()) {
			OrganizationBOE org = new OrganizationBOE();
			org.setID(rs.getString("idOrganization"));
			org.setVersion(rs.getString("Version_Number"));
			org.setOldVersion("Version_Number");
			org.setOrganization("Organization_Name");
			org.setProduct("Product");
			
			clin.addOrganiztion(org);
		}
		
		rs.close();
		for(OrganizationBOE org : clin.getOrganizations()) {
			loadWorkPackages(org);
		}
	}
	
	private void loadWorkPackages(OrganizationBOE org) throws ClassNotFoundException, SQLException {
		//result set stuff
		//put all work packages in org
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM WP WHERE idOrganization = " + org.getID() + ";");
		while(rs.next()) {
			WorkPackage wp = new WorkPackage();
			
			wp.setID(rs.getString("idWP"));
			wp.setAuthor(rs.getString("BoE_Author"));
			wp.setName(rs.getString("WP_Name"));
			wp.setOldVersion(rs.getString("Version_Number"));
			wp.setPopStart(rs.getString("PoP_Start"));
			wp.setPopEnd(rs.getString("PoP_End"));
			wp.setScope(rs.getString("Scope"));
			wp.setVersion(rs.getString("Version_Number"));
			wp.setTypeOfWork(rs.getString("Type_of_Work"));
		
			org.addWorkPackage(wp);
		}
		rs.close();
		
		for(WorkPackage wp : org.getWorkPackages()) {
			loadTasks(wp);
		}
	}
	
	private void loadTasks(WorkPackage wp) throws ClassNotFoundException, SQLException {
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM Task WHERE idWP = " + wp.getID() + ";");

		while(rs.next()) {
			Task task = new Task();
			
			task.setConditions(rs.getString("Assumptions"));
			task.setDetails(rs.getString("Task_Details"));
			task.setFormula(rs.getString("Estimate_Formula"));
			task.setID(rs.getString("idTask"));
			task.setMethodology(rs.getString("Estimate_Methodology"));
			task.setName(rs.getString("Task_Name"));
			//task.setOldVersion(rs.getString("Version_Number"));
			task.setPopEnd(rs.getString("PoP_End"));
			task.setPopStart(rs.getString("PoP_Start"));
			task.setStaffHours(rs.getInt("Staff_Hours"));
			//task.setVersion(rs.getString("Version_Number"));
		}
		rs.close();
		
	}
	
	

	public void discardChanges(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Remove Project");
		alert.setHeaderText("This will discard any unsaved changes.");
		alert.setContentText("Are you sure you want to exit?");

		ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			closeCurrent();
		} else {
			// ... user chose CANCEL or closed the dialog
		}
	}

	public void estimateCLIN(ActionEvent event) {
		try {

			CLIN clin = clinEstimateListView.getSelectionModel().getSelectedItem();
			System.out.println(clin);
			// TODO: ADD NULL CHECK

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN_Estimate.fxml"));
			Parent root = fxmlLoader.load();

			CLIN_EstimateController controller = fxmlLoader.getController();
			// controller.setProjectVersion(project);
			controller.setCLIN(clin);
			controller.setPreviousController(this);

			Stage clinEstimateStage = new Stage();
			clinEstimateStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			clinEstimateStage.setScene(new Scene(root));

			clinEstimateStage.show();
			clinEstimateStage.setResizable(true);
			clinEstimateStage.sizeToScene();

			StageHandler.addStage(clinEstimateStage);
			//StageHandler.hidePreviousStage();
			// Stage stage = (Stage) estCLINButton.getScene().getWindow();
			// stage.close();

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
		
		
		//TODO: go through each clin in estimate list view
		//fill with all the sub-stuff
		System.out.println("test2");
		for(CLIN c : clinObservableList) {
			System.out.println("test1");
			//clin stuff should all be loaded
			try {
				loadOrganizations(c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
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
