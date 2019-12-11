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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PM_VPE_Controller implements Initializable, Refreshable {
	private Refreshable prevController;
	@FXML
	private Button returnEstimationButton;
	@FXML
	private Button approveButton;
	@FXML
	private Button denyButton;
	@FXML
	private Button closeEstimationButton;
	@FXML
	private Button viewCLINestimateButton;
	@FXML
	private Label projectManager;
	@FXML
	private Label propNum;
	@FXML
	private Label versionNum;
	@FXML
	private Label projectName;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;

	@FXML
	private ListView<CLIN> clinListView;
	@FXML
	private ListView<CLIN> clinEstimateListView;
	private ObservableList<CLIN> clinObservableList;

	@FXML
	private ListView<SDRL> sdrlListView;
	private ObservableList<SDRL> sdrlObservableList;

	@FXML
	private ListView<SOW> sowListView;
	private ObservableList<SOW> sowObservableList;
	
	private ProjectVersion project;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
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

	public void closeEstimation() {
		closeCurrent();
	}

	public void returnEstimation(ActionEvent event) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL return_for_estimation(" + project.getProjectID() + ")");
		closeEstimation();
	}

	public void approveProject(ActionEvent event) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL approve_project(" + project.getProjectID() + ", '"
									+ LocalDate.now().toString() + "')");
		closeCurrent();
	}

	public void denyProject(ActionEvent event) throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteUpdate("CALL deny_project(" + project.getProjectID() + ", '"
									+ LocalDate.now().toString() + "')");
		closeCurrent();
	}

	public void viewCLINestimation(ActionEvent event) {
		try {

			CLIN clin = clinEstimateListView.getSelectionModel().getSelectedItem();
			System.out.println(clin);

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCLINEstimate.fxml"));
			Parent root = fxmlLoader.load();

			ViewCLIN_Estimate_Controller controller = fxmlLoader.getController();
			controller.setCLIN(clin);
			controller.setPreviousController(this);

			Stage viewCLIN = new Stage();
			viewCLIN.setTitle("Estimation Suite - Project Manager - Estimate Project");
			viewCLIN.setScene(new Scene(root));

			viewCLIN.show();
			viewCLIN.setResizable(true);
			viewCLIN.sizeToScene();

			StageHandler.addStage(viewCLIN);

		} catch (Exception e) {
			e.printStackTrace();
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

	public void setName (String name) {
		this.projectName.setText(name);
	}

	public void setProject(ProjectVersion project) {
		this.project = project;
		setAllFields();
	}

	private void setAllFields() {
		if (project != null) {
			projectManager.setText(project.getProjectManager());
			propNum.setText(project.getProposalNumber());
			versionNum.setText(project.getVersionNumber());

			startDate.setValue(project.getPopStart());
			startDate.setDisable(true);
			endDate.setValue(project.getPopEnd());
			endDate.setDisable(true);
			
			clinObservableList.setAll(project.getCLINList());
			sowObservableList.setAll(project.getSOWList());
			sdrlObservableList.setAll(project.getSDRLList());

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
			
		} else {
			System.out.println("ERROR: NULL PROJECT");
		}
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}
	
	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}
	
	private void closeCurrent() {

		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
