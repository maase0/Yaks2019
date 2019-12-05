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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class PM_EditProjectController implements Initializable {

	private ProjectVersion proj;

	private ResultSet rs;
	@FXML
	private Button saveChangesButton;
	@FXML
	private Button discardChangesButton;
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
	private ObservableList<CLIN> clinObservableList;

	// SDRL List fields
	@FXML
	private ListView<SDRL> SDRLListView;
	private ObservableList<SDRL> sdrlObservableList;

	// SOW List fields
	@FXML
	private ListView<SOW> SOWListView;
	private ObservableList<SOW> sowObservableList;

	private ArrayList<CLIN> clinDelete = new ArrayList<CLIN>();
	private ArrayList<SOW> sowDelete = new ArrayList<SOW>();
	private ArrayList<SDRL> sdrlDelete = new ArrayList<SDRL>();

	private Refreshable prevController;

	public PM_EditProjectController() {

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
		// clinObservableList.remove(CLINListView.getSelectionModel().getSelectedItem());
		clinDelete.add(clinObservableList.remove(CLINListView.getSelectionModel().getSelectedIndex()));
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
		sdrlDelete.add(sdrlObservableList.remove(SDRLListView.getSelectionModel().getSelectedIndex()));
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
		sowDelete.add(sowObservableList.remove(SOWListView.getSelectionModel().getSelectedIndex()));
	}

	@FXML
	/**
	 * Saves all of the information of the newly created project
	 *
	 * @param event
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void saveNewChanges() throws SQLException, ClassNotFoundException {

		save();

		closeCurrent();

	}

	private void save() throws ClassNotFoundException, SQLException {
		int vid = 0;

		boolean passed = true;

		String versionReg = "\\d(.\\d)*";
		String propReg = "^[0-9]*$";
		String sowRefReg = "^[0-9]*$";

		if (!versionText.getText().matches(versionReg)) {
			passed = false;
			System.out.println(
					"Error: Version Text \"" + versionText.getText() + "\" does not match regexp " + versionReg);
		}

		// Checks that version number has not decreased.
		String[] newVer = versionText.getText().split("\\.");
		String[] oldVer = proj.getVersionNumber().split("\\.");
		for (int i = 0; i < newVer.length & i < oldVer.length; i++) {
			if (Integer.parseInt(newVer[i]) > Integer.parseInt(oldVer[i])) {
				break;
				// If greater, then rest is fine
			} else if (Integer.parseInt(newVer[i]) < Integer.parseInt(oldVer[i])) {
				passed = false;
				System.err.println("ERROR: Cannot change to a lower version number!");
				break;
			}
			// no else, if they are equal keep going.
		}

		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + proj.getProjectID()
				+ " AND Version_Number=\"" + versionText.getText() + "\"");

		if (rs.next() && !versionText.getText().equals(proj.getVersionNumber())) {
			passed = false;
			System.err.println("ERROR: Cannot change to an existing version number!");
		}
		rs.close();

		if (!propNumText.getText().matches(propReg)) {
			passed = false;
			System.out.println("Error: Version Proposal Number \"" + propNumText.getText() + "\" does not match regexp "
					+ propReg);
		}
		for (

		SOW s : sowObservableList) {
			if (!s.getReference().matches(sowRefReg)) {
				passed = false;
				System.out.println(
						"Error: Sow Reference \"" + "" + s.getReference() + "\" does not match regexp " + sowRefReg);
			}
		}

		if (passed) {
			System.out.println("Save Changes Button");
			// `update_projectVersion`(VID int, versionNumber varchar(45), projectName
			// varchar(45), projectManager varchar(45), propNum int, popStart date, popEnd
			// date)
			rs = DBUtil.dbExecuteQuery("CALL update_projectVersion(" + proj.getProjectVersionID() + ", \""
					+ versionText.getText() + "\", \"" + projectNameText.getText() + "\", \"" + pmText.getText()
					+ "\", " + propNumText.getText() + ", '" + startDate.getValue() + "', '" + endDate.getValue()
					+ "')");
			/*
			 * ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_new_project(" +
			 * versionText.getText() + ", \"" + projectNameText.getText() + "\", \"" +
			 * pmText.getText() + "\", " + propNumText.getText() + ",'" +
			 * startDate.getValue().toString() + "', '" + endDate.getValue().toString() +
			 * "')");
			 */
			while (rs.next()) {
				vid = rs.getInt("idProjectVersion");
			}
			rs.close();

			if (proj.getVersionNumber().equals(versionText.getText())) {

				// If the item has an id, then it was loaded from the database and already
				// exists
				// update existing items, insert new items.
				for (CLIN c : clinObservableList) {
					if (c.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL update_clin(" + c.getID() + ", " + vid + ", \"" + c.getIndex()
								+ "\" , \"" + c.getVersion() + "\", \"" + c.getProjectType() + "\", \""
								+ c.getClinContent() + "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \""
								+ c.getVersion() + "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent()
								+ "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
					}
				}

				for (SDRL s : sdrlObservableList) {
					if (s.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL update_sdrl(" + s.getID() + ", " + vid + ", \"" + s.getName()
								+ "\", \"" + s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \""
								+ s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
					}
				}

				for (SOW s : sowObservableList) {
					if (s.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL update_sow(" + s.getID() + ", " + vid + ", " + s.getReference()
								+ ", \"" + s.getVersion() + "\", \"" + s.getSowContent() + "\")");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \""
								+ s.getVersion() + "\", \"" + s.getSowContent() + "\")");
					}
				}

				// Delete all the list items that were saved for deletion
				// delete only if version is the same, if version has changed
				// "deleted" items are just not copied over
				for (CLIN c : clinDelete) {
					DBUtil.dbExecuteUpdate("CALL delete_clin(" + c.getID() + ")");
				}

				for (SOW s : sowDelete) {
					DBUtil.dbExecuteUpdate("CALL delete_sow(" + s.getID() + ")");
				}

				for (SDRL s : sdrlDelete) {
					DBUtil.dbExecuteUpdate("CALL delete_sdrl(" + s.getID() + ")");
				}
			}

			// This is if the version number changed.
			// Re-insert all the items with the new vid(version id)
			else {
				for (CLIN c : clinObservableList) {
					if (c.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL clone_clin(" + c.getID() + ", " + vid + ", \"" + c.getIndex()
								+ "\" , \"" + c.getVersion() + "\", \"" + c.getProjectType() + "\", \""
								+ c.getClinContent() + "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \""
								+ c.getVersion() + "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent()
								+ "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
					}
				}

				for (SDRL s : sdrlObservableList) {
					if (s.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL clone_sdrl(" + s.getID() + ", " + vid + ", \"" + s.getName()
								+ "\", \"" + s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \""
								+ s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
					}
				}

				for (SOW s : sowObservableList) {
					if (s.getID() != null) {
						DBUtil.dbExecuteUpdate("CALL update_sow(" + s.getID() + ", " + vid + ", " + s.getReference()
								+ ", \"" + s.getVersion() + "\", \"" + s.getSowContent() + "\")");
					} else {
						DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \""
								+ s.getVersion() + "\", \"" + s.getSowContent() + "\")");
					}
				}

			}
		}
	}

	@FXML
	/**
	 * Discards the project without saving it do the database
	 *
	 * @param event
	 */
	public void discardNewChanges(ActionEvent event) {

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

	@FXML
	/**
	 * Submits the project for estimation Code is similar to saveChanges, except
	 * Submit for Estimation in the New Project Page saves the project and adds a
	 * submission date.
	 *
	 */
	public void submitForEstimation(ActionEvent event) throws SQLException, ClassNotFoundException {
		int vid = 0;

		boolean passed = true;

		if (passed) {
			save();
			String startString = startDate.getValue() == null ? "" : startDate.getValue().toString();
			String endString = endDate.getValue() == null ? "" : endDate.getValue().toString();

			System.out.println("Submit for Estimation Button");

			DBUtil.dbExecuteUpdate(
					"CALL submit_project(" + proj.getProjectID() + ", '" + LocalDate.now().toString() + "')");

			try {
				closeCurrent();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void setProject(ProjectVersion proj) {
		this.proj = proj;
		setAllFields();
	}

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
