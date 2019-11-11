package application;

public class SOW {

	private String reference;
	private String sowContent;
		
	
    public SOW() {
    	reference = null;
    	sowContent = null;
    }

    public SOW(String reference, String sowContent) {
        this.reference = reference;
        this.sowContent = sowContent;
    }
		
    public void setReference(String reference) {
    	this.reference = reference;
    }

    public void setSowContent(String sowContent) {
    	this.sowContent = sowContent;
    }
    
    public String getReference() {
    	return reference;
    }
    
    public String getSowContent() {
    	return sowContent;
    }
    
    public String toString() {
    	return reference + ", " + "\n" + sowContent;
    }

}
