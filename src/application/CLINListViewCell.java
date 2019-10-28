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

public class CLINListViewCell extends ListCell<CLIN> {

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
    private Button removeButton;
  

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;
	private ObservableList<CLIN> clin;

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
        
    public void removeCLIN(ActionEvent event) {
    	clin = PM_NewProjectController.clinObservableList;
		clin.remove(clin.size()-1);

    }
}