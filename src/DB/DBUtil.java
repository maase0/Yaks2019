package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import java.util.Properties;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sun.rowset.CachedRowSetImpl;

/**
 * Modified from example class by ONUR BASKIRT
 * Database Helper Class
 * Used to connect to MySQL database over SSH, Perform Queries, and disconnect.
 */
public class DBUtil
{
	//Declare JDBC Driver
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	
	//Connection
	private static Connection conn = null;
	private static Session session = null;
	
	//Connection String
	private static final String connStr = "jdbc:mysql://localhost:3366/Estimation_Suite";
    
	//SSH Tunnel
	private static void doSshTunnel( String strSshUser, String strSshPassword, String strSshHost, int nSshPort, String strRemoteHost, int nLocalPort, int nRemotePort ) throws JSchException
	{
		final JSch jsch = new JSch();
	    session = jsch.getSession( strSshUser, strSshHost, 22 );
	    session.setPassword( strSshPassword );
	     
	    final Properties config = new Properties();
	    config.put( "StrictHostKeyChecking", "no" );
	    session.setConfig( config );
	    jsch.addIdentity("tortoise_key.pem");
	    
	    session.connect();
	    session.setPortForwardingL(nLocalPort, strRemoteHost, nRemotePort);
	    System.out.println("SSH Tunnel Successful!");
	}
	
	//Connect to DB
	public static void dbConnect() throws SQLException, ClassNotFoundException {
		//Setting mysql JDBC Driver
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Missing MySQL Driver");
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("MySQL JDBC Driver Registered!");
		
		//For use with SSH Tunnel and MySQL Connection
		String strSshUser = "ubuntu";                 						// SSH loging username
	    String strSshPassword = "tortoise101$";                   			// SSH login password
	    String strSshHost = "ec2-18-209-59-108.compute-1.amazonaws.com";	// hostname or ip or SSH server
	    int nSshPort = 22;                               		    		// remote SSH host port number
	    String strRemoteHost = "127.0.0.1";  								// hostname or ip of your database server
	    int nLocalPort = 3366;                                				// local port number use to bind SSH tunnel
	    int nRemotePort = 3306;                               				// remote port number of your database 
	    String strDbUser = "root";                    						// database loging username
	    String strDbPassword = "tortoise101$";                    			// database login password
	    String schemaName = "Estimation_Suite";								// schema name

		// possibly make constant, can't be changed
	    
		//Establish MySQL Connection
		try {
		    //Call SSH Tunnel method
		    try {
				DBUtil.doSshTunnel(strSshUser, strSshPassword, strSshHost, nSshPort, strRemoteHost, nLocalPort, nRemotePort);
			} catch (JSchException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(connStr, strDbUser, strDbPassword);
			System.out.println("Successfully Connected to Database!");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console " + e);
			e.printStackTrace();
			throw e;
		}
	}
	
	//Close Connection
	public static void dbDisconnect() throws SQLException {
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
				System.out.println("Successfully Disconnected from Database!");
			}
			if(session != null && session.isConnected()) {
				session.disconnect();
				System.out.println("Closing SSH connection");
			}
		} catch(Exception e) {
			throw e;
		}
	}
	
	//DB Execute Query Operations
	public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
		//Declare statement, resultSet, and CachedResultSet as null
		Statement stmt = null;
		ResultSet resultSet = null;
		CachedRowSetImpl crs = null;
		try {
			System.out.println("\nSelect statement: " + queryStmt + "\n");
			
			//Create statement
			stmt = conn.createStatement();
			
			//Execute select (query) operation
			resultSet = stmt.executeQuery(queryStmt);
			
			//CachedRowSet Implementation
			crs = new CachedRowSetImpl();
			crs.populate(resultSet);
	} catch (SQLException e) {
		System.out.println("Problem occured at executeQuery operation: " + e);
		throw e;
	} finally {
			if (resultSet != null) {
				//Close resultSet
				resultSet.close();
			}
			if (stmt != null) {
				//Close Statement
				stmt.close();
			}
		}
		//Return CachedRowSet
		return crs;
	}	
	
	
	//DB Execute Update (For Update/Insert/Delete) Operations
	public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
		//Declare statement as null
		Statement stmt = null;
		try {
			System.out.println("\nSQL statement: " + sqlStmt + "\n");
			//Create Statement
			stmt = conn.createStatement();
			//Run executeUpdate operation with given sql statement
			stmt.executeUpdate(sqlStmt);
		} catch (SQLException e) {
			System.out.println("Problem occured at executeUpdate operation: " + e);
			throw e;
		} finally {
			if (stmt!=null) {
				//Close statement
				stmt.close();
			}
		}
	}
}