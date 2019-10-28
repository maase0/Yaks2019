package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SDRL
{
	private String name;
	private String sdrlInfo;
		
	
    public SDRL() {
    	name = null;
    	sdrlInfo = null;
    
    }
		
    public void setName(String name) {
    	this.name = name;
    }
    
    
    public String getName() {
    	return name;
    }


    public String getSdrlInfo() {
    	return sdrlInfo;
    }
    
    public String toString() {
    	return name + "\n" + sdrlInfo;
    }
}