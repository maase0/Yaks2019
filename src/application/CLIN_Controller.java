package application;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class CLIN_Controller extends ListCell<CLIN> {

    @FXML
    private TextField nameField;
    @FXML
    private TextField projectField;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TextArea clinArea;
    @FXML
    private Button saveButton;
    @FXML
    private Button removeButton;
	@FXML 
	private Button clinSaveButton;
	@FXML 
	private Button clinRemoveButton;
	@FXML 
	private TextField clinName;
	@FXML 
	private TextField clinProjectType;
	@FXML 
	private TextArea clinTextArea;
	@FXML 
	private DatePicker clinPoPStart;
	@FXML 
	private DatePicker clinPoPEnd;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;
	private ObservableList<CLIN> clinObservableList;

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
    }
        
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
    	clinObservableList = PM_NewProjectController.clinObservableList;
		clinObservableList.remove(clinObservableList.size()-1);

    }
}