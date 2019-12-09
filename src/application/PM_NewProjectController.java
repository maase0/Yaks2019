package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class PM_NewProjectController implements Initializable {

	private ProjectVersion proj;

	@FXML private Label error8;
	@FXML private Label error1;
	@FXML private Label error2;
	@FXML private Label error3;
	@FXML private Label error4;
	@FXML private Label error5;
	@FXML private Label error6;
	@FXML private Label error7;
	
	private ResultSet rs;
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
	private TextField propNumText;
	@FXML
	private TextField versionText;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;

	// CLIN List fields
	@FXML
	private ListView<CLIN> CLINListView;
	@FXML
	private Button addCLINButton;
	@FXML
	private Button editCLINButton;
	@FXML
	private Button removeCLINButton;
	private ObservableList<CLIN> clinObservableList;

	// SDRL List fields
	@FXML
	private ListView<SDRL> SDRLListView;
	@FXML
	private Button addSDRLButton;
	@FXML
	private Button editSDRLButton;
	@FXML
	private Button removeSDRLButton;
	private ObservableList<SDRL> sdrlObservableList;

	// SOW List fields
	@FXML
	private ListView<SOW> SOWListView;
	@FXML
	private Button addSOWButton;
	@FXML
	private Button editSOWButton;
	@FXML
	private Button removeSOWButton;
	private ObservableList<SOW> sowObservableList;

	private Refreshable prevController;
	private int projectID;

	public PM_NewProjectController() {

	}

	public void initialize(URL location, ResourceBundle resources) {
		// Create a new obesrvable list for the CLINS, gives it to the list view
		clinObservableList = FXCollections.observableArrayList();
		CLINListView.setItems(clinObservableList);

		sdrlObservableList = FXCollections.observableArrayList();
		SDRLListView.setItems(sdrlObservableList);

		sowObservableList = FXCollections.observableArrayList();
		SOWListView.setItems(sowObservableList);
		
		error1.setVisible(false);
		error2.setVisible(false);
		error3.setVisible(false);
		error4.setVisible(false);
		error5.setVisible(false);
		error6.setVisible(false);
		error7.setVisible(false);
		error8.setVisible(false);
	}

	/**
	 * Adds a new clin to the list. Creates a popup menu to start editing a new CLIN
	 *
	 * @param event
	 */
	public void addCLIN(ActionEvent event) {
		Parent root = null;
		try {
			// Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			// Grab the controller from the loader
			CLINController controller = fxmlLoader.<CLINController>getController();
			// Set the controller's list to allow message passing
			controller.setList(clinObservableList);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Removes the selected CLIN from the list view and observable list.
	 *
	 * @param event
	 */
	public void discardCLIN(ActionEvent event) {
		clinObservableList.remove(CLINListView.getSelectionModel().getSelectedItem());
	}

	/**
	 * Edit an existing CLIN that is selected in the list view.
	 *
	 * @param event
	 */
	public void editCLIN(ActionEvent event) {
		// get the clin to be edited
		CLIN clin = CLINListView.getSelectionModel().getSelectedItem();

		try {
			// Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN.fxml"));
			Parent root1;
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			// Grab the controller from the loader and set it's list for message passing
			CLINController controller = fxmlLoader.<CLINController>getController();
			controller.setList(clinObservableList);

			// Set the controller's CLIN to the existing one
			controller.setCLIN(clin);
			// Set all of the controller's input fields
			controller.setInputFields();

			stage.show(); // Show the popup editor

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addSDRL(ActionEvent event) {
		Parent root = null;
		try {
			// Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SDRL.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			// Grab the controller from the loader
			SDRLController controller = fxmlLoader.<SDRLController>getController();
			// Set the controller's list to allow message passing
			controller.setList(sdrlObservableList);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editSDRL(ActionEvent event) {
		SDRL sdrl = SDRLListView.getSelectionModel().getSelectedItem();

		try {
			// Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SDRL.fxml"));
			Parent root;
			root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			// Grab the controller from the loader and set it's list for message passing
			SDRLController controller = fxmlLoader.<SDRLController>getController();
			controller.setList(sdrlObservableList);

			controller.setSDRL(sdrl);
			// Set all of the controller's input fields
			controller.setInputFields();

			stage.show(); // Show the popup editor

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeSDRL(ActionEvent event) {
		sdrlObservableList.remove(SDRLListView.getSelectionModel().getSelectedItem());
	}

	public void addSOW(ActionEvent event) {
		Parent root = null;
		try {
			// Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SOWRef.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			// Grab the controller from the loader
			SOWController controller = fxmlLoader.<SOWController>getController();
			// Set the controller's list to allow message passing
			controller.setList(sowObservableList);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void editSOW(ActionEvent event) {
		SOW sow = SOWListView.getSelectionModel().getSelectedItem();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SOWRef.fxml"));
			Parent root;
			root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));

			SOWController controller = fxmlLoader.<SOWController>getController();
			controller.setList(sowObservableList);

			controller.setSOW(sow);
			controller.setInputFields();

			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeSOW(ActionEvent event) {
		sowObservableList.remove(SOWListView.getSelectionModel().getSelectedItem());
	}

	@FXML
	/**
	 * Saves all of the information of the newly created project
	 *
	 * @param event
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void saveChanges() throws SQLException, ClassNotFoundException {
		if (save()) {
			closeCurrent();
		}

	}

	@FXML
	/**
	 * Discards the project without saving it do the database
	 *
	 * @param event
	 */
	public void discardChanges(ActionEvent event) {
		try {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	/**
	 * Submits the project for estimation Code is similar to saveChanges, except
	 * Submit for Estimation in the New Project Page saves the project and adds a
	 * submission date.
	 *
	 */

	public void submitForEstimation(ActionEvent event) throws SQLException, ClassNotFoundException {
		int vid = 0;

		save();

		String errorMessage = ProjectHandler.checkProjectForSubmission(proj);

		if (errorMessage == null) {
			String startString = startDate.getValue() == null ? "" : startDate.getValue().toString();
			String endString = endDate.getValue() == null ? "" : endDate.getValue().toString();

			System.out.println("Submit for Estimation Button");

			DBUtil.dbExecuteUpdate("CALL submit_project(" + projectID + ", '" + LocalDate.now().toString() + "')");

			try {
				closeCurrent();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Submitting Project");
			alert.setHeaderText("There was an error submitting this project!");
			alert.setContentText(errorMessage);

			// ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
			ButtonType buttonTypeCancel = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeCancel);
			alert.showAndWait();
		}
	}

	private boolean save() throws ClassNotFoundException, SQLException {
boolean passed = false;
		
		ProjectVersion newProj = new ProjectVersion();

		
		newProj.setName(projectNameText.getText());
		newProj.setPopEnd(endDate.getValue());
		newProj.setPopStart(startDate.getValue());
		newProj.setProjectManager(pmText.getText());
		newProj.setProposalNumber(propNumText.getText());
		newProj.setVersionNumber(versionText.getText());

		newProj.setCLINList(new ArrayList<CLIN>(clinObservableList));
		newProj.setSDRLList(new ArrayList<SDRL>(sdrlObservableList));
		newProj.setSOWList(new ArrayList<SOW>(sowObservableList));
		
		String errorMessage = ProjectHandler.checkProjectForSaving(newProj);

		if (errorMessage == null) {
			ProjectHandler.saveNewProject(newProj);
			proj = newProj;
			passed = true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Saving Project");
			alert.setHeaderText("There was an error saving this project!");
			alert.setContentText(errorMessage);

			// ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
			ButtonType buttonTypeCancel = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeCancel);
			alert.showAndWait();

		}

		return passed;
	}

	/**
	 * Sets the project version
	 * 
	 * @param proj
	 */
	public void setProject(ProjectVersion proj) {
		this.proj = proj;
		setAllFields();
	}

	/**
	 * Sets the data for all the various input fields in the project information,
	 * inserts CLINs, SOWs, SDRLs into their respective lists
	 */
	private void setAllFields() {
		clinObservableList.addAll(proj.getCLINList());
		sowObservableList.addAll(proj.getSOWList());
		sdrlObservableList.addAll(proj.getSDRLList());

		versionText.setText(proj.getVersionNumber());
		projectNameText.setText(proj.getName());
		pmText.setText(proj.getProjectManager());
		propNumText.setText(proj.getProposalNumber());

		startDate.setValue(proj.getPopStart());
		endDate.setValue(proj.getPopEnd());
	}

	public void setPreviousController(PM_ProjectsController controller) {
		this.prevController = controller;
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
