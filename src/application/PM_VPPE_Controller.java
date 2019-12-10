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
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PM_VPPE_Controller implements Initializable {

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

    public void closeEstimate() {
        //prevController.refresh();
        StageHandler.showPreviousStage();
        StageHandler.closeCurrentStage();
    }

    public void makePending (ActionEvent event) throws SQLException, ClassNotFoundException {
        if (aOrd) {
            DBUtil.dbExecuteUpdate("CALL return_from_approved(" + proj.getProjectID() + ")");
        }
        else {
            DBUtil.dbExecuteUpdate("CALL return_from_denied(" + proj.getProjectID() + ")");
        }
        closeEstimate();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCLINEstimate.fxml"));
            Parent root = fxmlLoader.load();

            Stage viewCLIN = new Stage();
            viewCLIN.setTitle("Estimation Suite - Project Manager - Estimate Project");
            viewCLIN.setScene(new Scene(root));

            viewCLIN.show();
            viewCLIN.setResizable(true);
            viewCLIN.sizeToScene();

            Stage stage = (Stage) viewCLINEstimateButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
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

        } else {
            System.out.println("ERROR: NULL PROJECT");
        }
    }
    
    private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
