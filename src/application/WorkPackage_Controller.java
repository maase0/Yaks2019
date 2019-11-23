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

public class WorkPackage_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button editTaskButton;
    @FXML
    private Button removeTaskButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addTask(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Task.fxml"));
            Parent root = fxmlLoader.load();

            Stage addTaskStage = new Stage();
            addTaskStage.setTitle("Estimation Suite - Estimator - Estimate Project");
            addTaskStage.setScene(new Scene(root));

            addTaskStage.show();
            addTaskStage.setResizable(true);
            addTaskStage.sizeToScene();

            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editTask(ActionEvent event) {

    }

    public void removeTask(ActionEvent event) {

    }

    public void close(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrganizationBOE.fxml"));
            Parent root = fxmlLoader.load();

            Stage closeStage = new Stage();
            closeStage.setTitle("Estimation Suite - Estimator - Estimate Project");
            closeStage.setScene(new Scene(root));

            closeStage.show();
            closeStage.setResizable(true);
            closeStage.sizeToScene();

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
