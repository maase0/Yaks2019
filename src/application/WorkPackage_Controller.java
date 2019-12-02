package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
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

	private Refreshable prevController;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

	}

	public void refresh() {

	}

	public void addTask(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
			Parent root = fxmlLoader.load();

			Task_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			
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

	}

	public void removeTask(ActionEvent event) {

	}

	public void close(ActionEvent event) {
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
}
