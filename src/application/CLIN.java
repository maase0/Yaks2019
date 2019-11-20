package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
// TODO: change this class to function in a similar manner to Project.java, as there is now a CLINVersion class
public class CLIN
{
	private String index;
	private String projectType;
	private String popStart;
	private String popEnd;
	private String clinContent;
	private String version;
	private String id;
	
    public CLIN() {
    	index = null;
    	projectType = null;
    	popStart = null;
    	popEnd = null;
    	clinContent = null;
    	version = null;
    
    }
    
    public CLIN(String id, String index, String projectType,
    		String clinContent, String version, String popStart,
            String popEnd) {
    	this.id = id;
    	this.index = index;
        this.version = version;
    	this.projectType = projectType;
    	this.clinContent = clinContent;
    	this.popStart = popStart;
    	this.popEnd = popEnd;
    }
		
    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return id;
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

    public void setVersion(String version) {
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

    public String getVersion() {
        return version;
    }
    
    public String toString() {
    	return index + ", v" + version + ", " + projectType + ", \n"
                + popStart + " to " + popEnd + "\n" + clinContent;
    }
}