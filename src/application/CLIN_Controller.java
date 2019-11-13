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
 * Interface to create / edit CLINs. 
 */

public class CLIN_Controller extends ListCell<CLIN> implements Initializable {
		//fxml elements for the editor
		@FXML private Button clinSaveButton;
		@FXML private Button clinClose;
		@FXML private Button clinSaveAndClose;
		@FXML private TextField clinIndex;
		@FXML private TextField clinProjectType;
		@FXML private TextField clinVersion;
		@FXML private TextArea clinTextArea;
		@FXML private DatePicker clinPoPStart;
		@FXML private DatePicker clinPoPEnd;
		@FXML private GridPane gridPane;
	
		CLIN clin;
		private ObservableList<CLIN> clinObservableList;


    /**
     * Saves the CLIN being edited. Creates a new CLIN object
     * if the CLIN has not been saved yet, otherwise updates
     * existing CLIN.
     *
     * @param event
     */
	public void saveCLIN() {

		//Get all the data from the form
		String index = clinIndex.getText();
		String projectType = clinProjectType.getText();
		String text = clinTextArea.getText();
		//String start = clinPoPStart.getValue().toString();
		//String end = clinPoPEnd.getValue().toString();

		if(clin == null) {
			//Create a new object if not yet saved
			clin = new CLIN(index, projectType, text);
			clinObservableList.add(clin);
		} else {
			//Update CLIN with new information
			clin.setIndex(index);
			clin.setProjectType(projectType);
			clin.setClinContent(text);
			//clin.setPopStart(start);
			//clin.setPopEnd(end);
			clinObservableList.set(clinObservableList.indexOf(clin), clin);  //probably not the "right" way to update the list
			//   https://coderanch.com/t/666722/java/Notify-ObservableList-Listeners-Change-Elements
		}

		//System.out.println(clin);
	}

	/**
	 * Saves the CLIN and closes the editor
	 * @param event
	 */
	public void saveAndClose(ActionEvent event) {
		saveCLIN();
		close();
	}

	/** 
	 * Closes the editor without saving any unsaved changes
	 */
    public void close() {
    	//TODO: make a popup if there are unsaved changes
    	//      probably keep track of state with a boolean, use
    	//      fxml "on input method text changed" for each input field
    	Stage stage = (Stage) clinClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets the CLIN observable list to allow the editor to add
     * to the list view
     * @param clinObservableList
     */
    public void setList(ObservableList<CLIN> clinObservableList) {
		this.clinObservableList = clinObservableList;
	}

    /** 
     * Set the CLIN to allow for editing an existing CLIN
     * @param clin
     */
    public void setCLIN(CLIN clin) {
    	this.clin = clin;
    }

    /**
     * Fills in all of the input fields with the existing CLIN's data.
     * Only runs if CLIN is not null
     */
    public void setInputFields() {
    	if(clin != null) {
    		clinIndex.setText(clin.getIndex());
    		clinProjectType.setText(clin.getProjectType());
    		clinTextArea.setText(clin.getClinContent());
    		//clinPoPStart.setValue(LocalDate.parse(clin.getPopStart()));
			//clinPoPEnd.setValue(LocalDate.parse(clin.getPopEnd()));
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	
	//Might need this to make the clins display nicely in the list
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
