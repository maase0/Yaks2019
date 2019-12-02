package ProjectListCells;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import DB.DBUtil;
import application.Project;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

/**
 * List Cell to hold buttons for unsubmitted list
 */
public class UnsubmittedCell extends ProjectListCell {
	Button edit = new Button("Edit");
	Button remove = new Button("Remove");

	public UnsubmittedCell(BiConsumer<Project, String> editMethod, ObservableList<Project> unsubmittedObservableList) {
		super();

		hbox.getChildren().addAll(edit, remove);

		// Edits selected project
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(
						"EDIT ITEM: " + getItem() + "  VERSION: " + versionList.getSelectionModel().getSelectedItem());

				// Get item and Version Number from combo box
				editMethod.accept(getItem(), versionList.getSelectionModel().getSelectedItem());
				// editMethod.apply((getItem(),
				// versionList.getSelectionModel().getSelectedItem()));
			}
		});

		// Removes selected project
		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("REMOVE ITEM" + getItem());

				try {
					Project project = getItem();
					String versionNumber = versionList.getSelectionModel().getSelectedItem();
					int index = versionList.getSelectionModel().getSelectedIndex();

					String warning = "";
					if (versionList.getItems().size() == 1) {
						warning = "This project has only one version! Removing this version will remove the entire project. ";
					}

					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Remove Project");
					alert.setHeaderText("Do you want to remove the selected version (" + versionNumber
							+ ") or the entire project?");
					alert.setContentText(warning + "Choose your option.");

					ButtonType buttonTypeOne = new ButtonType("Remove Version " + versionNumber);
					ButtonType buttonTypeTwo = new ButtonType("Remove Entire Project");
					ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

					alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == buttonTypeOne) {
						ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject="
								+ project.getID() + " AND Version_Number=\"" + versionNumber + "\"");

						// Should only have one item, but go to latest just in case (maybe throw error?)
						rs.last();

						String versionID = rs.getString("idProjectVersion");

						if (versionList.getItems().size() == 1) {
							DBUtil.dbExecuteUpdate("CALL delete_project(" + project.getID() + ")");
							unsubmittedObservableList.remove(project);
						} else {
							DBUtil.dbExecuteUpdate("CALL delete_projectVersion(" + versionID + ")");
							versionList.getItems().remove(index);
						}

					} else if (result.get() == buttonTypeTwo) {
						DBUtil.dbExecuteUpdate("CALL delete_project(" + project.getID() + ")");
						unsubmittedObservableList.remove(project);
					} else {
						// ... user chose CANCEL or closed the dialog
					}

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

}