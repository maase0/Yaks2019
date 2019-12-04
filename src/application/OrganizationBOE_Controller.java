package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
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
	@FXML
	private TextField orgText;
	@FXML
	private TextField productText;
	@FXML
	private TextField versionText;

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
		org = null;
	}

	public void refresh() {

	}

	public void addWorkPack(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
			Parent root = fxmlLoader.load();

			WorkPackage_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setWorkPackageList(workPackageObservableList);

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

	public void editWorkPackage(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WorkPackage.fxml"));
			Parent root = fxmlLoader.load();

			WorkPackage_Controller controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			controller.setWorkPackageList(workPackageObservableList);
			controller.setWorkPackage(workPackageListView.getSelectionModel().getSelectedItem());
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

	public void removeWorkPackage(ActionEvent event) {

	}

	public void close() {
		closeCurrent();
	}

	public void save() {
		boolean flag = org == null;
		if (flag) {
			org = new OrganizationBOE();
		}
		org.setOrganization(orgText.getText());
		org.setProduct(productText.getText());
		org.setVersion(versionText.getText());
		org.setWorkPackages(new ArrayList<WorkPackage>(workPackageObservableList));
		if (flag) {
			organizationBOEObservableList.add(org);
		}
	}

	public void saveAndClose() {
		save();
		close();
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	public void setOrganizationList(ObservableList<OrganizationBOE> organizationBOEObservableList) {
		this.organizationBOEObservableList = organizationBOEObservableList;
	}

	public void setOrganiztion(OrganizationBOE org) {
		this.org = org;
		setAllFields();
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
}
