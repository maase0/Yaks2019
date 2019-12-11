package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class ProjectVersion
{
	private String projectVersionID;
	private String projectID;
	private String name;
	private String projectManager;
	private String versionNumber;
	private LocalDate popStart;
	private LocalDate popEnd;
	private String proposalNumber;
	private ArrayList<CLIN> clins = new ArrayList();
	private ArrayList<SDRL> sdrls = new ArrayList();
	private ArrayList<SOW> sows = new ArrayList();
	
	private ArrayList<CLIN> clinDelete = new ArrayList<CLIN>();
	private ArrayList<SOW> sowDelete = new ArrayList<SOW>();
	private ArrayList<SDRL> sdrlDelete = new ArrayList<SDRL>();
	
    public ProjectVersion() {
    }
	
    public String getName() {
    	return name;
    }

    public void setName(String name) {
    	this.name = name;
    }
    
    public String getProposalNumber() {
    	return proposalNumber;
    }
    
    public void setProposalNumber(String propNum) {
    	this.proposalNumber = propNum;
    }

    public void setProjectID(String id) {
    	this.projectID = id;
    }
    
    public String getProjectID() {
    	return projectID;
    }
    
	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public LocalDate getPopStart() {
		return popStart;
	}

	public void setPopStart(LocalDate popStart) {
		this.popStart = popStart;
	}

	public LocalDate getPopEnd() {
		return popEnd;
	}

	public void setPopEnd(LocalDate popEnd) {
		this.popEnd = popEnd;
	}
	
	public void addCLIN(CLIN clin) {
		this.clins.add(clin);
	}
	
	public void addSDRL(SDRL sdrl) {
		this.sdrls.add(sdrl);
	}
	
	public void addSOW(SOW sow) {
		this.sows.add(sow);
	}
	
	public void removeCLIN(CLIN clin) {
		this.clins.remove(clin);
	}
	
	public void removeSDRL(SDRL sdrl) {
		this.sdrls.remove(sdrl);
	}
	
	public void removeSOW(SOW sow) {
		this.sows.remove(sow);
	}
	
	public void setCLINList(ArrayList<CLIN> clins) {
		this.clins = clins;
	}
	
	public void setSOWList(ArrayList<SOW> sows) {
		this.sows = sows;
	}
	
	public void setSDRLList(ArrayList<SDRL> sdrls) {
		this.sdrls = sdrls;
	}
	
	public ArrayList<CLIN> getCLINList() {
    	return clins;
    }
	
	public ArrayList<SOW> getSOWList() {
		return sows;
	}
	public ArrayList<SDRL> getSDRLList() {
		return sdrls;
	}
	
	
	public void setCLINDeleteList(ArrayList<CLIN> clins) {
		this.clinDelete = clins;
	}
	
	public void setSOWDeleteList(ArrayList<SOW> sows) {
		this.sowDelete = sows;
	}
	
	public void setSDRLDeleteList(ArrayList<SDRL> sdrls) {
		this.sdrlDelete = sdrls;
	}
	
	public ArrayList<CLIN> getCLINDeleteList() {
    	return clinDelete;
    }
	
	public ArrayList<SOW> getSOWDeleteList() {
		return sowDelete;
	}
	public ArrayList<SDRL> getSDRLDeleteList() {
		return sdrlDelete;
	}
	
	
	public void setProjectVersionID(String version) {
		this.projectVersionID = version;
	}
	
	public String getProjectVersionID() {
		return projectVersionID;
	}
}