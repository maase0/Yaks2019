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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class WorkPackage_Controller implements Initializable, Refreshable {

	@FXML
	private Button closeButton;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button editTaskButton;
	@FXML
	private Button removeTaskButton;

	@FXML
	private TextField name;
	@FXML
	private TextField author;
	@FXML
	private TextField scope;
	@FXML
	private TextField type;
	@FXML
	private TextField version;

	@FXML
	DatePicker startDate;
	@FXML
	DatePicker endDate;

	@FXML
	private ListView<Task> taskListView;
	private ObservableList<Task> taskObservableList;

	private ObservableList<WorkPackage> workPackageObservableList;
	private Refreshable prevController;

	private ArrayList<Task> taskDelete;

	private WorkPackage workPackage;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		taskObservableList = FXCollections.observableArrayList();
		taskListView.setItems(taskObservableList);
		workPackage = null;
		version.setText("1");
	}

	public void refresh() {

	}

	public void addTask(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
			Parent root = fxmlLoader.load();

			Task_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setTaskList(taskObservableList);

			Stage addTaskStage = new Stage();
			addTaskStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addTaskStage.setScene(new Scene(root));

			addTaskStage.show();
			addTaskStage.setResizable(true);
			addTaskStage.sizeToScene();

			StageHandler.addStage(addTaskStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editTask(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
			Parent root = fxmlLoader.load();

			Task_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setTaskList(taskObservableList);
			controller.setTask(taskListView.getSelectionModel().getSelectedItem());

			Stage addTaskStage = new Stage();
			addTaskStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			addTaskStage.setScene(new Scene(root));

			addTaskStage.show();
			addTaskStage.setResizable(true);
			addTaskStage.sizeToScene();

			StageHandler.addStage(addTaskStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeTask(ActionEvent event) {
		Task task = taskObservableList.remove(taskListView.getSelectionModel().getSelectedIndex());
		if (task.getID() != null) {
			taskDelete.add(task);
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

	public boolean save() {

		boolean passed = true;
		String errorMessage = "";

		String versionReg = "\\d+(.\\d)*";

		if (!version.getText().matches(versionReg)) {
			passed = false;
			errorMessage += "Error: Invalid version! Use form 1, 1.2, 1.2.3, etc.\n";
		}
		
		if (startDate.getValue() != null && endDate.getValue() != null
				&& startDate.getValue().compareTo(endDate.getValue()) > 0) {
			passed = false;
			errorMessage += "Error: End date must be after start date!\n";
		}

		if (passed) {
			boolean flag = workPackage == null;
			if (flag) {
				workPackage = new WorkPackage();
				workPackage.setOldVersoin(version.getText());
			}

			workPackage.setName(name.getText());
			workPackage.setWptype(type.getText());
			workPackage.setAuthor(author.getText());
			workPackage.setScope(scope.getText());
			workPackage.setVersion(version.getText());
			workPackage.setPopEnd(endDate.getValue() != null ? endDate.getValue().toString() : null);
			workPackage.setPopStart(startDate.getValue() != null ? startDate.getValue().toString() : null);
			workPackage.setTasks(new ArrayList<Task>(taskObservableList));

			if (flag) {
				workPackageObservableList.add(workPackage);
			}
		} else {
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

	public void saveAndClose() {
		if (save()) {
			closeCurrent();
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

	public void setWorkPackageList(ObservableList<WorkPackage> list) {
		this.workPackageObservableList = list;
	}

	public void setWorkPackage(WorkPackage wp) {
		this.workPackage = wp;
		setAllFields();
	}

	private void setAllFields() {

		name.setText(workPackage.getName());
		author.setText(workPackage.getAuthor());
		scope.setText(workPackage.getScope());
		type.setText(workPackage.getWptype());
		version.setText(workPackage.getVersion());

		if (workPackage.getPopStart() != null) {
			startDate.setValue(LocalDate.parse(workPackage.getPopStart()));
		}
		if (workPackage.getPopEnd() != null) {
			endDate.setValue(LocalDate.parse(workPackage.getPopEnd()));
		}

		
		taskObservableList.addAll(workPackage.getTasks());
	}

	public ArrayList<Task> getDeleteList() {
		return taskDelete;
	}
}
