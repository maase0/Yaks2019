package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

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
		// on each thing, getDeleteList to remove deleted items from database, then
		// deleteList.removeAll()
		// will also need to go through and delete all sub-things, need to finish
		// database procedures
		// if any thing has a new version, it is inserted new and all sub-things are
		// inserted new
		// otherwise they are updated, like with the CLINs etc in
		// PM_EditProjectController
		// each thing will have a version and an oldVersion, if they are different then
		// the version changed
		// also if thing.getID() is null, then insert instead of update

		for (CLIN c : clinObservableList) {
			for (OrganizationBOE org : c.getOrganizations()) {
				saveOrganization(org, c.getVersionID());
			}

			for (OrganizationBOE org : c.getDeletedOrganizations()) {
				if (org.getID() != null) {
					DBUtil.dbExecuteUpdate("CALL delete_organization(" + org.getID() + ")");
				}
				//
			}
		}
		closeCurrent();
	}

	private void saveOrganization(OrganizationBOE org, String clinID) throws SQLException, ClassNotFoundException {
		if (org.getID() != null) {
//update_organization`(ORGVID int, ORGID int, orgName VARCHAR(45), versionNumber VARCHAR(45), product VARCHAR(100))
			ResultSet rs = DBUtil.dbExecuteQuery("CALL update_organization(" + org.getVersionID() + ", " + org.getID()
					+ ", '" + org.getOrganization() + "', '" + org.getVersion() + "', '" + org.getProduct() + "')");

			rs.next();

			if (!org.getVersion().equals(org.getOldVersion())) {
				org.setVersionID(rs.getString("idOrganizationVersion"));

				for (WorkPackage wp : org.getWorkPackages()) {
					cloneWorkPackage(wp, org.getVersionID());
				}
			} else {
				org.setVersionID(rs.getString("idOrganizationVersion"));
				for (WorkPackage wp : org.getWorkPackages()) {
					saveWorkPackage(wp, org.getVersionID());
				}
			}

			rs.close();

		} else {
			ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_organization(" + clinID + ", '" + org.getOrganization()
					+ "', '" + org.getVersion() + "', '" + org.getProduct() + "')");

			rs.last();
			org.setVersionID(rs.getString("idOrganizationVersion"));
			org.setID(rs.getString("idOrganization"));
			rs.close();

			for (WorkPackage wp : org.getWorkPackages()) {
				saveWorkPackage(wp, org.getVersionID());
			}
		}

		for (WorkPackage wp : org.getDeletedWorkPackages()) {
			if (wp.getID() != null) {
				DBUtil.dbExecuteUpdate("CALL delete_wp(" + wp.getID() + ")");
			}
		}
	}

	private void cloneWorkPackage(WorkPackage wp, String orgID) throws ClassNotFoundException, SQLException {
		ResultSet rs;
		if (wp.getID() != null) {
			// clone_wp`(ORGVID int, WPVID int, versionNumber varchar(45), wpName
			// varchar(45),
			// boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork
			// varchar(45), popStart DATE, popEnd DATE)
			rs = DBUtil.dbExecuteQuery("CALL clone_wp(" + orgID + ", " + wp.getVersionID() + ", '" + wp.getVersion()
					+ "', '" + wp.getName() + "', '" + wp.getAuthor() + "', '" + wp.getScope() + "', '"
					+ wp.getWorkPackageType() + "', '" + wp.getTypeOfWork() + "', '" + wp.getPopStart() + "', '"
					+ wp.getPopEnd() + "')");

		} else {
			rs = DBUtil.dbExecuteQuery("CALL insert_wp(" + orgID + ", '" + wp.getVersion() + "', '" + wp.getName()
					+ "', '" + wp.getAuthor() + "', '" + wp.getScope() + "', '" + wp.getWorkPackageType() + "', '"
					+ wp.getTypeOfWork() + "', '" + wp.getPopStart() + "', '" + wp.getPopEnd() + "')");
		}
		rs.last();
		wp.setVersionID(rs.getString("idWPVersion"));

		rs.close();

		for (Task task : wp.getTasks()) {
			cloneTask(task, wp.getVersionID());
		}
	}

	private void saveWorkPackage(WorkPackage wp, String orgID) throws SQLException, ClassNotFoundException {
		if (wp.getID() != null) {
//WPVID int, WPID int, versionNumber varchar(45), wpName varchar(45), boeAuthor varchar(45), scope varchar(45), wpType varchar(45), typeOfWork varchar(45), popStart DATE, p
			ResultSet rs = DBUtil.dbExecuteQuery("CALL update_WP(" + wp.getVersionID() + ", " + wp.getID() + ", '"
					+ wp.getVersion() + "', '" + wp.getName() + "', '" + wp.getAuthor() + "', '" + wp.getScope()
					+ "', '" + wp.getWorkPackageType() + "', '" + wp.getTypeOfWork() + "', '" + wp.getPopStart()
					+ "', '" + wp.getPopEnd() + "')");

			rs.last();
			wp.setVersionID(rs.getString("idWPVersion"));
			if (!wp.getVersion().equals(wp.getOldVersion())) {
				for (Task task : wp.getTasks()) {
					cloneTask(task, wp.getVersionID());
				}
			} else {
				for (Task task : wp.getTasks()) {
					saveTask(task, wp.getVersionID());
				}
			}

			rs.close();

		} else {
			ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_wp(" + orgID + ", '" + wp.getVersion() + "', '"
					+ wp.getName() + "', '" + wp.getAuthor() + "', '" + wp.getScope() + "', '" + wp.getWorkPackageType()
					+ "', '" + wp.getTypeOfWork() + "', '" + wp.getPopStart() + "', '" + wp.getPopEnd() + "')");

			rs.last();
			wp.setVersionID(rs.getString("idWPVersion"));

			rs.close();

			for (Task task : wp.getTasks()) {
				saveTask(task, wp.getVersionID());
			}
		}

		for (Task task : wp.getDeletedTasks()) {
			if (task.getID() != null) {
				DBUtil.dbExecuteUpdate("CALL delete_task(" + task.getID() + ")");
			}
		}
	}

	private void cloneTask(Task task, String wpID) throws ClassNotFoundException, SQLException {

		if (task.getID() != null) {
			// clone_task`(WPVID int, TASKVID int, taskName varchar(45), versionNumber
			// VARCHAR(45),
			// estimateFormula varchar(45), staffHours varchar(45), taskDetails
			// varchar(1000),
			// assumptions varchar(1000), methodology varchar(1000), popStart DATE, popEnd
			// DATE)
			DBUtil.dbExecuteUpdate("CALL clone_task(" + wpID + ", " + task.getVersionID() + ", '" + task.getName()
					+ "', '" + task.getVersion() + "', '" + task.getFormula() + "', " + task.getStaffHours() + ", '"
					+ task.getDetails() + "', '" + task.getConditions() + "', '" + task.getMethodology() + "', '"
					+ task.getPopStart() + "', '" + task.getPopEnd() + "')");
		} else {
			DBUtil.dbExecuteUpdate("CALL insert_task(" + wpID + ", '" + task.getName() + "', '" + task.getVersion()
					+ "', '" + task.getFormula() + "', " + task.getStaffHours() + ", '" + task.getDetails() + "', '"
					+ task.getConditions() + "', '" + task.getMethodology() + "', '" + task.getPopStart() + "', '"
					+ task.getPopEnd() + "')");
		}
	}

	private void saveTask(Task task, String wpID) throws SQLException, ClassNotFoundException {
		if (task.getID() != null) {
			System.out.println(task.getID());
			System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

			DBUtil.dbExecuteUpdate("CALL update_task(" + task.getID() + ", " + wpID + ", '" + task.getName() + "', '"
					+ task.getVersion() + "', '" + task.getFormula() + "', " + task.getStaffHours() + ", '"
					+ task.getDetails() + "', '" + task.getConditions() + "', '" + task.getMethodology() + "', '"
					+ task.getPopStart() + "', '" + task.getPopEnd() + "')");
		} else {
			DBUtil.dbExecuteUpdate("CALL insert_task(" + wpID + ", '" + task.getName() + "', '" + task.getVersion()
					+ "', '" + task.getFormula() + "', " + task.getStaffHours() + ", '" + task.getDetails() + "', '"
					+ task.getConditions() + "', '" + task.getMethodology() + "', '" + task.getPopStart() + "', '"
					+ task.getPopEnd() + "')");
		}
	}

	private void loadOrganizations(CLIN clin) throws ClassNotFoundException, SQLException {
		// result set stuff
		// put each one in clin

		//

		ResultSet rs = DBUtil
				.dbExecuteQuery("SELECT * FROM Organization WHERE idCLINVersion = " + clin.getVersionID() + ";");

		while (rs.next()) {
			ResultSet rs2 = DBUtil.dbExecuteQuery("CALL select_organizations(" + rs.getString("idOrganization") + ")");
			rs2.last();

			OrganizationBOE org = new OrganizationBOE();
			org.setID(rs2.getString("idOrganization"));
			org.setVersion(rs2.getString("Version_Number"));
			org.setOldVersion(rs2.getString("Version_Number"));
			org.setOrganization(rs2.getString("Organization_Name"));
			org.setProduct(rs2.getString("Product"));
			org.setVersionID(rs2.getString("idOrganizationVersion"));

			clin.addOrganiztion(org);

			rs2.close();
		}

		rs.close();
		for (OrganizationBOE org : clin.getOrganizations()) {
			loadWorkPackages(org);
		}
	}

	private void loadWorkPackages(OrganizationBOE org) throws ClassNotFoundException, SQLException {
		// result set stuff
		// put all work packages in org
		ResultSet rs = DBUtil
				.dbExecuteQuery("SELECT * FROM WP WHERE idOrganizationVersion = " + org.getVersionID() + ";");
		while (rs.next()) {
			ResultSet rs2 = DBUtil.dbExecuteQuery("CALL select_wps(" + rs.getString("idWP") + ")");

			if (rs2.last()) {

				WorkPackage wp = new WorkPackage();

				wp.setID(rs2.getString("idWP"));
				wp.setAuthor(rs2.getString("BoE_Author"));
				wp.setName(rs2.getString("WP_Name"));
				wp.setOldVersion(rs2.getString("Version_Number"));
				wp.setPopStart(rs2.getString("PoP_Start"));
				wp.setPopEnd(rs2.getString("PoP_End"));
				wp.setScope(rs2.getString("Scope"));
				wp.setVersion(rs2.getString("Version_Number"));
				wp.setTypeOfWork(rs2.getString("Type_of_Work"));
				wp.setVersionID(rs2.getString("idWPVersion"));

				org.addWorkPackage(wp);
			}
			rs2.close();
		}
		rs.close();

		for (WorkPackage wp : org.getWorkPackages()) {
			loadTasks(wp);
		}
	}

	private void loadTasks(WorkPackage wp) throws ClassNotFoundException, SQLException {
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM Task WHERE idWPVersion = " + wp.getVersionID() + ";");

		while (rs.next()) {
			ResultSet rs2 = DBUtil.dbExecuteQuery("CALL select_tasks(" + rs.getString("idTask") + ")");
			rs2.last();

			Task task = new Task();

			task.setConditions(rs2.getString("Assumptions"));
			task.setDetails(rs2.getString("Task_Details"));
			task.setFormula(rs2.getString("Estimate_Formula"));
			task.setID(rs2.getString("idTask"));
			task.setMethodology(rs2.getString("Estimate_Methodology"));
			task.setName(rs2.getString("Task_Name"));
			task.setOldVersion(rs2.getString("Version_Number"));
			task.setPopEnd(rs2.getString("PoP_End"));
			task.setPopStart(rs2.getString("PoP_Start"));
			task.setStaffHours(rs2.getInt("Staff_Hours"));
			task.setVersion(rs2.getString("Version_Number"));
			task.setVersionID(rs2.getString("idTaskVersion"));
			wp.addTask(task);

			rs2.close();
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
			// StageHandler.hidePreviousStage();
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

		// TODO: go through each clin in estimate list view
		// fill with all the sub-stuff
		System.out.println("test2");
		for (CLIN c : clinObservableList) {
			System.out.println("test1");
			// clin stuff should all be loaded
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
