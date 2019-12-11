package application;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PM_VPPE_Controller implements Initializable, Refreshable {

	private Refreshable prevController;
	
    @FXML
    private Button makePendingButton;
    @FXML
    private javafx.scene.control.Button closeEstimateButton;
    @FXML
    private Button viewCLINEstimateButton;
    @FXML
    private Label projectName;
    @FXML
    private Label status;
    @FXML
    private Label projectManager;
    @FXML
    private Label propNum;
    @FXML
    private Label versionNum;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

    @FXML
    private ListView<CLIN> clinListView;
    private ObservableList<CLIN> clinObservableList;

    @FXML
    private ListView<SDRL> sdrlListView;
    private ObservableList<SDRL> sdrlObservableList;

    @FXML
    private ListView<SOW> sowListView;
    private ObservableList<SOW> sowObservableList;

    @FXML
    private ListView<CLIN> clinEstimateListView;

    private ProjectVersion proj;
    private boolean aOrd;

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

    public void closeEstimate() {
        closeCurrent();
    }

    public void makePending (ActionEvent event) throws SQLException, ClassNotFoundException {
        if (aOrd) {
            DBUtil.dbExecuteUpdate("CALL return_from_approved(" + proj.getProjectID() + ")");
        }
        else {
            DBUtil.dbExecuteUpdate("CALL return_from_denied(" + proj.getProjectID() + ")");
        }
        closeCurrent();
    }

    public void setStatus (String status) {
        this.status.setText(status);
    }

    public void setName (String name) {
        this.projectName.setText(name);
    }

    public void setAorD (Boolean aord) { //sets flag for Approved or Denied
        this.aOrd = aord;
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

    public void setProject(ProjectVersion project) {
        this.proj = project;
        setAllFields();
    }

    private void setAllFields() {
        if (proj != null) {
            projectManager.setText(proj.getProjectManager());
            propNum.setText(proj.getProposalNumber());
            versionNum.setText(proj.getVersionNumber());

            startDate.setValue(proj.getPopStart());
            startDate.setDisable(true);
            endDate.setValue(proj.getPopEnd());
            endDate.setDisable(true);

            clinObservableList.setAll(proj.getCLINList());
            sowObservableList.setAll(proj.getSOWList());
            sdrlObservableList.setAll(proj.getSDRLList());

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
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
