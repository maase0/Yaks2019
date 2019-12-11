package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCLIN_Estimate_Controller implements Initializable, Refreshable {

    private Refreshable prevController;
    @FXML
    private Button closeButton;
    @FXML
    private Button viewOrganizationButton;
    @FXML
    private Label clinName;
    @FXML
    private Label clinVersion;

    private CLIN clin;

    @FXML
    private ListView<OrganizationBOE> organizationBOEListView;
    private ObservableList<OrganizationBOE> organizationBOEObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        organizationBOEObservableList = FXCollections.observableArrayList();
        organizationBOEListView.setItems(organizationBOEObservableList);
    }

    public void refresh() {
        organizationBOEListView.refresh();
    }

    public void viewOrganization(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewOrganizationBOE.fxml"));
            Parent root = fxmlLoader.load();

            ViewOrganization_Controller controller = fxmlLoader.getController();

            controller.setPreviousController(this);
            controller.setOrganizationList(organizationBOEObservableList);
            controller.setOrganization(organizationBOEListView.getSelectionModel().getSelectedItem());

            Stage viewOrg = new Stage();
            viewOrg.setTitle("Estimation Suite - Project Manager - Project");
            viewOrg.setScene(new Scene(root));

            viewOrg.show();
            viewOrg.setResizable(true);
            viewOrg.sizeToScene();

            StageHandler.addStage(viewOrg);
            StageHandler.hidePreviousStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        closeCurrent();
    }

    public void setCLIN(CLIN clin) {
        this.clin = clin;
        clinName.setText(clin.getIndex());
        clinVersion.setText(clin.getVersion());
        organizationBOEObservableList.addAll(clin.getOrganizations());
    }

    public void setPreviousController(Refreshable controller) {
        this.prevController = controller;
    }

    private void closeCurrent() {
        prevController.refresh();
        StageHandler.showPreviousStage();
        StageHandler.closeCurrentStage();
    }
}
