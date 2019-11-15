package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Estimator_Projects_Controller implements Initializable{

	@FXML
	private Button logoutButton;
	
	@FXML
	private Tab estimatedTab;
	
	@FXML
	private Tab notestimatedTab;

	Project proj;
	@FXML
	private ListView<Project> notEstimatedListView;
	private ObservableList<Project> notEstimatedObservableList;

	@FXML
	private ListView<Project> estimatedListView;
	private ObservableList<Project> estimatedObservableList;

	public void initialize(URL location, ResourceBundle resources) {
		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);

		estimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new Estimator_Projects_Controller.EstimatedCell();
			}
		});

		notEstimatedObservableList = FXCollections.observableArrayList();
		notEstimatedListView.setItems(notEstimatedObservableList);

		notEstimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new Estimator_Projects_Controller.notEstimatedCell();
			}
		});

		System.out.println("\nNot Estimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				notEstimatedObservableList);

		System.out.println("\nEstimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
				estimatedObservableList);
	}

	private void fillProjectList(String query, ObservableList<Project> list) {
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(query);

			while (rs.next()) {
				String projName = rs.getString("Project_Name");
				String projID = rs.getString("idProject");
				Project proj = new Project(projName, projID);
				// TODO: Get the versions from the database, put them in project

				list.add(proj);
				System.out.println("\t" + projName);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception putting projects in list");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException putting projects into list");
			e.printStackTrace();
		}
	}

	class ProjectListCell extends ListCell<Project> {
		HBox hbox = new HBox();
		Label label = new Label("(empty)");
		Pane pane = new Pane();

		public ProjectListCell() {
			super();
			hbox.getChildren().addAll(label, pane);
			HBox.setHgrow(pane, Priority.ALWAYS);
			hbox.setSpacing(10);
		}

		protected void addButton(Button b) {
			hbox.getChildren().add(b);
		}

		@Override
		protected void updateItem(Project item, boolean empty) {
			super.updateItem(item, empty);
			setText(null); // No text in label of super class
			if (empty) {
				setGraphic(null);
			} else {
				label.setText(item != null ? item.toString() : "<null>");
				setGraphic(hbox);
			}
		}
	}

	class notEstimatedCell extends Estimator_Projects_Controller.ProjectListCell {
		Button estimateButton = new Button("Estimate");
		Button returnButton = new Button("Return");

		public notEstimatedCell() {
			super();

			hbox.getChildren().addAll(estimateButton, returnButton);

			estimateButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Estimate ITEM: " + getItem());

				}
			});

			returnButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Return ITEM" + getItem());
				}
			});
		}
	}

	// estimated: view estimate
	class EstimatedCell extends Estimator_Projects_Controller.ProjectListCell {
		Button viewButton = new Button("View Project Estimate");

		public EstimatedCell() {
			super();

			hbox.getChildren().addAll(viewButton);

			viewButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("VIEW ITEM: " + getItem());
				}
			});
		}
	}

	public void logout(ActionEvent event) {
		try {
			// Opens Login page
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

			Stage loginStage = new Stage();
			loginStage.setTitle("Estimation Suite - Login Page");
			loginStage.setScene(new Scene(root));
			loginStage.show();

			// Closes PM Page
			Stage stage = (Stage) logoutButton.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
