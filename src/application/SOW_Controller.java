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

public class SOW_Controller extends ListCell<SOW> {

	@FXML
    private TextField referenceField;
	
	@FXML
    private TextArea sowArea;
	
	@FXML
    private Button saveButton;
    private Button removeButton;
    
    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;
	private ObservableList<SOW> sow;
	
	@Override
    protected void updateItem(SOW sow, boolean empty) {
        super.updateItem(sow, empty);

        if(empty || sow == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass()
                		.getResource("SOWRef.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            setText(null);
            setGraphic(gridPane);
        }
    }
	
	public void removeSOW(ActionEvent event) {
    	sow = PM_NewProjectController.sowObservableList;
		sow.remove(sow.size()-1);

    }

}
