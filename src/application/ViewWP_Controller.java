package application;

import javafx.collections.FXCollections;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewWP_Controller implements Initializable, Refreshable {

    @FXML
    private Button closeButton;
    @FXML
    private Button viewTaskButton;
    @FXML
    private Label name;
    @FXML
    private Label author;
    @FXML
    private Label scope;
    @FXML
    private Label type;
    @FXML
    private Label version;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;

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
    }

    public void refresh() {
    }

    public void viewTask(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewTask.fxml"));
            Parent root = fxmlLoader.load();

            ViewTask_Controller controller = fxmlLoader.getController();

            controller.setPreviousController(this);
            controller.setTaskList(taskObservableList);
            controller.setTask(taskListView.getSelectionModel().getSelectedItem());

            Stage viewTask = new Stage();
            viewTask.setTitle("Estimation Suite - Project Manager - Project");
            viewTask.setScene(new Scene(root));

            viewTask.show();
            viewTask.setResizable(true);
            viewTask.sizeToScene();

            StageHandler.addStage(viewTask);
            StageHandler.hidePreviousStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        type.setText(workPackage.getTypeOfWork());
        version.setText(workPackage.getVersion());
        startDate.setValue(LocalDate.parse(workPackage.getPopStart()));
        startDate.setDisable(true);
        endDate.setValue(LocalDate.parse(workPackage.getPopEnd()));
        endDate.setDisable(true);
        taskObservableList.addAll(workPackage.getTasks());
    }
}
