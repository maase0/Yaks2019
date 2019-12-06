package application;

public class SOW {

	private String reference;
	private String sowContent;
	private String version;
	private String id;
	private String versionID;	
	
    public SOW() {
    	reference = null;
    	sowContent = null;
    	version = null;
    	versionID = null;
    }

    public SOW(String id, String versionID, String reference, String version, String sowContent) {
    	this.id = id;
        this.reference = reference;
        this.version = version;
        this.sowContent = sowContent;
        this.versionID = versionID;
    }
		
    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return id;
    }
    
    public void setVersionID(String versionID) {
    	this.versionID = versionID;
    }
    
    public String getVersionID() {
    	return versionID;
    }
    
    public void setReference(String reference) {
    	this.reference = reference;
    }

    public void setSowContent(String sowContent) {
    	this.sowContent = sowContent;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getReference() {
    	return reference;
    }
    
    public String getSowContent() {
    	return sowContent;
    }

    public String getVersion() {
        return version;
    }
    
    public String toString() {
    	return "Reference: " + reference + " v" + version + "\nContent:\n\t" + sowContent;
    }

}
