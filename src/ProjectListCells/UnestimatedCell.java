package ProjectListCells;

import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import DB.DBUtil;
import application.Project;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * List Cell to hold the buttons for an unestimated project
 */
public class UnestimatedCell extends ProjectListCell {
	Button estimateButton = new Button("Estimate");
	Button returnButton = new Button("Return");

	public UnestimatedCell(BiConsumer<Project, String> estimateMethod, ObservableList<Project> unsubmittedObservableList, ObservableList<Project> unestimatedObservableList) {
		super();

		hbox.getChildren().addAll(estimateButton, returnButton);

		estimateButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Estimate ITEM: " + getItem());

				/**
				 * TODO As of right now, estimating a project as a PM, when you click discard,
				 * it brings user to Estimator page, need to fix.
				 */

				estimateMethod.accept(getItem(), versionList.getSelectionModel().getSelectedItem());

				Stage stage = (Stage) estimateButton.getScene().getWindow();
				stage.close();

			}
		});

		returnButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Return ITEM" + getItem());

				try {
					DBUtil.dbExecuteUpdate("CALL return_project('" + getItem().getID() + "')");

					unsubmittedObservableList.add(getItem());
					unestimatedObservableList.remove(getItem());

				} catch (SQLException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}