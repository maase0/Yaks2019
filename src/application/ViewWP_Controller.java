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

public class ViewWP_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button viewTaskButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void viewTask(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewTask.fxml"));
            Parent root = fxmlLoader.load();

            Stage viewTask = new Stage();
            viewTask.setTitle("Estimation Suite - Project Manager - Project");
            viewTask.setScene(new Scene(root));

            viewTask.show();
            viewTask.setResizable(true);
            viewTask.sizeToScene();

            Stage stage = (Stage) viewTaskButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewOrganizationBOE.fxml"));
            Parent root = fxmlLoader.load();

            Stage closeStage = new Stage();
            closeStage.setTitle("Estimation Suite - Project Manager - Project");
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
