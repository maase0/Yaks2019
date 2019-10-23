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

public class CLINController implements Initializable{
	//TODO: use CLINListViewCell instead?
	//FXML Interactables
	@FXML
	private Button saveButton;
	@FXML
	private Button discardButton;
	@FXML
	private TextField nameText;
	@FXML
	private TextField projectTypeText;
	@FXML
	private TextArea clinText;
	@FXML
	private DatePicker startDate;
	@FXML
	private DatePicker endDate;
	
	//CLIN object to store data
	private CLIN clin;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	

	@FXML
	public void saveChanges(ActionEvent event) {
		System.out.println("Test");
		System.out.println(this);
	}
	
	@FXML
	public void discardCLIN(ActionEvent event) {
	
	}
	
}
