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

			// Get all the clins
			rs = DBUtil.dbExecuteQuery("SELECT * FROM CLIN WHERE idProjectVersion = " + versionID);
			// rs = DBUtil.dbExecuteQuery("CALL select_clins(" + versionID + ")");
			while (rs.next()) {
				// get the clin versions
				ResultSet clinRS = DBUtil.dbExecuteQuery("CALL select_clins(" + rs.getString("idCLIN") + ")");

				if (clinRS.last()) {

					version.addCLIN(new CLIN(clinRS.getString("idCLIN"), clinRS.getString("idCLINVersion"),
							clinRS.getString("CLIN_Index"), clinRS.getString("Version_Number"),
							clinRS.getString("Project_Type"), clinRS.getString("CLIN_Description"),
							clinRS.getString("PoP_Start"), clinRS.getString("PoP_End")));
				}
				System.out.println("a");
				clinRS.close();
			}
			System.out.println("b");


			// Get all the SDRLs, add them to the project
			// rs = DBUtil.dbExecuteQuery("CALL select_sdrls(" + versionID + ")");
			rs = DBUtil.dbExecuteQuery("SELECT * FROM SDRL WHERE idProjectVersion = " + versionID);

			while (rs.next()) {
				System.out.println("d");

				ResultSet rs2 = DBUtil.dbExecuteQuery("CALL select_sdrls(" + rs.getString("idSDRL") + ")");
				if (rs2.last()) {
					version.addSDRL(new SDRL(rs2.getString("idSDRL"), rs2.getString("idSDRLVersion"),
							rs2.getString("SDRL_Title"), rs2.getString("Version_Number"),
							rs2.getString("SDRL_Description")));
				}
				rs2.close();
			}

			// Get all the SOWs, add them to the project
			// rs = DBUtil.dbExecuteQuery("CALL select_sows(" + versionID + ")");
			rs = DBUtil.dbExecuteQuery("SELECT * FROM SoW WHERE idProjectVersion = " + versionID);

			while (rs.next()) {
				ResultSet rs2 = DBUtil.dbExecuteQuery("CALL select_sows(" + rs.getString("idSoW") + ")");
				System.out.println("c");

				if (rs2.last()) {
					System.out.println("d");

					version.addSOW(new SOW(rs2.getString("idSoW"), rs2.getString("idSoWVersion"),
							rs2.getString("Reference_Number"), rs2.getString("Version_Number"),
							rs2.getString("SoW_Description")));
					System.out.println("e");

				}
				System.out.println("f");

				rs2.close();
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
