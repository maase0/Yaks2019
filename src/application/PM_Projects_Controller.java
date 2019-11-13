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

		//https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
		unsubmittedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
            @Override
            public ListCell<Project> call(ListView<Project> param) {
                return new XCell();
            }
        });

		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);

		estimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
            @Override
            public ListCell<Project> call(ListView<Project> param) {
                return new XCell();
            }
        });

		unestimatedObservableList = FXCollections.observableArrayList();
		unestimatedListView.setItems(unestimatedObservableList);

		unestimatedListView.setCellFactory(new Callback<ListView<Project>, ListCell<Project>>() {
            @Override
            public ListCell<Project> call(ListView<Project> param) {
                return new XCell();
            }
        });

		System.out.println("\nUnsubmitted Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NULL",
				unsubmittedObservableList);

		System.out.println("\nUnestimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NULL",
				unestimatedObservableList);

		System.out.println("\nEstimated Project Names");
		fillProjectList("SELECT * FROM Project WHERE Submit_Date IS NOT NULL AND Estimated_Date IS NOT NULL",
				estimatedObservableList);

	}

	private void fillProjectList(String query, ObservableList<Project> list) {
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(query);

			while (rs.next()) {
				String projName = rs.getString("Project_Name");
				Project proj = new Project(projName);
				// TODO: Get the versions from the database, put them in project

				list.add(proj);
				System.out.println("\t" + projName);
			}
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

	public void editProject(MouseEvent event) {
		try {

			System.out.println("You are now editing project version id: " + projectVersionID);

			ProjectVersion proj = new ProjectVersion();



			ResultSet rs = DBUtil
					.dbExecuteQuery("CALL select_clins(" + projectVersionID +")");


			while(rs.next()) {
				System.out.println(rs.getString("CLIN_Index"));
				proj.addCLIN(new CLIN(rs.getString("CLIN_Index"), rs.getString("Project_Type"), rs.getString("CLIN_Description")));
			}

			// Opens New Project page
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PM_NewProject.fxml"));
			Parent root = fxmlLoader.load();

			Stage pmNewProjectStage = new Stage();
			pmNewProjectStage.setTitle("Estimation Suite - Product Manager - Edit Project");
			pmNewProjectStage.setScene(new Scene(root));

			PM_NewProjectController controller = fxmlLoader.<PM_NewProjectController>getController();

			controller.setProject(proj);

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



	//https://stackoverflow.com/questions/15661500/javafx-listview-item-with-an-image-button
	static class XCell extends ListCell<Project> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button edit = new Button("Edit");
        Button remove = new Button("Remove");
        Project lastItem;

        public XCell() {
            super();
            hbox.getChildren().addAll(label, pane, edit, remove);
            HBox.setHgrow(pane, Priority.ALWAYS);
            edit.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("EDIT ITEM");
                }
            });

            remove.setOnAction(new EventHandler<ActionEvent>() {
            	@Override
            	public void handle(ActionEvent event) {
            		System.out.println("REMOVE ITEM");
            	}
			});
        }

        @Override
        protected void updateItem(Project item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item!=null ? item.toString() : "<null>");
                setGraphic(hbox);
            }
        }
    }


}
