package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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
	
	public static String checkProjectForSubmission(ProjectVersion proj) {
		String errorMessage = "";
		
		String name = proj.getName();
		String pm = proj.getProjectManager();
		String propNum = proj.getProposalNumber();
		String versionNum = proj.getVersionNumber();
		LocalDate start = proj.getPopStart();
		LocalDate end = proj.getPopEnd();
		
		//Check all project information
		if(name == null || name.trim().isEmpty()) {
			errorMessage += "Project Error: Project Name must not be empty.\n";	
		}
		if(pm == null || pm.trim().equals("")) {
			errorMessage += "Project Error: Project Manager must not be empty.\n";
		}
		if(propNum == null || propNum.trim().isEmpty()) {
			errorMessage += "Project Error: Proposal Number must not be empty.\n";
		} else if(!propNum.matches("\\d+")) {
			errorMessage += "Project Error: Proposal Number must be a number. (ex: 11, 566)\n";
		}
		if(versionNum == null || versionNum.trim().isEmpty()) {
			errorMessage += "Project Error: Version Number must not be empty.\n";
		} else if(!versionNum.matches("\\d+(.\\d)*")) {
			errorMessage += "Project Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
		}
		if(start == null) {
			errorMessage += "Project Error: Period of Performance must have a starting value.\n";
		}
		if(end == null) {
			errorMessage += "Project Error: Period of Performance must have ending value.\n";
		}
		if(start != null && end != null) {
			if(start.compareTo(end) > 0) {
				errorMessage += "Project Error: Period of Performance Start must be before End.\n";
				errorMessage += "\tStart (" + start.toString() +") is after End (" + end.toString() + ")\n";

			}
		}
		
		ArrayList<CLIN> clins = proj.getCLINList();
		ArrayList<SDRL> sdrls = proj.getSDRLList();
		ArrayList<SOW> sows = proj.getSOWList();
		
		if(clins == null || clins.size() == 0) {
			errorMessage += "Project Error: Must have at least one Contract Line Item.\n";
		}
		if(sdrls == null || sdrls.size() == 0) {
			errorMessage += "Project Error: Must have at least one Subcontract Design Requirement List.\n";
		}
		if(sows == null || sows.size() == 0) {
			errorMessage += "Project Error: Must have at least one Statement of Work Reference.\n";
		}
		
		
		//Get errors for each CLIN
		for(CLIN c : clins) {
			String index = c.getIndex();
			String type = c.getProjectType();
			String details = c.getClinContent();
			String version = c.getVersion();
			LocalDate clinstart = c.getPopStart() == null ? null : LocalDate.parse(c.getPopStart());
			LocalDate clinend = c.getPopEnd() == null ? null : LocalDate.parse(c.getPopEnd());
			
			if(index == null || index.trim().isEmpty()) {
				errorMessage += "CLIN Error: Index cannot be empty.\n";
			} else if(!index.matches("\\d+")) {
				errorMessage += "CLIN Error: Index must be a number.\n";
			}
			if(type == null || type.trim().isEmpty()) {
				errorMessage += "CLIN Error: Project Type cannot be empty.\n";
			}
			if(details == null || details.trim().isEmpty()) {
				errorMessage += "CLIN Error: CLIN Details cannot be empty.\n";
			}
			if(clinstart == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a starting value.\n";
			}
			if(clinend == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a ending value.\n";
			}
			if(clinend != null && clinstart != null) {
				if(clinstart.compareTo(clinend) > 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance Start must be before End.\n";
					errorMessage += "\tStart (" + clinstart.toString() +") is after End (" + clinend.toString() + ")\n";
				}
				if(start.compareTo(clinstart) > 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance Start cannot be before Project Period of Performance Start.\n";
					errorMessage += "\tCLIN Start (" + clinstart.toString() +") is before Project Start(" + start.toString() + ")\n";

				}
				if(end.compareTo(clinend) < 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance End cannot be after project Period of Performance End.\n";
					errorMessage += "\tCLIN End (" + clinend.toString() +") is after Project End (" + end.toString() + ")\n";

				}
			}
			if(version == null || version.trim().isEmpty()) {
				errorMessage += "CLIN Error: CLIN Version cannot be empty.\n";
			} else if(!version.matches("\\d+(.\\d)*")) {
				errorMessage += "CLIN Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
			
		}
		
		for(SDRL s : sdrls) {
			String title = s.getName();
			String content = s.getSdrlInfo();
			String version = s.getVersion();
			
			if(title == null || title.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Name cannot be empty.\n";
			}
			if(content == null || content.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Details cannot be empty.\n";
			}
			if(version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if(!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}
		
		for(SOW s : sows) {
			String ref = s.getReference();	
			String content = s.getSowContent();
			String version = s.getVersion();
			
			if(ref == null || ref.trim().isEmpty()) {
				errorMessage += "SOW Error: SOW Ref cannot be empty\n";
			} else if(!ref.matches("\\d+")) {
				errorMessage += "SOW Error: SOW Reference Number must be a number.\n";
			}
			if(content == null | content.trim().isEmpty()) {
				errorMessage += "SOW Error: SOW Content cannot be empty.\n";
			}
			if(version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if(!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}
		
		if(errorMessage.equals("")) {
			errorMessage = null;
		}
		return errorMessage;
	}
}
