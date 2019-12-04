package application;

import java.util.ArrayList;

public class OrganizationBOE {
	private String organization;
	private String product;
	private String version;
	private ArrayList<WorkPackage> workPackages;
	private String id;
	private String oldVersion;
	private ArrayList<WorkPackage> deletedWorkPackages;
	
	public OrganizationBOE() {
		organization = null;
		product = null;
		version = null;
		workPackages = new ArrayList<WorkPackage>();
		oldVersion = null;
		deletedWorkPackages = new ArrayList<WorkPackage>();
	}

	public OrganizationBOE(String organization, String product, String version, ArrayList<WorkPackage> workPackages) {
		this.organization = organization;
		this.product = product;
		this.version = version;
		this.oldVersion = version;
		this.workPackages = workPackages;
		deletedWorkPackages = new ArrayList<WorkPackage>();
	}
	
	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}
	
	public String getOldVersion() {
		return oldVersion;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOrganization() {
		return organization;
	}

	public String getProduct() {
		return product;
	}

	public String getVersion() {
		return version;
	}

	public ArrayList<WorkPackage> getWorkPackages() {
		return workPackages;
	}

	public void setWorkPackages(ArrayList<WorkPackage> list) {
		this.workPackages = list;
	}

	public ArrayList<WorkPackage> getDeletedWorkPackages() {
		return deletedWorkPackages;
	}
	
	public void setDeletedWorkPackages(ArrayList<WorkPackage> wp) {
		this.deletedWorkPackages = wp;
	}
	
	public void addWorkPackage(WorkPackage wp) {
		workPackages.add(wp);
	}
	
	public String toString() {
		return organization + ": " + product + ", v" + version;
	}
}