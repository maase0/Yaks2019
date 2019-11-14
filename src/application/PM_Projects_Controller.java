package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class PM_Projects_Controller implements Initializable {

	@FXML
	private Button newProjectBtn;
	@FXML
	private Button logoutButton;
	@FXML
	private Button discardButton;
	@FXML
	private TabPane projectTabPane;
	@FXML
	private Tab submittedTab;
	@FXML
	private Tab unsubmittedTab;
	@FXML
	private Text editBtn;
	@FXML
	private Text discardBtn;
	@FXML
	private Text editBtn2;
	@FXML
	private Text discardBtn2;

	// Project list fields
	Project proj;
	@FXML
	private ListView<Project> unsubmittedListView;
	private ObservableList<Project> unsubmittedObservableList;

	@FXML
	private ListView<Project> estimatedListView;
	private ObservableList<Project> estimatedObservableList;

	@FXML
	private ListView<Project> unestimatedListView;
	private ObservableList<Project> unestimatedObservableList;

	public void initialize(URL location, ResourceBundle resources) {
		unsubmittedObservableList = FXCollections.observableArrayList();
		unsubmittedListView.setItems(unsubmittedObservableList);

		// https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
		unsubmittedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnsubmittedCell();
			}
		});

		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);

		estimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new EstimatedCell();
			}
		});

		unestimatedObservableList = FXCollections.observableArrayList();
		unestimatedListView.setItems(unestimatedObservableList);

		unestimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnestimatedCell();
			}
		});

		System.out.println("\nUnsubmitted Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NULL", unsubmittedObservableList);

		System.out.println("\nUnestimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				unestimatedObservableList);

		System.out.println("\nEstimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
				estimatedObservableList);

			System.out.println("\nEstimated Project Names");
			fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
					estimatedObservableList);
	}

	/**
	 * Fills the given list with projects retrieved with the given query
	 *
	 * @param query
	 * @param list
	 */
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

	/**
	 * Sets the Project observable list to allow the editor to add to the list view
	 *
	 * @param clinObservableList
	 */
	/*
	 * public void setList(ObservableList<Project> projObservableList) {
	 * this.projObservableList = projObservableList; }
	 */

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

	public void addNewProject(ActionEvent event) {
		try {
			// Opens New Project page
			Parent root = FXMLLoader.load(getClass().getResource("PM_NewProject.fxml"));

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - New Project");
			pmNewProjectStage.setScene(new Scene(root));
			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			// Closes PM Page
			Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editProject(Project project) {
		try {

			// System.out.println("You are now editing project version id: " +
			// projectVersionID);

			ProjectVersion version = new ProjectVersion();

			ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + project.getID());
			String versionID = "";
			// while(rs.next()) {
			// versionID = rs.getString("idProjectVersion");
			// }

			rs.last();
			versionID = rs.getString("idProjectVersion");

			version.setName(rs.getString("Project_Name"));
			version.setProjectManager(rs.getString("Project_Manager"));
			version.setVersionNumber(rs.getString("Version_Number"));
			version.setProposalNumber("Proposal_Number");
			// version.setPopStart();
			// TODO: get proposal numbers and PoPs
			// version.setP

			rs = DBUtil.dbExecuteQuery("CALL select_clins(" + versionID + ")");

			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addCLIN(new CLIN(rs.getString("CLIN_Index"), rs.getString("Project_Type"),
						rs.getString("CLIN_Description")));
			}

			rs = DBUtil.dbExecuteQuery("CALL select_sdrls(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSDRL(new SDRL(rs.getString("SDRL_Title"), rs.getString("SDRL_Description")));
			}

			rs = DBUtil.dbExecuteQuery("CALL select_sows(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSOW(new SOW(rs.getString("Reference_Number"), rs.getString("SoW_Description")));
			}

			rs.close();

			// Opens New Project page
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_NewProject.fxml"));
			Parent root = fxmlLoader.load();

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - Edit Project");
			pmNewProjectStage.setScene(new Scene(root));

			PM_NewProjectController controller = fxmlLoader.<PM_NewProjectController>getController();

			controller.setProject(version);

			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			// Closes PM Page
			Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			stage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void discard(ActionEvent event) { //remove the project from list
	 * projObservableList.remove(projListView.getSelectionModel().getSelectedItem())
	 * ; }
	 */

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

	// https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
	class UnsubmittedCell extends ProjectListCell {
		Button edit = new Button("Edit");
		Button remove = new Button("Remove");

		public UnsubmittedCell() {
			super();

			hbox.getChildren().addAll(edit, remove);

			edit.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("EDIT ITEM: " + getItem());

					editProject(getItem());
				}
			});

			remove.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("REMOVE ITEM");
				}
			});
		}
	}

	class UnestimatedCell extends ProjectListCell {
		Button estimateButton = new Button("Estimated");
		Button returnButton = new Button("Return");

		public UnestimatedCell() {
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
	class EstimatedCell extends ProjectListCell {
		Button viewButton = new Button("View");

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

}
