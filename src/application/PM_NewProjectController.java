package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class PM_NewProjectController implements Initializable {

	// Project Information
	@FXML
	private TextField projectNameText;
	@FXML
	private TextField pmText;
	@FXML
	private TextField labelText;
	@FXML
	private TextField versionText;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;

	// CLIN Stuff
	@FXML
	private Button addCLINButton;
	@FXML
	private Button editCLINButton;
	@FXML
	private Button discardCLINButton;
	@FXML
	private ListView<CLIN> CLINListView;

	private ObservableList<CLIN> clinObservableList;

	@FXML
	Button discardButton;
	
	public PM_NewProjectController() {
	}

	@Override
	/**
	 * Initializes the Controller. Creates the CLIN List and sets
	 * the list view's list to that list.
	 */
	public void initialize(URL location, ResourceBundle resources) {
		clinObservableList = FXCollections.observableArrayList();

		CLINListView.setItems(clinObservableList);
		
	}
	
	/*
	 * Adds a new CLIN to the list.
	 */
	public void addCLIN(ActionEvent event) {
		Parent root = null;
		try {
			//Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass()
					.getResource("CLIN.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			//Grab the controller from the loader
			CLIN_Controller controller = fxmlLoader.<CLIN_Controller>getController();
			//Set the controller's list to allow message passing
			controller.setList(clinObservableList);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Removes the selected CLIN from the list view and observable list.
	 * @param event
	 */
	public void discardCLIN(ActionEvent event) {
		clinObservableList.remove(CLINListView.getSelectionModel().getSelectedItem());
	}

	/**
	 * Edit an existing CLIN that is selected in the list view.
	 * @param event
	 */
	public void editCLIN(ActionEvent event) {
		CLIN clin = CLINListView.getSelectionModel().getSelectedItem();
		
		
		try {
			//Normal FXML Stuff
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN.fxml"));
			Parent root1;
			root1 = (Parent) fxmlLoader.load();	
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			//Grab the controller from the loader and set it's list for message passing
			CLIN_Controller controller = fxmlLoader.<CLIN_Controller>getController();
			controller.setList(clinObservableList);

			//Set the controller's CLIN to the existing one
			controller.setCLIN(clin);
			//Set all of the controller's input fields
			controller.setInputFields();
			
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@FXML
	public void saveChanges(ActionEvent event) {
		System.out.println("Save Changes Button");
		System.out.println("Project Name: " + projectNameText.getText());
		System.out.println("Project Manager: " + pmText.getText());
		System.out.println("Project Label: " + labelText.getText());
		System.out.println("Version Number: " + versionText.getText());
		System.out.println("Start Date: " + startDate.getValue());
		System.out.println("End Date: " + endDate.getValue());
	}

	@FXML
	public void discardChanges(ActionEvent event) {
		System.out.println("Discard Changes Button");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("PM_Projects.fxml"));

			Stage pmProjectsStage = new Stage();
			pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
			pmProjectsStage.setScene(new Scene(root));
			pmProjectsStage.show();

			Stage stage = (Stage) discardButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void submitForEstimation(ActionEvent event) {
		System.out.println("Submit Button");
	}

}
