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

public class PM_VPE_Controller implements Initializable {

    @FXML
    private Button returnEstimationButton;
    @FXML
    private Button approveButton;
    @FXML
    private Button denyButton;
    @FXML
    private Button viewCLINestimateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void returnEstimation(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_Projects.fxml"));
            Parent root = fxmlLoader.load();

            Stage returnEst = new Stage();
            returnEst.setTitle("Estimation Suite - Project Manager - Estimate Project");
            returnEst.setScene(new Scene(root));

            returnEst.show();
            returnEst.setResizable(true);
            returnEst.sizeToScene();

            Stage stage = (Stage) returnEstimationButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void approveProject(ActionEvent event) {

    }

    public void denyProject(ActionEvent event) {

    }

    public void viewCLINestimation(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCLINEstimate.fxml"));
            Parent root = fxmlLoader.load();

            Stage viewCLIN = new Stage();
            viewCLIN.setTitle("Estimation Suite - Project Manager - Estimate Project");
            viewCLIN.setScene(new Scene(root));

            viewCLIN.show();
            viewCLIN.setResizable(true);
            viewCLIN.sizeToScene();

            Stage stage = (Stage) viewCLINestimateButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
