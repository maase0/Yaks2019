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

public class Task_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button saveAndcloseButton;

	private Refreshable prevController;

    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveTask(ActionEvent event) {

    }

    public void saveAndclose(ActionEvent event) {

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
