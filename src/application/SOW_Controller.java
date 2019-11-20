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

public class SOW_Controller extends ListCell<SOW> implements Initializable {

    @FXML private Button sowSaveButton;
    @FXML private Button sowClose;
    @FXML private Button sowSaveAndClose;
    @FXML private TextField sowReference;
    @FXML private TextArea sowContent;
    @FXML private TextField sowVersion;

    SOW sow;
    private ObservableList<SOW> sowObservableList;

    public void saveSOW() {

        String ref = sowReference.getText();
        String content = sowContent.getText();
        String version = sowVersion.getText();

        if(sow == null) {
            sow = new SOW(ref, content, version);
            sowObservableList.add(sow);
        } else {
            sow.setReference(ref);
            sow.setSowContent(content);
            sowObservableList.set(sowObservableList.indexOf(sow), sow);
        }
    }

    public void SaveAndClose(ActionEvent event) {
        saveSOW();
        close();
    }

    public void close() {
        Stage stage = (Stage) sowClose.getScene().getWindow();
        stage.close();
    }

    public void setList(ObservableList<SOW> sowObservableList) {
        this.sowObservableList = sowObservableList;
    }

    public void setSOW(SOW sow) {
        this.sow = sow;
    }

    public void setInputFields() {
        if(sow != null) {
            sowReference.setText(sow.getReference());
            sowContent.setText(sow.getSowContent());
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
    }
}

