package com.monitoringlogs.entities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	
	private final String EXAMPLE_TIMESTAMP = "[09/25/2018 12:35:44,941 CDT]";
	private int idlog;
	private int idserver;
	private int idapplication;
	private Timestamp time;
	private String exception;
	private String logger;
	private String resource; //class
	private String source; //path log file
	private String level;
	private String message;
	private String event;
	
	//level types
	enum Level 
	{ 
	    INFO, ERROR, WARNING, FATAL, DEBUG; 
	} 
	
	public Message(String log) {
		this.event = log;
		String strTimestamp = log.substring(0, EXAMPLE_TIMESTAMP.length());
		this.level = evaluateLevel();
		this.source = log.substring(log.indexOf(" [") + 2, log.lastIndexOf("]"));
		this.logger = this.evaluateLogger();
		this.message = log.substring(log.indexOf(this.logger) + this.logger.length() + 1, 
				log.length()).trim();
		this.exception = evaluateException();
		this.time = evaluateTimestamp(strTimestamp);
	}
	
	public int getIdlog() {
		return idlog;
	}
	public void setIdlog(int idlog) {
		this.idlog = idlog;
	}
	public int getIdserver() {
		return idserver;
	}
	public void setIdserver(int idserver) {
		this.idserver = idserver;
	}
	public int getIdapplication() {
		return idapplication;
	}
	public void setIdapplication(int idapplication) {
		this.idapplication = idapplication;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getLogger() {
		return logger;
	}
	public void setLogger(String logger) {
		this.logger = logger;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	
	private String evaluateLevel() {
		if(this.event.contains(Level.INFO.toString())) {
			return Level.INFO.toString();
		} else if(this.event.contains(Level.DEBUG.toString())) {
			return Level.DEBUG.toString();
		} else if(this.event.contains(Level.WARNING.toString())) {
			return Level.WARNING.toString();
		} else if(this.event.contains(Level.ERROR.toString())) {
			return Level.ERROR.toString();
		} else if(this.event.contains(Level.FATAL.toString())) {
			return Level.FATAL.toString();
		}
		
		throw new IllegalArgumentException("LEVEL not found in log: " + this.level);
	}
	
	private String evaluateException() {
		if(! this.level.equals(Level.ERROR.toString()) 
				&& ! this.level.equals(Level.FATAL.toString()) ) {
			return null;
		}
		
		String exceptionAux = this.event.substring(0, this.event.indexOf("Exception") + "Exception".length());
		String exception = "";
		for(int i = exceptionAux.length()-1;i > 0;i--) {
			if(exceptionAux.charAt(i) == ' ') {
				break;
			}
			exception = exceptionAux.charAt(i) + exception;
		}
		
		return exception;
	}
	
	private Timestamp evaluateTimestamp(String strTimestamp) {
		String strDate = strTimestamp.substring(1, 11);
		String strTime = strTimestamp.substring(12, 24);
		String [] splitDate = strDate.split("/");
		Timestamp timestamp = null;
		
		try {
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
		    Date parsedDate = dateFormat.parse(splitDate[2] + "-" + splitDate[0] 
		    		+ "-" + splitDate[1] + " " + strTime);
		    timestamp = new java.sql.Timestamp(parsedDate.getTime());
		} catch(Exception e) { 
			System.err.println("Error on evaluateTimestamp: " + e);
		}
		
		return timestamp;
	}
	
	private String evaluateLogger() {
		if(! this.source.contains(".")) {
			return this.source;
		}
		
		String [] splitSource = this.source.split("\\.");
		
		return splitSource[splitSource.length - 1];
	}

	@Override
	public String toString() {
		return "Message [idlog=" + idlog + ", idserver=" + idserver + ", idapplication=" + idapplication + ", time="
				+ time + ", exception=" + exception + ", logger=" + logger + ", resource=" + resource + ", source="
				+ source + ", level=" + level + ", message=" + message + ", event=" + event + "]";
	}
	
	
}
