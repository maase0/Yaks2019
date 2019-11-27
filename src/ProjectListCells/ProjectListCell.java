package ProjectListCells;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import DB.DBUtil;
import application.Project;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * Generic List cell to hold all of the things common to the other list cells
 */
public class ProjectListCell extends ListCell<Project> {
	// https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
	protected HBox hbox = new HBox();
	protected Label label = new Label("(empty)");
	protected Pane pane = new Pane();
	protected ComboBox<String> versionList = new ComboBox<String>();

	public ProjectListCell() {
		super();
		hbox.getChildren().addAll(label, pane, versionList);
		HBox.setHgrow(pane, Priority.ALWAYS); // pushes buttons to right side
		hbox.setSpacing(10); // keeps buttons from touching
	}

	/**
	 * Add a button to the HBox
	 * 
	 * @param b
	 */
	protected void addButton(Button b) {
		hbox.getChildren().add(b);
	}

	@Override
	/**
	 * Updates the list when something is added?
	 */
	protected void updateItem(Project item, boolean empty) {
		super.updateItem(item, empty);
		setText(null); // No text in label of super class
		if (empty) {
			setGraphic(null);
		} else {
			label.setText(item != null ? item.toString() : "<null>");
			setGraphic(hbox);

			versionList.getItems().addAll(this.getItem().getVersionStrings());

		}

		versionList.getSelectionModel().select(versionList.getItems().size() - 1);

	}
}




