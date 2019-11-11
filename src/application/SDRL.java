package application;

public class SDRL
{
	private String name;
	private String sdrlInfo;
		
	
    public SDRL() {
    	name = null;
    	sdrlInfo = null;
    }

    public SDRL(String name, String sdrlInfo) {
        this.name = name;
        this.sdrlInfo = sdrlInfo;
    }
		
    public void setName(String name) {
    	this.name = name;
    }

    public void setSdrlInfo(String sdrlInfo) {
        this.sdrlInfo = sdrlInfo;
    }
    
    public String getName() {
    	return name;
    }

    public String getSdrlInfo() {
    	return sdrlInfo;
    }
    
    public String toString() {
    	return name + "\n" + sdrlInfo;
    }
}