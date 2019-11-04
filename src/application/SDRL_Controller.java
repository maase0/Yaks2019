package application;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class SDRL_Controller extends ListCell<SDRL> {

    @FXML
    private TextField nameField;
    @FXML
    private Button addButton;
    @FXML
    private MenuButton attachments;
    @FXML
    private TextArea sdrlArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button removeButton;
    @FXML
    private GridPane gridPane;
    private FXMLLoader mLLoader;
    @FXML
    private MenuItem fileOpen;
    
	private ObservableList<SDRL> sdrl;

    @Override
    protected void updateItem(SDRL sdrl, boolean empty) {
        super.updateItem(sdrl, empty);

        if(empty || sdrl == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass()
                		.getResource("SDRL.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
           // label1.setText(String.valueOf(student.getStudentId()));
           // label2.setText(student.getName());

            setText(null);
            setGraphic(gridPane);
        }
    }
        
        public void removeSDRL(ActionEvent event) {
        	sdrl = PM_NewProjectController.sdrlObservableList;
    		sdrl.remove(sdrl.size()-1);
    }
        
        @FXML 
        public void addAttachment(ActionEvent event) {
        	FileChooser fileChooser = new FileChooser();
        	File selectedFile = fileChooser.showOpenDialog(null);
        	 
        	if (selectedFile != null) {
        	 
        	    System.out.println("File selected: " + selectedFile.getName());
        	}
        	else {
        	    System.out.println("File selection cancelled.");
            }
        }
}
        
                    