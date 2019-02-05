package com.monitoringlogs.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GetDatabaseInfo{
	private final String SERVER = "";
	private final String DATABASE = "";
	private final String PORT = "";
	private final String USER = "";
	private final String PASSWORD = "";
	private final String DRIVER_CLASSPATH = "org.mariadb.jdbc.Driver";
	
	/*
	private final String SELECT_STATEMENT = "<?php"+"\n"	
        + "$data = json_decode(file_get_contents(\"php://input\"));"+"\n"
        + "include('config.php');"+"\n"
		+ "$db = new DB();"+"\n"
		+ "$sql = {SELECT};"+"\n"
		+ "$data = $db->qryRow($sql);"+"\n"
		+ "echo json_encode($data);"+"\n"
		+ "?>";
	*/
	
	public void getData(){
		try{  
			Class.forName(DRIVER_CLASSPATH);  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://199.79.63.142:3306/gvvcobez_saving_box","gvvcobez_rodrigo","Rodrigo.1");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from socio");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
			}catch(Exception e){ System.out.println(e);}  
	}
	
	public static void main(String args[]){  
		GetDatabaseInfo sql = new GetDatabaseInfo();
		List<String> tables = sql.getTables();
		for(String table : tables) {
			System.out.println(table);
			//sql.getColumnsAsJavaAttributes(table);
			sql.getColumnsAsInsertQuery(table);
		}
	} 
	
	public List<String> getTables(){
		Connection con = null;
		List<String> tables = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			con=DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);
					
			DatabaseMetaData md = con.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			tables = new ArrayList<String>();
			while (rs.next()) {
				tables.add(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(con != null) {
					con.close();
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return tables;
	}
	
	public void getColumnsAsJavaAttributes(String table){
		Connection con = null;
		Statement stmt = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select column_name, data_type from information_schema.columns " +
					"where table_schema = '"+DATABASE+"' and " +
					"table_name = '"+table+"'");  
			while(rs.next())  {
				String attribute = rs.getString(1);
				String dataType = convertDataType(rs.getString(2));
				
				System.out.println("	private " + dataType + " " + attribute + ";");  
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
	}
	
	public void getColumnsAsInsertQuery(String table){
		Connection con = null;
		Statement stmt = null;
		
		try {
			Class.forName(DRIVER_CLASSPATH);
			
			con = DriverManager.getConnection(  
					"jdbc:mysql://"+SERVER+":"+PORT+"/"+DATABASE, USER, PASSWORD);   
					
			stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select column_name, data_type from information_schema.columns " +
					"where table_schema = '"+DATABASE+"' and " +
					"table_name = '"+table+"'");
			
			String insertQuery = "INSERT INTO " + table + " (";
			String valuesQuery = "";
			while(rs.next())  {
				String attribute = rs.getString(1);
				
				insertQuery += attribute + ",";
				valuesQuery += "?,";
			}
			
			insertQuery += ") VALUES (" + valuesQuery + ");";
			System.out.println(insertQuery);
		
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
	}
	
	//SELECT ALL AND BY ID, INSERT, UPDATE, DELETE
	private String convertDataType(String dataType) {
		switch(dataType.toLowerCase()) {
		case "smallint": return "int";
		case "varchar" : return "String";
		case "int" : return dataType;
		case "char" : return dataType;
		case "text" : return "String";
		case "bigint" : return "int";
		case "datetime" : return "Timestamp";
		}
		
		throw new IllegalArgumentException("Data Type not found: " + dataType);
	}
} 

