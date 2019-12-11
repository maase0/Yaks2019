package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ViewTask_Controller implements Initializable, Refreshable {

    @FXML
    private Button closeButton;
    @FXML
    private Label name;
    @FXML
    private Label formula;
    @FXML
    private Label hours;
    @FXML
    private Label version;
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

    public void refresh() {
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
        startDate.setDisable(true);
        endDate.setValue(LocalDate.parse(task.getPopEnd()));
        endDate.setDisable(true);
        details.setText(task.getDetails());
        conditions.setText(task.getConditions());
        methodology.setText(task.getMethodology());
    }
}
