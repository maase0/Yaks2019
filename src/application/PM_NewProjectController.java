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
	// CLIN List fields

	// @FXML
	// private ListView<SDRL> SDRLListView;
	// @FXML
//    private Button addSDRLButton;

	// public static ObservableList<SDRL> sdrlObservableList;

	public PM_NewProjectController() {
		// CLINListView = new ListView<CLIN>();
		// CLINListView.setItems(clinObservableList);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clinObservableList = FXCollections.observableArrayList();

		/*clinObservableList.addListener(new ListChangeListener<CLIN>() {
			@Override
			public void onChanged(ListChangeListener.Change change) {
				System.out.println("Detected a change! ");
				while (change.next()) {
					System.out.println("Was added? " + change.wasAdded());
					System.out.println("Was removed? " + change.wasRemoved());
				}
			}
		});*/

		CLINListView.setItems(clinObservableList);
		// CLINListView.setCellFactory(clinListView -> new ListViewCell());
		/*
		 * SDRLListView.setItems(sdrlObservableList);
		 * SDRLListView.setCellFactory(sdrlListView -> new SDRLListViewCell());
		 */
	}

	public void addCLIN(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN.fxml"));

			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));

			CLIN_Controller controller = fxmlLoader.<CLIN_Controller>getController();
			controller.setList(clinObservableList);

			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void discardCLIN(ActionEvent event) {
		clinObservableList.remove(CLINListView.getSelectionModel().getSelectedItem());
	}

	public void editCLIN(MouseEvent event) {

	}

	public void saveCLIN(MouseEvent event) {
		clinObservableList.add(new CLIN());
	}

	public void addSDRL(ActionEvent event) {
		/*
		 * sdrlObservableList.add(new SDRL());
		 * 
		 * for (SDRL s : sdrlObservableList) { System.out.println(s); }
		 */
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

	/*
	 * public static ObservableList<CLIN> getObservableList() {
	 * if(clinObservableList == null) { clinObservableList =
	 * FXCollections.observableArrayList(); }
	 * 
	 * 
	 * return clinObservableList; }
	 */

}
