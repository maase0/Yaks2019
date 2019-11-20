package application;

public class SOW {

	private String reference;
	private String sowContent;
	private String version;
	private String id;
		
	
    public SOW() {
    	reference = null;
    	sowContent = null;
    	version = null;
    }

    public SOW(String id, String reference, String sowContent, String version) {
    	this.id = id;
        this.reference = reference;
        this.sowContent = sowContent;
        this.version = version;
    }
		
    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return id;
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
    	return reference + ", v" + version + ", " + "\n" + sowContent;
    }

}
