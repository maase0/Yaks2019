package application;


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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class CLIN_Controller implements Initializable {

	//CLIN PopUp Menu
		@FXML private Button clinSaveButton;
		@FXML private Button clinRemoveButton;
		@FXML private TextField clinName;
		@FXML private TextField clinProjectType;
		@FXML private TextArea clinTextArea;
		@FXML private DatePicker clinPoPStart;
		@FXML private DatePicker clinPoPEnd;
  
		
		private ObservableList<CLIN> clinObservableList;

    @FXML
    private GridPane gridPane;

    
	public void saveCLIN(ActionEvent event) {
		
		String name = clinName.getText();
		String projectType = clinProjectType.getText();
		String text = clinTextArea.getText();
		String start = clinPoPStart.getValue().toString();
		String end = clinPoPEnd.getValue().toString();
		
		CLIN clin = new CLIN(name, projectType, text, start, end);
		
		System.out.println(clin);
		
		clinObservableList.add(clin);
		
		/*
		for(CLIN c : PM_NewProjectController.getObservableList()) {
			System.out.println(c);
		}*/
		
	}
	
	public void setList(ObservableList<CLIN> clinObservableList) {
		this.clinObservableList = clinObservableList; 
	}
	
    public void removeCLIN(ActionEvent event) {
        	

    }
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}