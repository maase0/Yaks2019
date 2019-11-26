package application;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Johannes on 23.05.16.
 *
 */

public class SDRLController extends ListCell<SDRL> implements Initializable {

    @FXML private Button sdrlSaveButton;
    @FXML private Button sdrlClose;
    @FXML private Button sdrlSaveAndClose;
    @FXML private TextField sdrlName;
    @FXML private TextArea sdrlInfo;
    @FXML private MenuButton attachments;
    @FXML private TextField sdrlVersion;

    SDRL sdrl;
    private ObservableList<SDRL> sdrlObservableList;

    public void saveSDRL() {

        String name = sdrlName.getText();
        String info = sdrlInfo.getText();
        String version = sdrlVersion.getText();

        if(sdrl == null) {
            sdrl = new SDRL(null, name, version, info);
            sdrlObservableList.add(sdrl);
        } else {
            sdrl.setName(name);
            sdrl.setVersion(version);
            sdrl.setSdrlInfo(info);
            sdrlObservableList.set(sdrlObservableList.indexOf(sdrl), sdrl);
        }
    }

    public void SaveAndClose(ActionEvent event) {
        saveSDRL();
        close();
    }

    public void close() {
        Stage stage = (Stage) sdrlClose.getScene().getWindow();
        stage.close();
    }

    public void setList(ObservableList<SDRL> sdrlObservableList) {
        this.sdrlObservableList = sdrlObservableList;
    }

    public void setSDRL(SDRL sdrl) {
        this.sdrl = sdrl;
    }

    public void setInputFields() {
        if(sdrl != null) {
            sdrlName.setText((sdrl.getName()));
            sdrlInfo.setText((sdrl.getSdrlInfo()));
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
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
        
                    