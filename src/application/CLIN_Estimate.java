package application;

import java.util.ArrayList;

public class CLIN_Estimate
{
    private String version;
    private ArrayList<OrganizationBOE> organizationBOEs;

    public CLIN_Estimate () {
        version = null;
        organizationBOEs = null;
    }

    public CLIN_Estimate (String version, ArrayList<OrganizationBOE> organizationBOEs) {
        this.version = version;
        this.organizationBOEs = organizationBOEs;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public ArrayList<OrganizationBOE> getOrganizationBOEs() {
        return organizationBOEs;
    }
}