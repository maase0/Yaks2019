package application;

import java.util.ArrayList;

public class OrganizationBOE
{
    private String organization;
    private String product;
    private String version;
    private ArrayList<WorkPackage> workPackages;

    public OrganizationBOE () {
        organization = null;
        product = null;
        version = null;
        workPackages = null;
    }

    public OrganizationBOE (String organization, String product,
                            String version, ArrayList<WorkPackage> workPackages) {
        this.organization = organization;
        this.product = product;
        this.version = version;
        this.workPackages = workPackages;
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
    
    public String toString() {
    	return organization +": " + product + ", v" + version;
    }
}