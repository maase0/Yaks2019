package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ProjectVersion
{
	private String projectVersionID;
	private String name;
	private String projectManager;
	private String versionNumber;
	private LocalDate popStart;
	private LocalDate popEnd;
	private String proposalNumber;
	private ArrayList<CLIN> clins = new ArrayList();
	private ArrayList<SDRL> sdrls = new ArrayList();
	private ArrayList<SOW> sows = new ArrayList();
	
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
	
	public ArrayList<CLIN> getCLINList() {
    	return clins;
    }
	
	public ArrayList<SOW> getSOWList() {
		return sows;
	}
	public ArrayList<SDRL> getSDRLList() {
		return sdrls;
	}
	
	public void setProjectVersionID(String version) {
		this.projectVersionID = version;
	}
	
	public String getProjectVersionID() {
		return projectVersionID;
	}
}