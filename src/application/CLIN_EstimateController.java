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

public class CLIN_EstimateController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button addOrgButton;
    @FXML
    private Button editOrgButton;
    @FXML
    private Button removeOrgButton;

    private ProjectVersion project;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addOrganization(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrganizationBOE.fxml"));
            Parent root = fxmlLoader.load();

            Stage addOrgStage = new Stage();
            addOrgStage.setTitle("Estimation Suite - Estimator - Estimate Project");
            addOrgStage.setScene(new Scene(root));

            addOrgStage.show();
            addOrgStage.setResizable(true);
            addOrgStage.sizeToScene();

            Stage stage = (Stage) addOrgButton.getScene().getWindow();
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstimateProject.fxml"));
            Parent root = fxmlLoader.load();

            EstimateProjectController controller = fxmlLoader.getController();

            controller.setProjectVersion(project);

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

    public void serProjectVersion(ProjectVersion project) {
        this.project = project;
    }
}
