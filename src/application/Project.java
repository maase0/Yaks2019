package application;

import java.util.ArrayList;

public class Project 
{
	private String name;
	private String id;
	private ArrayList<ProjectVersion> versions = new ArrayList();
	
    public Project() {
    	name = null;
    }
    
    public Project(String name, String id) {
    	this.name = name;
    	this.id = id;
    }
		
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getID() {
    	return id;
    }
    
    public void setID(String id) {
    	this.id = id;
    }
    
    public String toString() {
    	return name;
    }
    
    public void addVersion(ProjectVersion version) {
    	this.versions.add(version);
    }
    
    public void removeVersion(ProjectVersion version) {
    	this.versions.remove(version);
    }
}