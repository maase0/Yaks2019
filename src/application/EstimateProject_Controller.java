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

public class EstimateProject_Controller implements Initializable {

    @FXML
    private Button discardButton;

    public EstimateProject_Controller() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void submitApproval(ActionEvent event) {

    }

    public void saveNewChanges(ActionEvent event) {

    }

    public void discardChanges(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Estimator_Projects.fxml"));

            Stage pmProjectsStage = new Stage();
            pmProjectsStage.setTitle("Estimation Suite - Estimator - Projects");
            pmProjectsStage.setScene(new Scene(root));
            pmProjectsStage.show();

            Stage stage = (Stage) discardButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
