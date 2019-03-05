package com.monitoringlogs.entities;

import java.sql.Timestamp;

public class Incident {
	public static char STATUS_NEW = 'N';
	public static char STATUS_W = 'W';
	public static char STATUS_C = 'C';
	
	private int idincident;
	private int idapplication;
	private int idcategory;
	private String incident_number;
	private Timestamp creation_datetime;
	private Timestamp start_datetime;
	private Timestamp end_datetime;
	private char status;
	private int iduser;
	private int idsolution;
	public int getIdincident() {
		return idincident;
	}
	public void setIdincident(int idincident) {
		this.idincident = idincident;
	}
	public int getIdapplication() {
		return idapplication;
	}
	public void setIdapplication(int idapplication) {
		this.idapplication = idapplication;
	}
	public int getIdcategory() {
		return idcategory;
	}
	public void setIdcategory(int idcategory) {
		this.idcategory = idcategory;
	}
	public String getIncident_number() {
		return incident_number;
	}
	public void setIncident_number(String incident_number) {
		this.incident_number = incident_number;
	}
	public Timestamp getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Timestamp creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public Timestamp getStart_datetime() {
		return start_datetime;
	}
	public void setStart_datetime(Timestamp start_datetime) {
		this.start_datetime = start_datetime;
	}
	public Timestamp getEnd_datetime() {
		return end_datetime;
	}
	public void setEnd_datetime(Timestamp end_datetime) {
		this.end_datetime = end_datetime;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public int getIdsolution() {
		return idsolution;
	}
	public void setIdsolution(int idsolution) {
		this.idsolution = idsolution;
	}
	
	@Override
	public String toString() {
		return "Incident [idincident=" + idincident + ", idapplication=" + idapplication + ", idcategory=" + idcategory
				+ ", incident_number=" + incident_number + ", creation_datetime=" + creation_datetime
				+ ", start_datetime=" + start_datetime + ", end_datetime=" + end_datetime + ", status=" + status
				+ ", iduser=" + iduser + ", idsolution=" + idsolution + "]";
	}
}
