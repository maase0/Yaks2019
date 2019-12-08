package application;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class PM_NewProjectController implements Initializable {

	private ProjectVersion proj;

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
		save();
		closeCurrent();

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

			DBUtil.dbExecuteUpdate(
					"CALL submit_project(" + projectID + ", '" + LocalDate.now().toString() + "')");

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
		// TODO possibly do a datatype check before actually saving anything.
		int vid = 0;

		boolean passed = true;

		// TODO: Find acceptable regexps for each field
		// add checks for clin dates etc discussed in sprint review
		// change to an error popup instead of printing to console

		String versionReg = "\\d*(.\\d)*";
		String propReg = "^[0-9]*$";
		String sowRefReg = "^[0-9]*$";

		if (!versionText.getText().matches(versionReg)) {
			passed = false;
			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Error_Window.fxml"));
				Parent root = fxmlLoader.load();

				ErrorWindow controller = fxmlLoader.getController();

				controller.errorMessage(
						"Version Text \"" + versionText.getText() + "\" does not match regexp " + versionReg);

				Stage errorStage = new Stage();
				errorStage.setTitle("ERROR");
				errorStage.setScene(new Scene(root));

				errorStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// System.out.println("Error: Version Text \"" + versionText.getText() + "\"
			// does not match regexp " + versionReg);
		}
		if (!propNumText.getText().matches(propReg)) {
			passed = false;

			try {

				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Error_Window.fxml"));
				Parent root = fxmlLoader.load();

				ErrorWindow controller = fxmlLoader.getController();

				controller.errorMessage(
						"Version Proposal Number \"" + propNumText.getText() + "\" does not match regexp " + propReg);

				Stage errorStage = new Stage();
				errorStage.setTitle("ERROR");
				errorStage.setScene(new Scene(root));

				errorStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}

//			System.out.println("Error: Version Proposal Number \"" + propNumText.getText() + "\" does not match regexp " + propReg);
		}
		for (SOW s : sowObservableList) {
			if (!s.getReference().matches(sowRefReg)) {
				passed = false;

				try {

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Error_Window.fxml"));
					Parent root = fxmlLoader.load();

					ErrorWindow controller = fxmlLoader.getController();

					controller.errorMessage(
							"Sow Reference \"" + "" + s.getReference() + "\" does not match regexp " + sowRefReg);

					Stage errorStage = new Stage();
					errorStage.setTitle("ERROR");
					errorStage.setScene(new Scene(root));

					errorStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}

//				System.out.println("Error: Sow Reference \"" + "" + s.getReference() + "\" does not match regexp " + sowRefReg);
			}
		}

		if (passed) {
			System.out.println("Save Changes Button");
			ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_new_project('" + versionText.getText() + "', \""
					+ projectNameText.getText() + "\", \"" + pmText.getText() + "\", " + propNumText.getText() + ", '"
					+ startDate.getValue() + "', '" + endDate.getValue() + "')");
			while (rs.next()) {
				vid = rs.getInt("idProjectVersion");
			}
			rs.close();
			
			rs = DBUtil.dbExecuteQuery("SELECT idProject FROM ProjectVersion WHERE idProjectVersion = " + vid);
			rs.last();
			projectID = rs.getInt("idProject");

			for (CLIN c : clinObservableList) {
				DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \"" + c.getVersion()
						+ "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent() + "\", '" + c.getPopStart()
						+ "', '" + c.getPopEnd() + "')");
			}

			for (SDRL s : sdrlObservableList) {
				DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \"" + s.getVersion()
						+ "\", \"" + s.getSdrlInfo() + "\")");
			}

			for (SOW s : sowObservableList) {
				DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \"" + s.getVersion()
						+ "\", \"" + s.getSowContent() + "\")");
			}

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
