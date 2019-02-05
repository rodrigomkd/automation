package com.monitoringlogs.entities;

public class Application {

	private int idapplication;
	private String app_name;
	private String CUID;
	private String email_distribution;
	private String logs_path;
	private String arima;
	private String description;
	
	public int getIdapplication() {
		return idapplication;
	}
	public void setIdapplication(int idapplication) {
		this.idapplication = idapplication;
	}
	public String getApp_name() {
		return app_name;
	}
	public void setApp_name(String app_name) {
		this.app_name = app_name;
	}
	public String getCUID() {
		return CUID;
	}
	public void setCUID(String cUID) {
		CUID = cUID;
	}
	public String getEmail_distribution() {
		return email_distribution;
	}
	public void setEmail_distribution(String email_distribution) {
		this.email_distribution = email_distribution;
	}
	public String getLogs_path() {
		return logs_path;
	}
	public void setLogs_path(String logs_path) {
		this.logs_path = logs_path;
	}
	public String getArima() {
		return arima;
	}
	public void setArima(String arima) {
		this.arima = arima;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
