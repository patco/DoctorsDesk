package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class PatientExistsException extends DoctorsDeskException{

	private static final long serialVersionUID = -6495331219412640700L;
	
	public PatientExistsException(int patientid) {
		this(patientid, "");
	}

	public PatientExistsException(int patientid, String message) {
		super("Patient not found:"+patientid+"\n"+message);
	}

	public PatientExistsException(int patientid, String message, Throwable x) {
		super("Patient not found:"+patientid+"\n"+message,x);
	}
	
	public PatientExistsException(int patientid, Throwable x) {
		this(patientid,"", x);
	}

}
