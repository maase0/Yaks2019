package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiFunction;

import DB.DBUtil;
import ProjectListCells.EstimatedCell;
import ProjectListCells.UnestimatedCell;
import ProjectListCells.UnsubmittedCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PM_ProjectsController implements Initializable, Refreshable {

	private Refreshable prevController;

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
	@FXML
	private Button refreshButton;

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

	@FXML
	private ListView<Project> approvedListView;
	private ObservableList<Project> approvedObservableList;

	@FXML
	private ListView<Project> deniedListView;
	private ObservableList<Project> deniedObservableList;

	private ArrayList<CLIN> clinDelete = new ArrayList<CLIN>();
	private ArrayList<SOW> sowDelete = new ArrayList<SOW>();
	private ArrayList<SDRL> sdrlDelete = new ArrayList<SDRL>();

	/**
	 * Initialize the page, create the lists, fill all the lists with data from the
	 * database
	 */
	public void initialize(URL location, ResourceBundle resources) {
		refresh();
	}

	public void refresh() {
		// Initialize the observable list, give it to the list view
		unsubmittedObservableList = FXCollections.observableArrayList();
		unsubmittedListView.setItems(unsubmittedObservableList);

		// Give the list view the custom HBox so that it has per-element buttons
		unsubmittedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {

			// BiFunction<Project, String, Boolean> edit = (a, b)-> editProject(a, b);
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnsubmittedCell((a, b) -> editProject(a, b), unsubmittedObservableList);
			}
		}); // https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button

		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);

		estimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {

			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new EstimatedCell((a, b) -> viewProjectEstimate(a, b));
			}
		});

		unestimatedObservableList = FXCollections.observableArrayList();
		unestimatedListView.setItems(unestimatedObservableList);

		unestimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnestimatedCell((a, b) -> estimateProject(a, b), unsubmittedObservableList,
						unestimatedObservableList);
			}
		});

		approvedObservableList = FXCollections.observableArrayList();
		approvedListView.setItems(approvedObservableList);

		approvedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {

		  @Override
		  public ListCell<Project> call(ListView<Project> param) {
			  return new EstimatedCell((a, b) -> viewApproved(a, b));
		  }
		});

		deniedObservableList = FXCollections.observableArrayList();
		deniedListView.setItems(deniedObservableList);

		deniedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {

		 @Override public ListCell<Project> call(ListView<Project> param) {
		 	return new EstimatedCell((a, b) -> viewDenied(a, b));
		 }
		});

		// Fill each list with relevant projects from database
		System.out.println("\nUnsubmitted Project Names");
		ProjectHandler.fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NULL", unsubmittedObservableList);

		System.out.println("\nUnestimated Project Names");
		ProjectHandler.fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				unestimatedObservableList);

		System.out.println("\nEstimated Project Names");
		ProjectHandler.fillProjectList(
				"SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL AND"
						+ " Approved_Date IS NULL AND Denied_Date IS NULL",
				estimatedObservableList);

		System.out.println("\nApproved Project Names");
		ProjectHandler.fillProjectList(
				"SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL AND"
						+ " Approved_Date IS NOT NULL",
				approvedObservableList);

		System.out.println("\nDenied Project Names");
		ProjectHandler.fillProjectList(
				"SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL AND"
						+ " Denied_Date IS NOT NULL",
				deniedObservableList);
	}

	/**
	 * Fills the given list with projects retrieved with the given query
	 *
	 * @param query The query to retrieve projects from the database
	 * @param list  The list to fill
	 */

	/**
	 * Sets the Project observable list to allow the editor to add to the list view
	 *
	 */
	/*
	 * public void setList(ObservableList<Project> projObservableList) {
	 * this.projObservableList = projObservableList; }
	 */

	/**
	 * Switches back to the login page
	 * 
	 * @param event
	 */
	public void logout(ActionEvent event) {
		closeCurrent();
	}

	/**
	 * Switches to the project creation page
	 * 
	 * @param event
	 */
	public void addNewProject(ActionEvent event) {
		try {
			// Opens New Project page
			// Parent root = FXMLLoader.load(getClass().getResource("PM_NewProject.fxml"));

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_NewProject.fxml"));
			Parent root = fxmlLoader.load();

			PM_NewProjectController controller = fxmlLoader.getController();
			controller.setPreviousController(this);
			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - New Project");
			pmNewProjectStage.setScene(new Scene(root));
			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			StageHandler.addStage(pmNewProjectStage);
			StageHandler.hidePreviousStage();

			// Closes PM Page
			// Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			// stage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches to the project editor page, loads the projects information
	 * 
	 * @param project The project to edit
	 */
	public void editProject(Project project, String versionNumber) {
		try {

			// System.out.println("You are now editing project version id: " +
			// projectVersionID);

			// Create a new version to hold all the data to be edited

			// Opens Edit Project page
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_EditProject.fxml"));
			Parent root = fxmlLoader.load();

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - Edit Project");
			pmNewProjectStage.setScene(new Scene(root));

			PM_EditProjectController controller = fxmlLoader.getController();

			ProjectVersion version = ProjectHandler.loadProjectVersion(project, versionNumber);

			controller.setProject(version);
			controller.setPreviousController(this);

			pmNewProjectStage.show();
			pmNewProjectStage.setResizable(true);
			pmNewProjectStage.sizeToScene();

			StageHandler.addStage(pmNewProjectStage);
			StageHandler.hidePreviousStage();
			// Closes PM Page
			// Stage stage = (Stage) newProjectBtn.getScene().getWindow();
			// stage.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove the given project from the database and the list
	 * 
	 * @param project The project to remove
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void discardProject(Project project, String versionNumber) throws SQLException, ClassNotFoundException {

		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + project.getID()
				+ " AND Version_Number=\"" + versionNumber + "\"");

		// Should only have one item, but go to latest just in case (maybe throw error?)
		rs.last();

		String versionID = rs.getString("idProjectVersion");

		// DBUtil.dbExecuteUpdate("CALL delete_project(" + project.getID() + ")");
		// unsubmittedObservableList.remove(project);

		DBUtil.dbExecuteUpdate("CALL delete_projectVersion(" + versionID + ")");

	}

	public void estimateProject(Project project, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstimateProject.fxml"));
			Parent root = fxmlLoader.load();

			EstimateProjectController controller = fxmlLoader.getController();

			ProjectVersion version = ProjectHandler.loadProjectVersion(project, versionNumber);

			if (version == null) {
				System.out.println("ERROR ERROR NULL ERROR ERROR");
			}
			controller.setProjectVersion(version);
			controller.setPreviousController(this);

			Stage estimateProjectStage = new Stage();
			estimateProjectStage.setTitle("Estimation Suite - Estimator - Estimate Project");
			estimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			estimateProjectStage.show();
			estimateProjectStage.setResizable(true);
			estimateProjectStage.sizeToScene();

			StageHandler.addStage(estimateProjectStage);
			StageHandler.hidePreviousStage();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewProjectEstimate(Project proj, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_ViewProjectEstimate.fxml"));
			Parent root = fxmlLoader.load();

			// EstimateProjectController controller = fxmlLoader.getController();

			// controller.setCameFromEstimator(false);

			ProjectVersion version = ProjectHandler.loadProjectVersion(proj, versionNumber);

			/*
			 * if (version == null) { System.out.println("ERROR ERROR NULL ERROR ERROR"); }
			 */

			PM_VPE_Controller controller = fxmlLoader.getController();

			controller.setProject(version);

			Stage eEstimateProjectStage = new Stage();
			eEstimateProjectStage.setTitle("Estimation Suite - Project Manager - Estimate Project");
			eEstimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			eEstimateProjectStage.show();
			eEstimateProjectStage.setResizable(true);
			eEstimateProjectStage.sizeToScene();
			StageHandler.addStage(eEstimateProjectStage);
			StageHandler.hidePreviousStage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewApproved(Project proj, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_ViewProcessedProjectEstimate.fxml"));
			Parent root = fxmlLoader.load();

			ProjectVersion version = ProjectHandler.loadProjectVersion(proj, versionNumber);

			PM_VPPE_Controller controller = fxmlLoader.getController();

			controller.setAorD(true);
			controller.setProject(version);
			controller.setStatus("Approved");
			controller.setName(version.getName());

			Stage approvedStage = new Stage();
			approvedStage.setTitle("Estimation Suite - Project Manager - Approved Project");
			approvedStage.setScene(new Scene(root));

			approvedStage.show();
			approvedStage.setResizable(true);
			approvedStage.sizeToScene();

			StageHandler.addStage(approvedStage);
			StageHandler.hidePreviousStage();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void viewDenied(Project proj, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_ViewProcessedProjectEstimate.fxml"));
			Parent root = fxmlLoader.load();

			ProjectVersion version = ProjectHandler.loadProjectVersion(proj, versionNumber);

			PM_VPPE_Controller controller = fxmlLoader.getController();

			controller.setAorD(false);
			controller.setProject(version);
			controller.setStatus("Denied");
			controller.setName(version.getName());

			Stage deniedStage = new Stage();
			deniedStage.setTitle("Estimation Suite - Project Manager - Denied Project");
			deniedStage.setScene(new Scene(root));

			deniedStage.show();
			deniedStage.setResizable(true);
			deniedStage.sizeToScene();

			StageHandler.addStage(deniedStage);
			StageHandler.hidePreviousStage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPreviousController(Refreshable controller) {
		this.prevController = controller;
	}

	private void closeCurrent() {
		prevController.refresh();
		StageHandler.showPreviousStage();
		StageHandler.closeCurrentStage();
	}
}
