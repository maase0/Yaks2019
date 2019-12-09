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

public class SDRLController extends ListCell<SDRL> implements Initializable {

	@FXML private Label error1;
	@FXML private Label error2;
	@FXML private Label error3;
	
	@FXML
	private Button sdrlSaveButton;
	@FXML
	private Button sdrlClose;
	@FXML
	private Button sdrlSaveAndClose;
	@FXML
	private TextField sdrlName;
	@FXML
	private TextArea sdrlInfo;
	@FXML
	private MenuButton attachments;
	@FXML
	private TextField sdrlVersion;

	SDRL sdrl;
	private ObservableList<SDRL> sdrlObservableList;

	public void saveSDRL() {

		String name = sdrlName.getText();
		String info = sdrlInfo.getText();
		String version = sdrlVersion.getText();

		if (sdrl == null) {
			sdrl = new SDRL(null, null, name, version, info);
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
		if (sdrl != null) {
			sdrlName.setText((sdrl.getName()));
			sdrlInfo.setText((sdrl.getSdrlInfo()));
			sdrlVersion.setText(sdrl.getVersion());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		sdrlVersion.setText("1");
		
		error1.setVisible(false);
		error2.setVisible(false);
		error3.setVisible(false);
	}

	@FXML
	public void addAttachment(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {

			System.out.println("File selected: " + selectedFile.getName());
		} else {
			System.out.println("File selection cancelled.");
		}
	}
}
