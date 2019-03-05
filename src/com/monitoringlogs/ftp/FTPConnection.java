package com.monitoringlogs.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.monitoringlogs.database.SqlConnection;
import com.monitoringlogs.entities.Incident;
import com.monitoringlogs.entities.Message;

public class FTPConnection {
	
	public List<Message> readLogsFile(String path, Timestamp timestamp) {
		
		List<Message> logs = new ArrayList<Message>();
		File file = new File(path); 
		  
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
		
			String st;
			String message = "";
			while ((st = br.readLine()) != null) {
				message = st;
				if(st.contains("\t")) {
					message += st;
					continue;
				}
				Message m = new Message(message);
				if(m.getTime().after(timestamp)) {
					logs.add(m);
				}
				
				message = "";
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();		
		}
		
		return logs;
	}
	
	public void readIssuesFile(String appName, int idapplication){
		File file = new File(appName); 		  
		BufferedReader br;
		
		try {
			br = new BufferedReader(new FileReader(file));
		
			String st;
			while ((st = br.readLine()) != null) {
				String [] issues = st.split(" ");
				if(issues[0].equals("YYYYMM")) {
					continue;
				}
				
				//create incidents
				generateIncidents(idapplication, issues[0], Integer.valueOf(issues[1]));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();		
		} catch (IOException e) {
			e.printStackTrace();		
		}
	}
	
	private void generateIncidents(int idapplication, String yyyymm, int count) {
		
		int year = Integer.valueOf(yyyymm.split("-")[0]);
		String month = yyyymm.split("-")[1];
		int i = 0;
		Incident inc = new Incident();
		
		while(i < count) {
			int day = new Random().nextInt(31 - 1) + 1;
			String sDay = day < 10 ? "0" + day : "" + day;
			int hour = new Random().nextInt(24);
			String sHour = hour < 10 ? "0" + hour : "" + hour;
			int minute = new Random().nextInt(60);
			String sMin = minute < 10 ? "0" + minute : "" + minute;
			int second = new Random().nextInt(60);
			String sSec = second < 10 ? "0" + second : "" + second;
			int nano = new Random().nextInt(500);
	
			System.out.println(year+"-"+month+"-"+sDay+" "+sHour+":"+sMin+":"+sSec+"." + nano);
			Timestamp timestamp = java.sql.Timestamp.valueOf(year+"-"+month+"-"+sDay+" "+sHour+":"+sMin+":"+sSec+"." + nano);		
			inc.setCreation_datetime(timestamp);
			inc.setIncident_number(new SqlConnection().getIncidentNumber());
			inc.setIdapplication(idapplication);
			inc.setStatus('C');
			inc.setIdcategory(getCategory());
			
			SqlConnection sql = new SqlConnection();
			sql.insertIncident(inc);
			i++;
			System.out.println("The Incident was created: " + inc);
		}
	}
	
	private int getCategory() {
		int random = new Random().nextInt(101);
		if(random < 30)
		{
			return 5;
		} else if(random < 50) {
			return 4;
		} else if(random < 80) {
			return 3;
		} else if(random < 95) {
			return 2;
		} else if(random <= 100) {
			return 1;
		}
		
		return 0;
	}
}

