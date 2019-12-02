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

public class OrganizationBOE_Controller implements Initializable, Refreshable {

    @FXML
    private Button closeButton;
    @FXML
    private Button addWorkPackButton;
    @FXML
    private Button editWorkPackButton;
    @FXML
    private Button removeWorkPackButton;

    private Refreshable prevController;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void refresh() {
    	
    }
    
    public void addWorkPack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
            Parent root = fxmlLoader.load();

            WorkPackage_Controller controller = fxmlLoader.getController();
            controller.setPreviousController(this);
            
            
            Stage addWPStage = new Stage();
            addWPStage.setTitle("Estimation Suite - Estimator - Estimate Project");
            addWPStage.setScene(new Scene(root));

            addWPStage.show();
            addWPStage.setResizable(true);
            addWPStage.sizeToScene();

            StageHandler.addStage(addWPStage);
			StageHandler.hidePreviousStage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editOrganization(ActionEvent event) {

    }

    public void removeOrganization(ActionEvent event) {

    }

    public void close(ActionEvent event) {
       closeCurrent();
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
