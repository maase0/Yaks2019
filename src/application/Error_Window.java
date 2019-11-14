package application;
	
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class Error_Window extends Application{
	
	@FXML
	private Button exitBtn;

	@FXML
	private Label errorTxt;
	

	
	public void start(Stage primaryStage) {
		try {
				Parent root = FXMLLoader.load(getClass()
						.getResource("Error_Window.fxml"));
	            primaryStage.setTitle("ERROR");
				primaryStage.setScene(new Scene(root));
				primaryStage.show();
			
			}
		catch(Exception e) {
			e.printStackTrace();
			}
	}


	
	public void Exit(ActionEvent event) {
		Stage stage = (Stage) exitBtn.getScene().getWindow();
		stage.close();

	}
	
	public static void main(String[] args) {
		launch(args);
	}




}