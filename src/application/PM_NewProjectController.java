package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBUtil;
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



public class PM_NewProjectController implements Initializable{

	private ResultSet rs;
	@FXML
	private Button saveButton;
	@FXML
	private Button discardButton;
	@FXML
	private Button submitButton;
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


	//CLIN List fields
	@FXML
    private ListView<CLIN> CLINListView;
	@FXML private Button addCLINButton;
	@FXML private Button editCLINButton;
	@FXML private Button removeCLINButton;
	private ObservableList<CLIN> clinObservableList;
	
	//SDRL List fields
	@FXML
	private ListView<SDRL> SDRLListView;
	@FXML private Button addSDRLButton;
	@FXML private Button editSDRLButton;
	@FXML private Button removeSDRLButton;
    private ObservableList<SDRL> sdrlObservableList;
    
    //SOW List fields
    @FXML
	private ListView<SOW> SOWListView;
    @FXML private Button addSOWButton;
	@FXML private Button editSOWButton;
	@FXML private Button removeSOWButton;
    private ObservableList<SOW> sowObservableList;
    
    
    

    public PM_NewProjectController()  {

    }

	public void initialize(URL location, ResourceBundle resources) {
		//Create a new obesrvable list for the CLINS, gives it to the list view
		clinObservableList = FXCollections.observableArrayList();
		CLINListView.setItems(clinObservableList);
		
		sdrlObservableList = FXCollections.observableArrayList();
		SDRLListView.setItems(sdrlObservableList);
		
    	sowObservableList = FXCollections.observableArrayList();
    	SOWListView.setItems(sowObservableList);
	}

	/**
	 * Adds a new clin to the list. Creates a popup menu to start editing a new CLIN
	 * @param event
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
		//get the clin to be edited
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

			stage.show(); //Show the popup editor

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void addSDRL() {
		
	}
	
	public static void editSDRL() {
		
	}
	
	public static void removeSDRL() {
		
	}
	
	public static void addSOW() {
		
	}
	
	public static void editSOW() {
		
	}
	
	public static void removeSOW() {
		
	}
	
	
	
	
	

	@FXML
	/**
	 * Saves all of the information of the newly created project
	 * @param event
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void saveChanges(ActionEvent event) throws SQLException, ClassNotFoundException {
    	//String name = "Reno";
		System.out.println("Save Changes Button");
		/**System.out.println("Project Name: " + projectNameText.getText());
		System.out.println("Project Manager: " + pmText.getText());
		System.out.println("Project Label: " + labelText.getText());
		System.out.println("Version Number: " + versionText.getText());
		System.out.println("Start Date: " + startDate.getValue());
		System.out.println("End Date: " + endDate.getValue());*/
		DBUtil.dbExecuteUpdate("INSERT INTO Project (Name) VALUES (' " + projectNameText.getText() + "')"); //THIS WORK YAY
		//DBUtil.dbExecuteUpdate("INSERT INTO Project (Name) VALUES ('Testing')");
	}

	@FXML
	/**
	 * Discards the project without saving it do the database
	 * @param event
	 */
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
	/**
	 * Submits the project for estimation
	 * @param event
	 */
	public void submitForEstimation(ActionEvent event) {
		System.out.println("Submit Button");
	}

}
