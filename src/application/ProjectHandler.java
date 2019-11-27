package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import DB.DBUtil;
import javafx.collections.ObservableList;

public class ProjectHandler {

	public static void fillProjectList(String query, ObservableList<Project> list) {
		try {

			ResultSet rs = DBUtil.dbExecuteQuery(query);

			// Go through each project in the result set
			while (rs.next()) {
				String projName = rs.getString("Project_Name");
				String projID = rs.getString("idProject"); // ID is stored for later use to get project versions
				Project proj = new Project(projName, projID);
				// TODO: Get the versions from the database, put them in project

				ResultSet rs2 = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + proj.getID());
				while (rs2.next()) {
					proj.addVersion(rs2.getString("Version_Number"));
				}
				rs2.close();

				list.add(proj); // Add the project to the list
				System.out.println("\t" + projName);
			}

			rs.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception putting projects in list");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException putting projects into list");
			e.printStackTrace();
		}
	}
	
	public static ProjectVersion loadProjectVersion(Project project, String versionNumber) {
		ProjectVersion version = new ProjectVersion();

		try {

			// Get all versions of the given project
			ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion WHERE idProject=" + project.getID()
					+ " AND Version_Number=\"" + versionNumber + "\"");

			// Should only have one item, but go to latest just in case (maybe throw error?)
			rs.last();

			String versionID = rs.getString("idProjectVersion");

			// Set all of the project information
			version.setName(project.getName());
			version.setProjectManager(rs.getString("Project_Manager"));
			version.setVersionNumber(rs.getString("Version_Number"));
			version.setProposalNumber(rs.getString("Proposal_Number"));
			version.setProjectID(project.getID());
			version.setProjectVersionID(rs.getString("idProjectVersion"));
			// Save dates since some are null, causes errors parsing
			// TODO: should have null checks for all fields maybe
			String start = rs.getString("PoP_Start");
			String end = rs.getString("PoP_End");
			// Null check the date strings to prevent errors
			version.setPopStart(start == null ? null : LocalDate.parse(start));
			version.setPopEnd(end == null ? null : LocalDate.parse(end));

			// Get all the clins, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_clins(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));

				version.addCLIN(new CLIN(rs.getString("idCLIN"), rs.getString("CLIN_Index"),
						rs.getString("Version_Number"), rs.getString("Project_Type"), rs.getString("CLIN_Description"),
						rs.getString("PoP_Start"), rs.getString("PoP_End")));
			}

			// Get all the SDRLs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sdrls(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSDRL(new SDRL(rs.getString("idSDRL"), rs.getString("SDRL_Title"),
						rs.getString("Version_Number"), rs.getString("SDRL_Description")));
			}

			// Get all the SOWs, add them to the project
			rs = DBUtil.dbExecuteQuery("CALL select_sows(" + versionID + ")");
			while (rs.next()) {
				// System.out.println(rs.getString("CLIN_Index"));
				version.addSOW(new SOW(rs.getString("idSoW"), rs.getString("Reference_Number"),
						rs.getString("Version_Number"), rs.getString("SoW_Description")));
			}

			rs.close();
		} catch (SQLException e) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
}
