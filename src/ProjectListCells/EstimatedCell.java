package ProjectListCells;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import application.Project;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * List cell to hold buttons for the Estimated Project List
 */
public class EstimatedCell extends ProjectListCell {
	Button viewButton = new Button("View Project Estimate");

	public EstimatedCell(BiConsumer<Project, String> viewMethod) {
		super();

		hbox.getChildren().addAll(viewButton);

		viewButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("VIEW ITEM: " + getItem());

				viewMethod.accept(getItem(), versionList.getSelectionModel().getSelectedItem());

				Stage stage = (Stage) viewButton.getScene().getWindow();
				stage.close();
			}
		});
	}
}