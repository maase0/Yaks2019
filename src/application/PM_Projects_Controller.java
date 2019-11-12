package application;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DB.DBUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PM_Projects_Controller implements Initializable{

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
	
	//Project list fields
    Project proj;
    @FXML
	private ListView<Project> unsubmittedListView;
    private ObservableList<Project> unsubmittedObservableList;
    
    @FXML
    private ListView<Project> estimatedListView;
    private ObservableList estimatedObservableList;
    
    @FXML
    private ListView<Project> unestimatedListView;
    private ObservableList<Project> unestimatedObservableList;

	
	public void initialize(URL location, ResourceBundle resources) {
		unsubmittedObservableList = FXCollections.observableArrayList();
		unsubmittedListView.setItems(unsubmittedObservableList);
		
		estimatedObservableList = FXCollections.observableArrayList();
		estimatedListView.setItems(estimatedObservableList);
		
		unestimatedObservableList = FXCollections.observableArrayList();
		unestimatedListView.setItems(unestimatedObservableList);
		
		String name;
		
		//Select Query on Database
		try {
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM Project");
		
		//Actions to perform with data (this can be whatever, generally going to be filling in text fields)
	    System.out.println("\nProject Name");
	    
	    //Potentially figure out why it continuously runs after execution
			while(rs.next()) {
				name = rs.getString("Project_Name");
				String projName = rs.getString("Project_Name");
				Project proj = new Project(projName);
				//TODO: Get the versions from the database, put them in project
				
				unsubmittedObservableList.add(proj);
				
			    System.out.println("\t" + projName);
				/*if(proj == null) {
					//Create a new object if not yet saved
					proj = new Project(name);
					projObservableList.add(proj);
				} else {
					//Update Project with new information
					proj.setName(name);
					projObservableList.set(projObservableList.indexOf(proj), proj);  //probably not the "right" way to update the list
					//   https://coderanch.com/t/666722/java/Notify-ObservableList-Listeners-Change-Elements
				}*/
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
    /**
     * Sets the Project observable list to allow the editor to add
     * to the list view
     * @param clinObservableList
     */
   /* public void setList(ObservableList<Project> projObservableList) {
		this.projObservableList = projObservableList;
	}*/
	
	public void logout(ActionEvent event) {
		try {
            // Opens Login page
            Parent root = FXMLLoader.load(getClass()
            		.getResource("Login.fxml"));
            
            Stage loginStage = new Stage();
            loginStage.setTitle("Estimation Suite - Login Page");
            loginStage.setScene(new Scene(root));
            loginStage.show();
            
            //Closes PM Page
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addNewProject(ActionEvent event) {
		try {
            // Opens New Project page
           // Parent root = FXMLLoader.load(getClass()
           // 		.getResource("PM_NewProject.fxml"));
            Parent root = FXMLLoader.load(getClass()
            		.getResource("PM_NewProject.fxml"));
                                  
            Stage pmNewProjectStage = new Stage();
            pmNewProjectStage.setTitle("Estimation Suite - Product Manager - New Project");
            pmNewProjectStage	.setScene(new Scene(root));
            pmNewProjectStage.show();
            pmNewProjectStage.setResizable(true);
            pmNewProjectStage.sizeToScene();
            
            //Closes PM Page
            Stage stage = (Stage) newProjectBtn.getScene().getWindow();
            stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void editProject(MouseEvent event) {
		try {
            // Opens New Project page
           // Parent root = FXMLLoader.load(getClass()
           // 		.getResource("PM_NewProject.fxml"));
            Parent root = FXMLLoader.load(getClass()
            		.getResource("PM_EditProject.fxml"));
                                  
            Stage pmNewProjectStage = new Stage();
            pmNewProjectStage.setTitle("Estimation Suite - Product Manager - Edit Project");
            pmNewProjectStage	.setScene(new Scene(root));
            pmNewProjectStage.show();
            pmNewProjectStage.setResizable(true);
            pmNewProjectStage.sizeToScene();
            
            //Closes PM Page
            Stage stage = (Stage) newProjectBtn.getScene().getWindow();
            stage.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	public void discard(ActionEvent event) {
	//remove the project from list
		projObservableList.remove(projListView.getSelectionModel().getSelectedItem());
	}*/
}
