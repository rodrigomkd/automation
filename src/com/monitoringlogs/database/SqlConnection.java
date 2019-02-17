package com.monitoringlogs.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.monitoringlogs.entities.Application;
import com.monitoringlogs.entities.Incident;
import com.monitoringlogs.entities.Message;

public class SqlConnection {
	private final String SERVER = "";
	private final String DATABASE = "";
	private final String PORT = "";
	private final String USER = "";
	private final String PASSWORD = "";
	private final String DRIVER_CLASSPATH = "org.mariadb.jdbc.Driver";
	
	public void insertLog(Message message){
		Connection con = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);			
			con = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			final String query = "INSERT INTO log "
					+ "(idserver,idapplication,time,exception,logger,resource,source,"
					+ "level,message,event) VALUES (?,?,?,?,?,?,?,?,?,?,);";
					
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, message.getIdserver());
			preparedStmt.setInt(2, message.getIdapplication());
			preparedStmt.setTimestamp(3, message.getTime());
			preparedStmt.setString(4, message.getException());
			preparedStmt.setString(5, message.getLogger());
			preparedStmt.setString(6, message.getResource());
			preparedStmt.setString(7, message.getSource());
			preparedStmt.setString(8, message.getLevel());
			preparedStmt.setString(9, message.getMessage());
			preparedStmt.setString(10, message.getEvent());
			
			preparedStmt.execute();	
			preparedStmt.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
	}
	
	public void insertIncident(Incident incident){
		Connection con = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			final String query = "INSERT INTO incident (idapplication,"
					+ "idcategory,incident_number,creation_datetime,start_datetime,"
					+ "end_datetime,status,iduser,idsolution) "
					+ "VALUES (?,?,?,?,?,?,?,?,?);";
					
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, incident.getIdapplication());
			preparedStmt.setInt(2, incident.getIdcategory());
			preparedStmt.setString(3, incident.getIncident_number());
			preparedStmt.setTimestamp(4, incident.getCreation_datetime());
			preparedStmt.setTimestamp(5, incident.getStart_datetime());
			preparedStmt.setTimestamp(6, incident.getEnd_datetime());
			preparedStmt.setString(7, "" + incident.getStatus());
			preparedStmt.setInt(8, incident.getIduser());
			preparedStmt.setInt(9, incident.getIdsolution());
			
			preparedStmt.execute();	
			preparedStmt.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
	}
	
	public List<Application> getApplications() {
		Connection con = null;
		Statement stmt = null;
		List<Application> apps = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT idapplication, app_name FROM application");  
			apps = new ArrayList<Application>();
			
			while(rs.next())  {
				Application app = new Application();
				app.setIdapplication(rs.getInt(1));
				app.setApp_name(rs.getString(2));
				apps.add(app);
			}
			
			rs.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {	
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
		
		return apps;
	}
	
	public int getIdserverByApp(int idapplication) {
		Connection con = null;
		Statement stmt = null;
		int idserver = -1;
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT idserver FROM application_server WHERE idapplication = " 
					+ idapplication);  
			
			while(rs.next())  {
				idserver = rs.getInt(1);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {	
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
		
		return idserver;
	}
	
	public String getIncidentNumber() {
		Connection con = null;
		Statement stmt = null;
		String incidentNumber = "INC";//inc0000100
		int idincident = 1000; //set 1 by default
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("SELECT MAX(incident_number) FROM incident");
			
			while(rs.next())  {
				String currentIncNumber = rs.getString(1);
				if(currentIncNumber != null) {
					currentIncNumber = currentIncNumber.substring(3);
					idincident = Integer.valueOf(currentIncNumber);
				}
				
				idincident++;		
			}
			
			if(String.valueOf(idincident).length() == 4) {
				incidentNumber += "000" + idincident;
			} else if(String.valueOf(idincident).length() == 5) {
				incidentNumber += "00" + idincident;
			} else if(String.valueOf(idincident).length() == 6) {
				incidentNumber += "0" + idincident;
			} else if(String.valueOf(idincident).length() == 7) {
				incidentNumber += idincident;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {	
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
		
		return incidentNumber;
	}
	
	public Incident getIdCategory(String exception, String message) {
		Connection con = null;
		PreparedStatement stmt = null;
		Incident inc = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);			
			con = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			final String QUERY = "SELECT idcategory FROM `log` WHERE exception = ? AND message = ?";
			
			stmt = con.prepareStatement(QUERY);
			stmt.setString(1, exception);
			stmt.setString(2, message);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next())  {
				inc = new Incident();
				inc.setIdcategory(rs.getInt("idcategory"));
				inc.setIdsolution(rs.getInt("idsolution"));
				break;
			}
			
			rs.close();
		
		} catch (Exception e) {
			System.err.println("Error on getIdCategory(): " + e);
			e.printStackTrace();
		}  finally {	
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
		
		return inc;
	}

	public void insertLogToIncident(int idincident, int idlog, int idserver, int idapplication) {
		Connection con = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);			
			con = DriverManager.getConnection("jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			final String query = "INSERT INTO log_incident "
					+ "(idincident,idlog,idserver,idapplication"
					+ ") VALUES (?,?,?,?);";
					
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, idincident);
			preparedStmt.setInt(2, idlog);
			preparedStmt.setInt(3, idserver);
			preparedStmt.setInt(4, idapplication);
			
			preparedStmt.execute();	
			preparedStmt.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(con != null){
				try {				
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}		
		}
	}
}
