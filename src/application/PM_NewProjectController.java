package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import javafx.stage.Stage;
import javafx.util.Callback;

public class PM_NewProjectController implements Initializable{
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
	@FXML
    private Button addCLINButton;
    @FXML
	private ListView<SDRL> SDRLListView;
    @FXML
    private Button addSDRLButton;
    @FXML
	private ListView<SOW> SOWListView;
    @FXML
    private Button addSOWButton;

    public static ObservableList<CLIN> clinObservableList;
    public static ObservableList<SDRL> sdrlObservableList;
    public static ObservableList<SOW> sowObservableList;

    public PM_NewProjectController()  {

    	clinObservableList = FXCollections.observableArrayList();
    	sdrlObservableList = FXCollections.observableArrayList();
    	sowObservableList = FXCollections.observableArrayList();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CLINListView.setItems(clinObservableList);
        CLINListView.setCellFactory(clinListView -> new CLIN_Controller());
        SDRLListView.setItems(sdrlObservableList);
        SDRLListView.setCellFactory(sdrlListView -> new SDRL_Controller());
        SOWListView.setItems(sowObservableList);
        SOWListView.setCellFactory(sowListView -> new SOW_Controller());
	}


	public void addCLIN(ActionEvent event) {
		clinObservableList.add(new CLIN());
    	//clinObservableList.add(null);

    	for(CLIN c : clinObservableList) {
    		System.out.println(c);
    	}

	}
	public void addSDRL(ActionEvent event) {
		sdrlObservableList.add(new SDRL());

		for (SDRL s : sdrlObservableList) {
			System.out.println(s);
		}
	}

	public void addSOW(ActionEvent event) {
		sowObservableList.add(new SOW());

		for (SOW s : sowObservableList) {
			System.out.println(s);
		}
	}
	
	public void saveCLIN(MouseEvent event) {
		clinObservableList.add(new CLIN());
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
            Parent root = FXMLLoader.load(getClass()
                    .getResource("PM_Projects.fxml"));

            Stage pmProjectsStage = new Stage();
            pmProjectsStage.setTitle("Estimation Suite - Product Manager - Projects");
            pmProjectsStage.setScene(new Scene(root));
            pmProjectsStage.show();

    		Stage stage = (Stage) discardButton.getScene().getWindow();
    		stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void submitForEstimation(ActionEvent event) {
		System.out.println("Submit Button");
	}

}
