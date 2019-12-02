package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import DB.DBUtil;
import ProjectListCells.EstimatedCell;
import ProjectListCells.UnestimatedCell;
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

import ProjectListCells.*;

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
				return new EstimatedCell((a,b)->viewProjectEstimate(a,b));
			}
		});

		notEstimatedObservableList = FXCollections.observableArrayList();
		notEstimatedListView.setItems(notEstimatedObservableList);

		notEstimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
			@Override
			public ListCell<Project> call(ListView<Project> param) {
				return new UnestimatedCell((a,b)->estimateProject(a, b), notEstimatedObservableList);
			}
		});

		System.out.println("\nNot Estimated Project Names");
		ProjectHandler.fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				notEstimatedObservableList);

		System.out.println("\nEstimated Project Names");
		ProjectHandler.fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
				estimatedObservableList);
	}

	public void estimateProject(Project project, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EstimateProject.fxml"));
			Parent root = fxmlLoader.load();

			EstimateProjectController controller = fxmlLoader.getController();

			ProjectVersion version = ProjectHandler.loadProjectVersion(project, versionNumber);

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

	public void viewProjectEstimate(Project proj, String versionNumber) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Estimator_ViewProjectEstimate.fxml"));
			Parent root = fxmlLoader.load();


			//EstimateProjectController controller = fxmlLoader.getController();

			//controller.setCameFromEstimator(false);

			ProjectVersion version = ProjectHandler.loadProjectVersion(proj, versionNumber);

			/*if (version == null) {
				System.out.println("ERROR ERROR NULL ERROR ERROR");
			}*/

			//Estimator_VPE_Controller controller = fxmlLoader.getController();


			//controller.setProject(version);

			Stage eEstimateProjectStage = new Stage();
			eEstimateProjectStage.setTitle("Estimation Suite - Project Manager - Estimate Project");
			eEstimateProjectStage.setScene(new Scene(root));

			// EstimateProject_Controller controller = fxmlLoader.getController();

			eEstimateProjectStage.show();
			eEstimateProjectStage.setResizable(true);
			eEstimateProjectStage.sizeToScene();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void logout(ActionEvent event) {
		StageHandler.closeCurrentStage();
		StageHandler.showCurrentStage();
	}

}
