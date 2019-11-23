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

public class OrganizationBOE_Controller implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button addWorkPackButton;
    @FXML
    private Button editWorkPackButton;
    @FXML
    private Button removeWorkPackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addWorkPack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
            Parent root = fxmlLoader.load();

            Stage addWPStage = new Stage();
            addWPStage.setTitle("Estimation Suite - Estimator - Estimate Project");
            addWPStage.setScene(new Scene(root));

            addWPStage.show();
            addWPStage.setResizable(true);
            addWPStage.sizeToScene();

            Stage stage = (Stage) addWorkPackButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editOrganization(ActionEvent event) {

    }

    public void removeOrganization(ActionEvent event) {

    }

    public void close(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN_Estimate.fxml"));
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
