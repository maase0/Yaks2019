package DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Select Query on Database
		ResultSet rs = DBUtil.dbExecuteQuery("SELECT * FROM ProjectVersion");
		
		//Actions to perform with data (this can be whatever, generally going to be filling in text fields)
	    System.out.println("\nidProjectVersion" + "     " + "idProject(FK)" + "\t" + "Project Manager" + "\t\t " + 
	    "Project Name");
	    
	    while(rs.next()) {
	  	  System.out.println("\t" + rs.getInt("idProjectVersion") + "\t\t  " + rs.getInt("idProject") + "\t\t     " + 
	        rs.getString("Project_Manager") + "\t\t" + rs.getString("Project_Name"));
	    }
	    return;
	}
}
