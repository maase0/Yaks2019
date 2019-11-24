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

public class ViewOrganization_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button viewWorkPackageButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void viewWorkPackage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewWorkPackage.fxml"));
            Parent root = fxmlLoader.load();

            Stage viewWP = new Stage();
            viewWP.setTitle("Estimation Suite - Project Manager - Project");
            viewWP.setScene(new Scene(root));

            viewWP.show();
            viewWP.setResizable(true);
            viewWP.sizeToScene();

            Stage stage = (Stage) viewWorkPackageButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCLINEStimate.fxml"));
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
