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

public class ViewCLIN_Estimate_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button viewOrganizationButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void viewOrganization(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewOrganizationBOE.fxml"));
            Parent root = fxmlLoader.load();

            Stage viewOrg = new Stage();
            viewOrg.setTitle("Estimation Suite - Project Manager - Project");
            viewOrg.setScene(new Scene(root));

            viewOrg.show();
            viewOrg.setResizable(true);
            viewOrg.sizeToScene();

            Stage stage = (Stage) viewOrganizationButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_ViewProjectEstimate.fxml"));
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
