package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CLINVersion
{
	private String index;
	private String projectType;
	private String popStart;
	private String popEnd;
	private String clinContent;
	private int version;
	
    public CLINVersion() {
    	index = null;
    	projectType = null;
    	popStart = null;
    	popEnd = null;
    	clinContent = null;
    	version = 0;
    }
    
    public CLINVersion(String index, String projectType, 
    		String clinContent, int version) {
    	this.index = index;
    	this.projectType = projectType;
    	this.clinContent = clinContent;
    	this.popStart = popStart;
    	this.popEnd = popEnd;
    	this.version = version;
    }
		
    public void setIndex(String index) {
    	this.index = index;
    }
    
    public void setProjectType(String projectType) {
    	this.projectType = projectType;
    }
    
    public void setPopStart(String popStart) {
    	this.popStart = popStart;
    }
    
    public void setPopEnd(String popEnd) {
    	this.popEnd = popEnd;
    }
    
    public void setClinContent(String clinContent) {
    	this.clinContent = clinContent;
    }
    
    public void setVersion(int version) {
    	this.version = version;
    }
    
    public String getIndex() {
    	return index;
    }
    
    public String getProjectType() {
    	return projectType;
    }
    
    public String getPopStart() {
    	return popStart;
    }
    
    public String getPopEnd() {
    	return popEnd;
    }
    
    public String getClinContent() {
    	return clinContent;
    }
    
    public int getVersion() {
    	return version;
    }
    
    public String toString() {
    	return index + ", " + version + ", " + projectType + ", " + popStart + " - " + popEnd + "\n" + clinContent;
    }
}