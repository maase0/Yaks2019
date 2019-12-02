package application;

import java.util.ArrayList;

public class CLIN_Estimate
{
    private ArrayList<OrganizationBOE> organizationBOEs;

    public CLIN_Estimate () {
        organizationBOEs = null;
    }

    public CLIN_Estimate (ArrayList<OrganizationBOE> organizationBOEs) {
        this.organizationBOEs = organizationBOEs;
    }

    public ArrayList<OrganizationBOE> getOrganizationBOEs() {
        return organizationBOEs;
    }
}