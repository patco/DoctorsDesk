package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.InvalidTitleFormatTypeException;

@Entity
public class UserPreferences extends DBEntity implements Serializable {

	private static final long serialVersionUID = 5978164937464593953L;
	
	public static final String DEFAULT_USER_THEME = "aristo";
	public static final String DEFAULT_USER_EMAILCONTENT = "";
	public static final String DEFAULT_USER_PRESCRIPTIONHEADER = "";
	public static final String DEFAULT_USER_REPORTEMAIL = "";
	public static final boolean DEFAULT_USER_EMAILNOTIFICATIONS = true;
	public static final boolean DEFAULT_USER_DAILYREPORTS = true;
	public static final int DEFAULT_USER_SCHEDMINHR= 6;
	public static final int DEFAULT_USER_SCHEDMAXHR= 22;
	public static final int DEFAULT_USER_SCHEDSTARTHR= 8;
	public static final int DEFAULT_USER_EVTITLEFORMAT = DoctorsDeskUtils.EventTitleFormatType.SHORT.getValue();
	public static final int DEFAULT_USER_SCHEDSLOTMINS= 5;
	@Id
	@OneToOne
	@JoinColumn(name="userid") 
	private Doctor doctor;
	
	private String emailcontent;
	private boolean emailnotification;
	private boolean dailyreports;
	private int eventtitleformat;
	private String theme;
	private String reportemail;
	private String prescriptionheader;
	
	private int scheduler_minhr;
	private int scheduler_maxhr;
	private int scheduler_starthr;
	private int scheduler_slotmins;
	
	//GETTERS/SETTERS
	public String getTheme() {	return theme;	}
	public String getEmailcontent() {	return emailcontent;	}
	public Doctor getDoctor() 	{	return doctor;	}
	public int getSchedulerMinHour() {	return scheduler_minhr; }
	public int getSchedulerMaxHour() {	return scheduler_maxhr; }
	public int getSchedulerStartHour() {	return scheduler_starthr; }
	public int getSchedulerSlotMins() {	return scheduler_slotmins; }
	public int getEventTitleFormatType() {	return eventtitleformat; }
	public String getPrescriptionHeader(){	return prescriptionheader;	}
	public boolean isEmailnotification() {	return emailnotification;	}
	public boolean isDailyreports() 	 {	return dailyreports;	}
	public String getReportemail() {	return reportemail;	}

	public void setEventTitleFormatType (int type) throws InvalidTitleFormatTypeException {
		if (DoctorsDeskUtils.isTitleFormatTypeValid(type)) {
	    	this.eventtitleformat = type;
	    	return;
		}
    	throw new InvalidTitleFormatTypeException(type);
	}
	public void setReportemail(String reportemail) {	this.reportemail = reportemail;	}
	public void setSchedulerMaxHour(int hour) { scheduler_maxhr = hour; }
	public void setSchedulerMinHour(int hour) { scheduler_minhr = hour; }
	public void setSchedulerStartHour(int hour) { scheduler_starthr = hour; }
	public void setSchedulerSlotMins(int hour) { scheduler_slotmins = hour; }
	public void setTheme(String theme) {	this.theme = theme;	}
	public void setEmailnotification(boolean emailnotification) {	this.emailnotification = emailnotification;	}
	public void setDailyreports(boolean dailyreports) {	this.dailyreports = dailyreports;	}
	public void setEmailcontent(String emailcontent)  {	this.emailcontent = emailcontent;	}
	public void setPrescriptionHeader(String header)  {	this.prescriptionheader = header;	}
	public void setDoctor(Doctor doctor) {	this.doctor = doctor;	}
	
	@Override
	public String getXML() {
		StringBuffer ans = new StringBuffer();
		return ans.toString();
	}

	public void reset() {
		this.setDailyreports(UserPreferences.DEFAULT_USER_DAILYREPORTS);
		this.setEmailcontent(UserPreferences.DEFAULT_USER_EMAILCONTENT);
		this.setEmailnotification(UserPreferences.DEFAULT_USER_EMAILNOTIFICATIONS);
		try {
			this.setEventTitleFormatType(UserPreferences.DEFAULT_USER_EVTITLEFORMAT);
		} catch (Exception ignored) {}
		this.setTheme(UserPreferences.DEFAULT_USER_THEME);
		this.setSchedulerMaxHour(UserPreferences.DEFAULT_USER_SCHEDMAXHR);
		this.setSchedulerMinHour(UserPreferences.DEFAULT_USER_SCHEDMINHR);
		this.setSchedulerStartHour(UserPreferences.DEFAULT_USER_SCHEDSTARTHR);
		this.setSchedulerSlotMins(UserPreferences.DEFAULT_USER_SCHEDSLOTMINS);
		this.setPrescriptionHeader(UserPreferences.DEFAULT_USER_PRESCRIPTIONHEADER);
		this.setReportemail(UserPreferences.DEFAULT_USER_REPORTEMAIL);
	}

}
