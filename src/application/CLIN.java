package application;


public class CLIN
{
	private String name;
	private String projectType;
	private String popStart;
	private String popEnd;
	private String clinContent;
		
	
    public CLIN() {
    	name = null;
    	projectType = null;
    	popStart = null;
    	popEnd = null;
    	clinContent = null;
    
    }
		
    public void setName(String name) {
    	this.name = name;
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
    
    public String getName() {
    	return name;
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
    
    public String toString() {
    	return name + ", " + projectType + ", " + popStart + " - " + popEnd + "\n" + clinContent;
    }
}