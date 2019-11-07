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
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class CLIN_Controller extends ListCell<CLIN> implements Initializable {

	//CLIN PopUp Menu
		@FXML private Button clinSaveButton;
		@FXML private Button clinClose;
		@FXML private Button clinSaveAndClose;
		@FXML private TextField clinName;
		@FXML private TextField clinProjectType;
		@FXML private TextArea clinTextArea;
		@FXML private DatePicker clinPoPStart;
		@FXML private DatePicker clinPoPEnd;
		
		
		CLIN clin;
		
		private ObservableList<CLIN> clinObservableList;

    @FXML
    private GridPane gridPane;

  
    
    /**
     * Saves the CLIN being edited. Creates a new CLIN object
     * if the CLIN has not been saved yet, otherwise updates
     * existing CLIN.
     * 
     * @param event
     */
	public void saveCLIN() {
		
		//Get all the data from the form
		String name = clinName.getText();
		String projectType = clinProjectType.getText();
		String text = clinTextArea.getText();
		String start = clinPoPStart.getValue().toString();
		String end = clinPoPEnd.getValue().toString();
		
		if(clin == null) {		
			//Create a new object if not yet saved
			clin = new CLIN(name, projectType, text, start, end);
			clinObservableList.add(clin);
		} else {
			//Update CLIN with new information
			clin.setName(name);
			clin.setProjectType(projectType);
			clin.setClinContent(text);
			clin.setPopStart(start);
			clin.setPopEnd(end);
			clinObservableList.set(clinObservableList.indexOf(clin), clin);
			//   https://coderanch.com/t/666722/java/Notify-ObservableList-Listeners-Change-Elements
		}
		
		//System.out.println(clin);
	}
	
	public void saveAndClose(ActionEvent event) {
		saveCLIN();
		close();
	}
	
    public void close() {
    	Stage stage = (Stage) clinClose.getScene().getWindow();
        stage.close();
    }
   
    public void setList(ObservableList<CLIN> clinObservableList) {
		this.clinObservableList = clinObservableList; 
	}
    
    public void setCLIN(CLIN clin) {
    	this.clin = clin;
    }
    
    public void setInputFields() {
    	clinName.setText(clin.getName());
    	clinProjectType.setText(clin.getProjectType());
		clinTextArea.setText(clin.getClinContent());
		clinPoPStart.setValue(LocalDate.parse(clin.getPopStart()));
		clinPoPEnd.setValue(LocalDate.parse(clin.getPopEnd()));
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 @Override
	    protected void updateItem(CLIN clin, boolean empty) {
	        super.updateItem(clin, empty);

	        if(empty || clin == null) {

	            setText(null);
	            setGraphic(null);
	        } else {
	            if (mLLoader == null) {
	                mLLoader = new FXMLLoader(getClass()
	                		.getResource("CLINListCell.fxml"));
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
	    }*/
	
	
}