package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
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

	}

	public void saveTask() {
		boolean flag = task == null;
		if (flag) {
			task = new Task();
		}
		task.setName(name.getText());
		task.setFormula(formula.getText());
		task.setStaffHours(Integer.parseInt(hours.getText()));
		task.setVersion(version.getText());
		task.setPopStart(startDate.getValue().toString());
		task.setPopEnd(endDate.getValue().toString());
		task.setDetails(details.getText());
		task.setConditions(conditions.getText());
		task.setMethodology(methodology.getText());

		if (flag) {
			taskObservableList.add(task);
		}
	}

	public void saveAndclose(ActionEvent event) {
		saveTask();
		close();
	}

	public void close() {
		closeCurrent();
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

		startDate.setValue(LocalDate.parse(task.getPopStart()));
		endDate.setValue(LocalDate.parse(task.getPopEnd()));

		details.setText(task.getDetails());
		conditions.setText(task.getConditions());
		methodology.setText(task.getMethodology());
	}
}
