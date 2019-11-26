package application;

public class SDRL
{
	private String name;
	private String sdrlInfo;
	private String version;
	private String id;
	
    public SDRL() {
    	name = null;
    	sdrlInfo = null;
    	version = null;
    }

    public SDRL(String id, String name, String version, String sdrlInfo) {
        this.id = id;
    	this.name = name;
        this.version = version;
        this.sdrlInfo = sdrlInfo;
    }
		
    public void setID(String id) {
    	this.id = id;
    }
    
    public String getID() {
    	return id;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public void setSdrlInfo(String sdrlInfo) {
        this.sdrlInfo = sdrlInfo;
    }

    public void setVersion(String version) {
        this.version = version;
    }
    
    public String getName() {
    	return name;
    }

    public String getSdrlInfo() {
    	return sdrlInfo;
    }

    public String getVersion() {
        return version;
    }
    
    public String toString() {
    	return "Name: " + name + " v" + version + "\nInformation:\n\t" + sdrlInfo;
    }
}