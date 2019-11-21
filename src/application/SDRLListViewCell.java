package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.IOException;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class SDRLListViewCell extends ListCell<SDRL> {

    @FXML
    private TextField nameField;
    @FXML
    private Button addAttachment;
    @FXML
    private MenuButton attachments;
    @FXML
    private TextArea sdrlArea;
    @FXML
    private Button saveButton;
    private Button removeButton;
  

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;
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
                		.getResource("SDRLListCell.fxml"));
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

    }
}