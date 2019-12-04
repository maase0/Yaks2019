package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class Task_Controller implements Initializable {

	@FXML
	private Button closeButton;
	@FXML
	private Button saveButton;
	@FXML
	private Button saveAndcloseButton;
	@FXML
	private TextField name;
	@FXML
	private TextField formula;
	@FXML
	private TextField hours;
	@FXML
	private TextField version;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	@FXML
	private TextArea details;
	@FXML
	private TextArea conditions;
	@FXML
	private TextArea methodology;

	private Refreshable prevController;
	private ObservableList<Task> taskObservableList;

	private Task task;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		version.setText("1");

	}

	public boolean saveTask() {
		boolean passed = true;
		String errorMessage = "";

		String versionReg = "\\d+(.\\d)*";
		String staffReg = "\\d*(.\\d)?";

		if (!version.getText().matches(versionReg)) {
			passed = false;
			errorMessage += "Error: Invalid version! Use form 1, 1.2, 1.2.3, etc.\n";
		}
		if (!hours.getText().matches(staffReg)) {
			passed = false;
			errorMessage += "Error: Staff Hours must be a positive number!\n";
		}
		if (startDate.getValue() != null && endDate.getValue() != null
				&& startDate.getValue().compareTo(endDate.getValue()) > 0) {
			passed = false;
			errorMessage += "Error: End date must be after start date!\n";
		}

		if (passed) {

			boolean flag = task == null;
			if (flag) {
				task = new Task();
				task.setOldVersion(version.getText());
			}

			task.setName(name.getText());
			task.setFormula(formula.getText());
			if (hours.getText().equals("")) {
				task.setStaffHours(0);
			} else {
				task.setStaffHours(Integer.parseInt(hours.getText()));
			}
			task.setVersion(version.getText());
			task.setPopStart(startDate.getValue() != null ? startDate.getValue().toString() : null);
			task.setPopEnd(endDate.getValue() != null ? endDate.getValue().toString() : null);
			task.setDetails(details.getText());
			task.setConditions(conditions.getText());
			task.setMethodology(methodology.getText());

			if (flag) {
				taskObservableList.add(task);
			}
		} else

		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Saving Task");
			alert.setHeaderText("There was an error saving this task!");
			alert.setContentText(errorMessage);

			// ButtonType buttonTypeOne = new ButtonType("Discard Changes ");
			ButtonType buttonTypeCancel = new ButtonType("OK", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeCancel);
			alert.showAndWait();
		}

		return passed;
	}

	public void saveAndclose(ActionEvent event) {
		if (saveTask()) {
			closeCurrent();
		}
	}

	public void close() {
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

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}

	public void setTaskList(ObservableList<Task> list) {
		this.taskObservableList = list;
	}

	public void setTask(Task task) {
		this.task = task;
		setAllFields();
	}

	private void setAllFields() {

		name.setText(task.getName());
		formula.setText(task.getFormula());
		hours.setText("" + task.getStaffHours());
		version.setText(task.getVersion());

		if (task.getPopStart() != null) {
			startDate.setValue(LocalDate.parse(task.getPopStart()));
		}
		if (task.getPopEnd() != null) {
			endDate.setValue(LocalDate.parse(task.getPopEnd()));
		}

		details.setText(task.getDetails());
		conditions.setText(task.getConditions());
		methodology.setText(task.getMethodology());
	}
}
