package com.monitoringlogs.emailservice;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.monitoringlogs.database.SqlConnection;
import com.monitoringlogs.entities.Application;
import com.monitoringlogs.entities.Incident;
import com.monitoringlogs.entities.Message;
import com.monitoringlogs.ftp.FTPConnection;

public class AutomationLogService extends Thread {
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.Utilidades");

	@Override
	public void run() {
		LOGGER.log(Level.INFO, "Start Automation Logs Service...");
		SqlConnection sql = new SqlConnection();
		
		//check email every minute
		try {
			while(true) {
				List<String> emails = EmailUtils.check();
				for(String email : emails) {
					Application app = getApplication(email);
					Timestamp timestamp = getTimestamp(email);
					
					//Get server by Application
					
					//search logs from the server
					FTPConnection ftp = new FTPConnection();
					List<Message> logs = ftp.readLogsFile(app.getLogs_path(), timestamp);
					
					//save all logs
					Message errorLog = null;
					for(Message log : logs){
						log.setIdserver(getIdserver(app.getIdapplication()));
						log.setIdapplication(app.getIdapplication());
						sql.insertLog(log);
						if(log.getLevel().equals(Message.Level.ERROR.toString()) 
								|| log.getLevel().equals(Message.Level.FATAL.toString())) {
							errorLog = log;
						}
					}
					
					Incident inc = new Incident();
					timestamp = new Timestamp(new Date().getTime());
					inc.setCreation_datetime(timestamp);
					//inc.setEnd_datetime(null);
					inc.setIdapplication(app.getIdapplication());
					Incident completedInc = sql.getIdCategory(errorLog.getException(), errorLog.getMessage());
					inc.setIdcategory(completedInc.getIdcategory());
					inc.setIdsolution(completedInc.getIdsolution());
					inc.setIduser(1);//
					inc.setIncident_number(sql.getIncidentNumber());
					//inc.setStart_datetime(start_datetime);
					inc.setStatus(Incident.STATUS_NEW);
					
					//set related logs to incident
					for(Message log : logs) {
						if(log.getStringTimestamp().equals(errorLog.getStringTimestamp())) {
							sql.insertLogToIncident(inc.getIdincident(), log.getIdlog(), 1, app.getIdapplication());
						}
					}
					
					//send email to developer
					//TODO: create sendEmail method
					//EmailUtils.sendEmail(url, email);
				}
				Thread.sleep(60000);
			}			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private Application getApplication(String email) {
		SqlConnection sql = new SqlConnection();
		List<Application> apps = sql.getApplications();

		for(Application app : apps) {
			if(email.contains(app.getApp_name().toUpperCase())) {
				return app;
			}
		}
		
		throw new IllegalArgumentException("Application does not found in the emai.");
	}
	
	private Timestamp getTimestamp(String email) {
		Timestamp timestamp = null;
		String strTimestamp = email.substring(email.indexOf("Timestamp") + "Timestamp".length(), email.length()-1);
		LOGGER.log(Level.OFF, "Timestamp from email " + strTimestamp);
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		    Date parsedDate = dateFormat.parse(strTimestamp);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { //this generic but you can control another types of exception
		    LOGGER.log(Level.SEVERE, "Occurred an error to try to cast the timestamp in the e-mail..." + e);
		}
		
		return timestamp;
	}
	
	private int getIdserver(int idapplication) {
		return new SqlConnection().getIdserverByApp(idapplication);
	}
	
	public static void main(String [] mkd) {
		Thread service = new AutomationLogService();
		service.start();
	}
}
