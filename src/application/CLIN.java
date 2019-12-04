package application;

import java.util.ArrayList;

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
	private ArrayList<OrganizationBOE> organizations;
	
    public CLIN() {
    	index = null;
    	projectType = null;
    	popStart = null;
    	popEnd = null;
    	clinContent = null;
    	version = null;
    	organizations = null;
    }
    
    public CLIN(String id, String index, String version,
                String projectType, String clinContent,
                String popStart, String popEnd) {
    	this.id = id;
    	this.index = index;
        this.version = version;
    	this.projectType = projectType;
    	this.clinContent = clinContent;
    	this.popStart = popStart;
    	this.popEnd = popEnd;
    	organizations = null;
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

    public ArrayList<OrganizationBOE> getOrganizations() {
    	return organizations;
    }
    
    public void setOrganizations(ArrayList<OrganizationBOE> list) {
    	this.organizations = list;
    }
    
    public void addOrganiztion(OrganizationBOE org) {
    	organizations.add(org);
    }
    public String toString() {
    	return "Index: " + index + " v" + version + "\n" + "Project Type: " + projectType + "\n"
                + "PoP: " + popStart + " to " + popEnd + "\n" + "Content:\n\t" + clinContent;
    }
}