package BOE.view;

import java.io.IOException;

import BOE.boe_tool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class projectOverviewController {

	// Reference to the main application.
    private boe_tool mainApp;
    @FXML
    AnchorPane switchPane;
    
    @FXML
    private void initialize() {
    }
    
    @FXML
    private void closeBtnControl()
    {
    	mainApp.getPrimaryStage().close();
    }

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(boe_tool mainApp) {
	    this.mainApp = mainApp;
	
	    // Set View to default summary view
	  //  setSummary();
	}
	
	public void setSummary() {
		// Load PM layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        System.out.print("Summary Window : ");
        System.out.println(loader);
        loader.setLocation(boe_tool.class.getResource("view\\pmMenu.fxml"));
        AnchorPane rootLayout = null;
        try {
        	rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        switchPane.getChildren().setAll(rootLayout);
	}
    

   public void setSPYPrograms()
   {
	   FXMLLoader loader = new FXMLLoader();
	   System.out.print("SPY Window : ");
       System.out.println(loader);
       loader.setLocation(boe_tool.class.getResource("view\\productSummary.fxml"));
       AnchorPane rootLayout = null;
       try {
       	rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       switchPane.getChildren().setAll(rootLayout);
   }
   
   public void setDMPrograms() {
	   FXMLLoader loader = new FXMLLoader();
	   System.out.print("DM Window : ");
       System.out.println(loader);
       loader.setLocation(boe_tool.class.getResource("view\\productSummary.fxml"));
       AnchorPane rootLayout = null;
       try {
       	rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       switchPane.getChildren().setAll(rootLayout);
   }
   
   public void setSDRLs() {
	   FXMLLoader loader = new FXMLLoader();
	   System.out.print("SDRL Window : ");
       System.out.println(loader);
       loader.setLocation(boe_tool.class.getResource("view\\SDRLControl.fxml"));
       AnchorPane rootLayout = null;
       try {
       	rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       switchPane.getChildren().setAll(rootLayout);
   }
   public void setSOWs() {
	   FXMLLoader loader = new FXMLLoader();
	   System.out.print("SOW Window : ");
       System.out.println(loader);
       loader.setLocation(boe_tool.class.getResource("view\\SOWRefControl.fxml"));
       AnchorPane rootLayout = null;
       try {
       	rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       switchPane.getChildren().setAll(rootLayout);
   }
}