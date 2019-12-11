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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewOrganization_Controller implements Initializable, Refreshable {

    @FXML
    private Button closeButton;
    @FXML
    private Button viewWorkPackageButton;
    @FXML
    private Label orgText;
    @FXML
    private Label productText;
    @FXML
    private Label versionText;

    @FXML
    private ListView<WorkPackage> workPackageListView;
    private ObservableList<WorkPackage> workPackageObservableList;

    private Refreshable prevController;
    private ObservableList<OrganizationBOE> organizationBOEObservableList;

    private OrganizationBOE org;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        workPackageObservableList = FXCollections.observableArrayList();
        workPackageListView.setItems(workPackageObservableList);
    }

    public void refresh() {
    }

    public void viewWorkPackage(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewWorkPackage.fxml"));
            Parent root = fxmlLoader.load();

            ViewWP_Controller controller = fxmlLoader.getController();

            controller.setPreviousController(this);
            controller.setWorkPackageList(workPackageObservableList);
            controller.setWorkPackage(workPackageListView.getSelectionModel().getSelectedItem());

            Stage viewWP = new Stage();
            viewWP.setTitle("Estimation Suite - Project Manager - Project");
            viewWP.setScene(new Scene(root));

            viewWP.show();
            viewWP.setResizable(true);
            viewWP.sizeToScene();

            StageHandler.addStage(viewWP);
            StageHandler.hidePreviousStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(ActionEvent event) {
        closeCurrent();
    }

    private void setAllFields() {
        orgText.setText(org.getOrganization());
        productText.setText(org.getProduct());
        versionText.setText(org.getVersion());
        workPackageObservableList.addAll(org.getWorkPackages());
    }

    private void closeCurrent() {
        prevController.refresh();
        StageHandler.showPreviousStage();
        StageHandler.closeCurrentStage();
    }

    public void setOrganizationList(ObservableList<OrganizationBOE> organizationBOEObservableList) {
        this.organizationBOEObservableList = organizationBOEObservableList;
    }

    public void setPreviousController(Refreshable controller) {
        this.prevController = controller;
    }

    public void setOrganization(OrganizationBOE org) {
        this.org = org;
        setAllFields();
    }

}
