package application;

import java.util.ArrayList;

public class Project 
{
	private String name;
	private ArrayList<ProjectVersion> versions = new ArrayList();
	
    public Project() {
    }
		
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
    	return name;
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