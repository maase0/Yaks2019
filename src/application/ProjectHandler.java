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

	public static void saveNewProject(ProjectVersion proj) throws ClassNotFoundException, SQLException {
		int vid = 0;
		int projectID;

		System.out.println("Save Changes Button");
		ResultSet rs = DBUtil.dbExecuteQuery("CALL insert_new_project('" + proj.getVersionNumber() + "', \""
				+ proj.getName() + "\", \"" + proj.getProjectManager() + "\", " + proj.getProposalNumber() + ", '"
				+ proj.getPopStart() + "', '" + proj.getPopEnd() + "')");

		rs.last();
		vid = rs.getInt("idProjectVersion");

		rs.close();

		rs = DBUtil.dbExecuteQuery("SELECT idProject FROM ProjectVersion WHERE idProjectVersion = " + vid);
		rs.last();
		projectID = rs.getInt("idProject");

		for (CLIN c : proj.getCLINList()) {
			DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \"" + c.getVersion()
					+ "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent() + "\", '" + c.getPopStart() + "', '"
					+ c.getPopEnd() + "')");
		}

		for (SDRL s : proj.getSDRLList()) {
			DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \"" + s.getVersion()
					+ "\", \"" + s.getSdrlInfo() + "\")");
		}

		for (SOW s : proj.getSOWList()) {
			DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \"" + s.getVersion()
					+ "\", \"" + s.getSowContent() + "\")");
		}

	}

	public static void saveProject(ProjectVersion proj, String oldVersion) throws ClassNotFoundException, SQLException {
		int vid = 0;

		ResultSet rs = DBUtil.dbExecuteQuery("CALL update_projectVersion(" + proj.getProjectVersionID() + ", \""
				+ proj.getVersionNumber() + "\", \"" + proj.getName() + "\", \"" + proj.getProjectManager() + "\", "
				+ proj.getProposalNumber() + ", '" + proj.getPopStart() + "', '" + proj.getPopEnd() + "')");

		rs.last();
		vid = rs.getInt("idProjectVersion");

		rs.close();

		if (proj.getVersionNumber().equals(oldVersion)) {

			// If the item has an id, then it was loaded from the database and already
			// exists
			// update existing items, insert new items.
			for (CLIN c : proj.getCLINList()) {
				if (c.getID() != null) {
					// `update_clin`(CLINVID int, CLINID int, clinIndex VARCHAR(45), versionNumber
					// VARCHAR(45),
					// projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd
					// DATE)
					DBUtil.dbExecuteUpdate("CALL update_clin(" + c.getVersionID() + ", " + c.getID() + ", \""
							+ c.getIndex() + "\" , \"" + c.getVersion() + "\", \"" + c.getProjectType() + "\", \""
							+ c.getClinContent() + "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
				} else {
					// insert_clin`(VID int, clinIndex VARCHAR(45), versionNumber VARCHAR(45),
					// projectType VARCHAR(45),
					// clinDescription VARCHAR(1000), popStart DATE, popEnd DATE)
					DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \"" + c.getVersion()
							+ "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent() + "\", '" + c.getPopStart()
							+ "', '" + c.getPopEnd() + "')");
				}
			}

			for (SDRL s : proj.getSDRLList()) {
				if (s.getID() != null) {
					// update_sdrl`(SDRLVID int, SDRLID int, sdrlTitle VARCHAR(45), versionNumber
					// VARCHAR(45), sdrlDescription VARCHAR(1000))
					DBUtil.dbExecuteUpdate("CALL update_sdrl(" + s.getVersionID() + ", " + s.getID() + ", \""
							+ s.getName() + "\", \"" + s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
				} else {
					DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \"" + s.getVersion()
							+ "\", \"" + s.getSdrlInfo() + "\")");
				}
			}

			for (SOW s : proj.getSOWList()) {
				if (s.getID() != null) {
					// update_sow`(SOWVID int, SOWID int, sowRef VARCHAR(45), versionNumber
					// VARCHAR(45), sowDescription VARCHAR(1000))
					DBUtil.dbExecuteUpdate("CALL update_sow(" + s.getVersionID() + ", " + s.getID() + ", "
							+ s.getReference() + ", \"" + s.getVersion() + "\", \"" + s.getSowContent() + "\")");
					System.out.println("AAAAA HELP");
				} else {
					DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \"" + s.getVersion()
							+ "\", \"" + s.getSowContent() + "\")");
				}
			}

			// Delete all the list items that were saved for deletion
			// delete only if version is the same, if version has changed
			// "deleted" items are just not copied over
			for (CLIN c : proj.getCLINDeleteList()) {
				DBUtil.dbExecuteUpdate("CALL delete_clin(" + c.getID() + ")");
			}

			for (SOW s : proj.getSOWDeleteList()) {
				DBUtil.dbExecuteUpdate("CALL delete_sow(" + s.getID() + ")");
			}

			for (SDRL s : proj.getSDRLDeleteList()) {
				DBUtil.dbExecuteUpdate("CALL delete_sdrl(" + s.getID() + ")");
			}
		}

		// This is if the version number changed.
		// Re-insert all the items with the new vid(version id)
		else {
			for (CLIN c : proj.getCLINList()) {
				if (c.getID() != null) {
					// clone_clin`(VID int, CLINVID int, clinIndex VARCHAR(45), versionNumber
					// VARCHAR(45),
					// projectType VARCHAR(45), clinDescription VARCHAR(1000), popStart DATE, popEnd
					// DATE)
					DBUtil.dbExecuteUpdate("CALL clone_clin(" + vid + ", " + c.getVersionID() + ", \"" + c.getIndex()
							+ "\" , \"" + c.getVersion() + "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent()
							+ "\", '" + c.getPopStart() + "', '" + c.getPopEnd() + "')");
				} else {
					DBUtil.dbExecuteUpdate("CALL insert_clin(" + vid + ", \"" + c.getIndex() + "\", \"" + c.getVersion()
							+ "\", \"" + c.getProjectType() + "\", \"" + c.getClinContent() + "\", '" + c.getPopStart()
							+ "', '" + c.getPopEnd() + "')");
				}
			}

			for (SDRL s : proj.getSDRLList()) {
				if (s.getID() != null) {
					// clone_sdrl`(VID int, SDRLVID int, sdrlTitle VARCHAR(45), versionNumber
					// VARCHAR(45), sdrlDescription VARCHAR(1000))
					DBUtil.dbExecuteUpdate("CALL clone_sdrl(" + vid + ", " + s.getVersionID() + ", \"" + s.getName()
							+ "\", \"" + s.getVersion() + "\", \"" + s.getSdrlInfo() + "\")");
				} else {
					DBUtil.dbExecuteUpdate("CALL insert_sdrl(" + vid + ", \"" + s.getName() + "\", \"" + s.getVersion()
							+ "\", \"" + s.getSdrlInfo() + "\")");
				}
			}

			for (SOW s : proj.getSOWList()) {
				if (s.getID() != null) {
					// clone_sow`(VID int, SOWVID int, sowRef VARCHAR(45), versionNumber
					// VARCHAR(45), sowDescription VARCHAR(1000))
					DBUtil.dbExecuteUpdate("CALL clone_sow(" + vid + ", " + s.getVersionID() + ", " + s.getReference()
							+ ", \"" + s.getVersion() + "\", \"" + s.getSowContent() + "\")");
				} else {
					DBUtil.dbExecuteUpdate("CALL insert_sow(" + vid + ", " + s.getReference() + ", \"" + s.getVersion()
							+ "\", \"" + s.getSowContent() + "\")");
				}
			}

		}
	}

	public static String checkProjectForSubmission(ProjectVersion proj) {
		String errorMessage = "";

		String name = proj.getName();
		String pm = proj.getProjectManager();
		String propNum = proj.getProposalNumber();
		String versionNum = proj.getVersionNumber();
		LocalDate start = proj.getPopStart();
		LocalDate end = proj.getPopEnd();

		// Check all project information
		if (name == null || name.trim().isEmpty()) {
			errorMessage += "Project Error: Project Name must not be empty.\n";
		}
		if (pm == null || pm.trim().equals("")) {
			errorMessage += "Project Error: Project Manager must not be empty.\n";
		}
		if (propNum == null || propNum.trim().isEmpty()) {
			errorMessage += "Project Error: Proposal Number must not be empty.\n";
		} else if (!propNum.matches("\\d+")) {
			errorMessage += "Project Error: Proposal Number must be a number. (ex: 11, 566)\n";
		}
		if (versionNum == null || versionNum.trim().isEmpty()) {
			errorMessage += "Project Error: Version Number must not be empty.\n";
		} else if (!versionNum.matches("\\d+(.\\d)*")) {
			errorMessage += "Project Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
		}
		if (start == null) {
			errorMessage += "Project Error: Period of Performance must have a starting value.\n";
		}
		if (end == null) {
			errorMessage += "Project Error: Period of Performance must have ending value.\n";
		}
		if (start != null && end != null) {
			if (start.compareTo(end) > 0) {
				errorMessage += "Project Error: Period of Performance Start must be before End.\n";
				errorMessage += "\tStart (" + start.toString() + ") is after End (" + end.toString() + ")\n";

			}
		}

		ArrayList<CLIN> clins = proj.getCLINList();
		ArrayList<SDRL> sdrls = proj.getSDRLList();
		ArrayList<SOW> sows = proj.getSOWList();

		if (clins == null || clins.size() == 0) {
			errorMessage += "Project Error: Must have at least one Contract Line Item.\n";
		}
		if (sdrls == null || sdrls.size() == 0) {
			errorMessage += "Project Error: Must have at least one Subcontract Design Requirement List.\n";
		}
		if (sows == null || sows.size() == 0) {
			errorMessage += "Project Error: Must have at least one Statement of Work Reference.\n";
		}

		// Get errors for each CLIN
		for (CLIN c : clins) {
			String index = c.getIndex();
			String type = c.getProjectType();
			String details = c.getClinContent();
			String version = c.getVersion();
			LocalDate clinstart = c.getPopStart() == null ? null : LocalDate.parse(c.getPopStart());
			LocalDate clinend = c.getPopEnd() == null ? null : LocalDate.parse(c.getPopEnd());

			if (index == null || index.trim().isEmpty()) {
				errorMessage += "CLIN Error: Index cannot be empty.\n";
			} else if (!index.matches("\\d+")) {
				errorMessage += "CLIN Error: Index must be a number.\n";
			}
			if (type == null || type.trim().isEmpty()) {
				errorMessage += "CLIN Error: Project Type cannot be empty.\n";
			}
			if (details == null || details.trim().isEmpty()) {
				errorMessage += "CLIN Error: CLIN Details cannot be empty.\n";
			}
			if (clinstart == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a starting value.\n";
			}
			if (clinend == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a ending value.\n";
			}
			if (clinend != null && clinstart != null) {
				if (clinstart.compareTo(clinend) > 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance Start must be before End.\n";
					errorMessage += "\tStart (" + clinstart.toString() + ") is after End (" + clinend.toString()
							+ ")\n";
				}
				if (start.compareTo(clinstart) > 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance Start cannot be before Project Period of Performance Start.\n";
					errorMessage += "\tCLIN Start (" + clinstart.toString() + ") is before Project Start("
							+ start.toString() + ")\n";

				}
				if (end.compareTo(clinend) < 0) {
					errorMessage += "CLIN Error: CLIN Period of Performance End cannot be after project Period of Performance End.\n";
					errorMessage += "\tCLIN End (" + clinend.toString() + ") is after Project End (" + end.toString()
							+ ")\n";

				}
			}
			if (version == null || version.trim().isEmpty()) {
				errorMessage += "CLIN Error: CLIN Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "CLIN Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}

		}

		for (SDRL s : sdrls) {
			String title = s.getName();
			String content = s.getSdrlInfo();
			String version = s.getVersion();

			if (title == null || title.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Name cannot be empty.\n";
			}
			if (content == null || content.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Details cannot be empty.\n";
			}
			if (version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}

		for (SOW s : sows) {
			String ref = s.getReference();
			String content = s.getSowContent();
			String version = s.getVersion();

			if (ref == null || ref.trim().isEmpty()) {
				errorMessage += "SOW Error: SOW Ref cannot be empty\n";
			} else if (!ref.matches("\\d+")) {
				errorMessage += "SOW Error: SOW Reference Number must be a number.\n";
			}
			if (content == null | content.trim().isEmpty()) {
				errorMessage += "SOW Error: SOW Content cannot be empty.\n";
			}
			if (version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}

		if (errorMessage.equals("")) {
			errorMessage = null;
		}
		return errorMessage;
	}

	public static String checkProjectForSaving(ProjectVersion proj) {
		String errorMessage = "";

		String name = proj.getName();
		String propNum = proj.getProposalNumber();
		String versionNum = proj.getVersionNumber();
		LocalDate start = proj.getPopStart();
		LocalDate end = proj.getPopEnd();

		// Check all project information
		if (name == null || name.trim().isEmpty()) {
			errorMessage += "Project Error: Project Name must not be empty.\n";
		}
		if (propNum != null && !propNum.matches("\\d+")) {
			errorMessage += "Project Error: Proposal Number must be a number. (ex: 11, 566)\n";
		}
		if (versionNum == null || versionNum.trim().isEmpty()) {
			errorMessage += "Project Error: Version Number must not be empty.\n";
		} else {
			if (!versionNum.matches("\\d+(.\\d)*")) {
				errorMessage += "Project Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}
		if (start == null) {
			errorMessage += "Project Error: Period of Performance must have a starting value.\n";
		}
		if (end == null) {
			errorMessage += "Project Error: Period of Performance must have ending value.\n";
		}

		ArrayList<CLIN> clins = proj.getCLINList();
		ArrayList<SDRL> sdrls = proj.getSDRLList();
		ArrayList<SOW> sows = proj.getSOWList();

		// Get errors for each CLIN
		for (CLIN c : clins) {
			String index = c.getIndex();
			String version = c.getVersion();
			LocalDate clinstart = c.getPopStart() == null ? null : LocalDate.parse(c.getPopStart());
			LocalDate clinend = c.getPopEnd() == null ? null : LocalDate.parse(c.getPopEnd());

			if (index == null || index.trim().isEmpty()) {
				errorMessage += "CLIN Error: Index cannot be empty.\n";
			} else if (!index.matches("\\d+")) {
				errorMessage += "CLIN Error: Index must be a number.\n";
				errorMessage += "\t " + index + " is not valid.\n";
			}

			if (version == null || version.trim().isEmpty()) {
				errorMessage += "CLIN Error: CLIN Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "CLIN Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
			if (clinstart == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a starting value.\n";
			}
			if (clinend == null) {
				errorMessage += "CLIN Error: CLIN Period of Performance must have a ending value.\n";
			}
		}

		for (SDRL s : sdrls) {
			String title = s.getName();
			String version = s.getVersion();

			if (title == null || title.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Name cannot be empty.\n";
			}
			if (version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}

		for (SOW s : sows) {
			String ref = s.getReference();
			String version = s.getVersion();

			if (ref == null || ref.trim().isEmpty()) {
				errorMessage += "SOW Error: SOW Ref cannot be empty\n";
			} else if (!ref.matches("\\d+")) {
				errorMessage += "SOW Error: SOW Reference Number must be a number.\n";
			}
			if (version == null || version.trim().isEmpty()) {
				errorMessage += "SDRL Error: SDRL Version cannot be empty.\n";
			} else if (!version.matches("\\d+(.\\d)*")) {
				errorMessage += "SDRL Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
		}

		if (errorMessage.equals("")) {
			errorMessage = null;
		}
		return errorMessage;
	}

	public static String checkProjectForSaving(ProjectVersion proj, String oldVersion) {
		String errorMessage = "";

		String versionNum = proj.getVersionNumber();

		if (versionNum != null && !versionNum.trim().isEmpty()) {
			if (!versionNum.matches("\\d+(.\\d)*")) {
				errorMessage += "Project Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
			}
			if (oldVersion != null) {
				String[] newVer = proj.getVersionNumber().split("\\.");
				String[] oldVer = oldVersion.split("\\.");
				for (int i = 0; i < newVer.length & i < oldVer.length; i++) {
					if (Integer.parseInt(newVer[i]) > Integer.parseInt(oldVer[i])) {
						break;
						// If greater, then rest is fine
					} else if (Integer.parseInt(newVer[i]) < Integer.parseInt(oldVer[i])) {

						errorMessage += "Project Error: Cannot change to a lower version number!";
						break;
					}
					// no else, if they are equal keep going.
				}
			}

		}

		String otherErrorMessage = checkProjectForSaving(proj);

		errorMessage += otherErrorMessage == null ? "" : otherErrorMessage;

		if (errorMessage.equals("")) {
			errorMessage = null;
		}
		return errorMessage;
	}

	// TODO currently not working with implementation
	public static String checkProjectForEstimating(ArrayList<CLIN> clins) { // Submit for Approval
		String errorMessage = "";

		for (CLIN c : clins) {
			ArrayList<OrganizationBOE> organizations = c.getOrganizations();

			for (OrganizationBOE o : organizations) {
				String organization = o.getOrganization();
				String product = o.getProduct();
				String orgVersion = o.getVersion();
				ArrayList<WorkPackage> orgWPs = o.getWorkPackages();

				if (organization == null || organization.trim().isEmpty()) {
					errorMessage += "Organization Error: Organization cannot be empty.\n";
				}
				if (product == null || product.trim().isEmpty()) {
					errorMessage += "Organization Error: Product cannot be empty.\n";
				}
				if (orgVersion == null || orgVersion.trim().isEmpty()) {
					errorMessage += "Organization Error: Organization Version cannot be empty.\n";
				} else if (!orgVersion.matches("\\d+(.\\d)*")) {
					errorMessage += "Organization Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
				}
				if (orgWPs.isEmpty()) {
					errorMessage += "Organization Error: At least ONE Work Package is required.\n";
				}

				for (WorkPackage w : orgWPs) {
					String wpName = w.getName();
					String boeAuthor = w.getAuthor();
					LocalDate wpStart = w.getPopStart() == null ? null : LocalDate.parse(w.getPopStart());
					LocalDate wpEnd = w.getPopEnd() == null ? null : LocalDate.parse(w.getPopEnd());
					String wpScope = w.getScope();
					String typeOfWork = w.getTypeOfWork();
					String wpVersion = w.getVersion();
					ArrayList<Task> wpTasks = w.getTasks();

					if (wpName == null || wpName.trim().isEmpty()) {
						errorMessage += "Work Package Error: Work Package Name cannot be empty.\n";
					}
					if (boeAuthor == null || boeAuthor.trim().isEmpty()) {
						errorMessage += "Work Package Error: Basis of Estimates Author cannot be empty.\n";
					}
					if (wpStart == null) {
						errorMessage += "Work Package Error: Work Package Period of Performance must have a starting value.\n";
					}
					if (wpEnd == null) {
						errorMessage += "Work Package Error: Work Package Period of Performance must have an ending value.\n";
					}
					if (wpScope == null || wpScope.trim().isEmpty()) {
						errorMessage += "Work Package Error: Scope of Work cannot be empty.\n";
					}
					if (typeOfWork == null || typeOfWork.trim().isEmpty()) {
						errorMessage += "Work Package Error: Type of Work cannot be empty.\n";
					}
					if (wpVersion == null || wpVersion.trim().isEmpty()) {
						errorMessage += "Work Package Error: Work Package Version cannot be empty.\n";
					} else if (!wpVersion.matches("\\d+(.\\d)*")) {
						errorMessage += "Work Package Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
					}
					if (wpTasks.isEmpty()) {
						errorMessage += "Work Package Error: At least ONE Task is required.\n";
					}

					for (Task t : wpTasks) {
						String taskName = t.getName();
						LocalDate taskStart = t.getPopStart() == null ? null : LocalDate.parse(t.getPopStart());
						LocalDate taskEnd = t.getPopEnd() == null ? null : LocalDate.parse(t.getPopEnd());
						String boeFormula = t.getFormula();
						int staffHours = t.getStaffHours();
						String taskVersion = t.getVersion();
						String taskDetails = t.getDetails();
						String taskCandA = t.getConditions();
						String taskMandR = t.getMethodology();

						if (taskName == null || taskName.trim().isEmpty()) {
							errorMessage += "Task Error: Task Name cannot be empty.\n";
						}
						if (taskStart == null) {
							errorMessage += "Task Error: Task Period of Performance must have a starting value.\n";
						}
						if (taskEnd == null) {
							errorMessage += "Task Error: Task Period of Performance must have an ending value.\n";
						}
						if (boeFormula == null || boeFormula.trim().isEmpty()) {
							errorMessage += "Task Error: Basis of Estimates Formula cannot be empty.\n";
						}
						if (staffHours <= 0) {
							errorMessage += "Task Error: Staff Hours must be greater than 0.\n";
						}
						if (taskVersion == null || taskVersion.trim().isEmpty()) {
							errorMessage += "Task Error: Task Version cannot be empty.\n";
						} else if (!taskVersion.matches("\\d+(.\\d)*")) {
							errorMessage += "Task Error: Invalid Version Number! Must be of form 1, 3.2.1, 6.11.10, etc\n";
						}
						if (taskDetails == null || taskDetails.trim().isEmpty()) {
							errorMessage += "Task Error: Task Details cannot be empty.\n";
						}
						if (taskCandA == null || taskCandA.trim().isEmpty()) {
							errorMessage += "Task Error: Task Conditions & Assumptions cannot be empty.\n";
						}
						if (taskMandR == null || taskMandR.trim().isEmpty()) {
							errorMessage += "Task Error: Estimate Methodology & Rationale cannot be empty.\n";
						}
					}
				}
			}
		}

		if (errorMessage.equals("")) {
			errorMessage = null;
		}
		return errorMessage;
	}
}
