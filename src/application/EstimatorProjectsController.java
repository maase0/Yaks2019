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
import javafx.stage.Stage;
import javafx.util.Callback;

public class EstimatorProjectsController implements Initializable{

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
				return new EstimatorProjectsController.EstimatedCell();
			}
		});

		notEstimatedObservableList = FXCollections.observableArrayList();
		notEstimatedListView.setItems(notEstimatedObservableList);

		notEstimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new EstimatorProjectsController.notEstimatedCell();
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

	public void estimateProject(Project project, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstimateProject.fxml"));
			Parent root = fxmlLoader.load();

			EstimateProjectController controller = fxmlLoader.getController();

			ProjectVersion version = loadProjectVersion(project, versionNumber);

			if(version == null) {
				System.out.println("ERROR ERROR NULL ERROR ERROR");
			}

			controller.setCameFromEstimator(true);
			controller.setProjectVersion(version);

			Stage eEstimateProjectStage = new Stage();
			eEstimateProjectStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			eEstimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			eEstimateProjectStage.show();
			eEstimateProjectStage.setResizable(true);
			eEstimateProjectStage.sizeToScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProjectVersion loadProjectVersion(Project project, String versionNumber) {
		ProjectVersion version = new ProjectVersion();

		try {

			// Get all versions of the given project
			ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + project.getID()
					+ " AND Version_Number=" + versionNumber);

			// Should only have one item, but go to latest just in case (maybe throw error?)
			rs.last();

			// Set all of the project information
			version.setName(project.getName());
			version.setProjectManager(rs.getString("Project_Manager"));
			version.setVersionNumber(rs.getString("Version_Number"));
			version.setProposalNumber(rs.getString("Proposal_Number"));
			version.setProjectID(project.getID());

			// Save dates since some are null, causes errors parsing
			// TODO: should have null checks for all fields maybe
			String start = rs.getString("PoP_Start");
			String end = rs.getString("PoP_End");
			// Null check the date strings to prevent errors
			version.setPopStart(start == null ? null : LocalDate.parse(start));
			version.setPopEnd(end == null ? null : LocalDate.parse(end));

			// Get all the clins, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_clins(" + versionNumber + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addCLIN(new CLIN(rs.getString("idCLIN"),rs.getString("CLIN_Index"),
						rs.getString("Project_Type"), rs.getString("CLIN_Description"), rs.getString("Version_Number"),
						rs.getString("PoP_Start"), rs.getString("PoP_End")));
			}

			// Get all the SDRLs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sdrls(" + versionNumber + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSDRL(new SDRL(rs.getString("idSDRL"), rs.getString("SDRL_Title"), rs.getString("Version_Number")
						,rs.getString("SDRL_Description")));
			}

			// Get all the SOWs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sows(" + versionNumber + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSOW(new SOW(rs.getString("idSoW"), rs.getString("Reference_Number"), rs.getString("Version_Number")
						,rs.getString("SoW_Description")));
			}

			rs.close();
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	class ProjectListCell extends ListCell<Project> {
		HBox hbox = new HBox();
		Label label = new Label("(empty)");
		Pane pane = new Pane();
		ComboBox<String> versionList = new ComboBox<String>();

		public ProjectListCell() {
			super();
			hbox.getChildren().addAll(label, pane, versionList);
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

				try {
					ResultSet rs = DBUtil
							.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + getItem().getID());
					while (rs.next()) {
						versionList.getItems().add(rs.getString("Version_Number"));
					}
					rs.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			versionList.getSelectionModel().select(versionList.getItems().size() - 1);

		}
	}

	class notEstimatedCell extends ProjectListCell {
		Button estimateButton = new Button("Estimate");
		Button returnButton = new Button("Return");

		public notEstimatedCell() {
			super();

			hbox.getChildren().addAll(estimateButton, returnButton);

			estimateButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Estimate ITEM: " + getItem());

					estimateProject(getItem(), versionList.getSelectionModel().getSelectedItem());

					Stage stage = (Stage) estimateButton .getScene().getWindow();
					stage.close();
				}
			});

			returnButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("Return ITEM" + getItem());

					try {
						DBUtil.dbExecuteUpdate("UPDATE Project SET Submit_Date = NULL WHERE (idProject = '"
								+ getItem().getID() + "')");

						notEstimatedObservableList.remove(getItem());

					} catch (SQLException | ClassNotFoundException e ) {
						e.printStackTrace();
					}


				}
			});
		}
	}

	// estimated: view estimate
	class EstimatedCell extends ProjectListCell {
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
