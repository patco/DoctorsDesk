package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class PatientNotFoundException extends DoctorsDeskException {

	private static final long serialVersionUID = 5010266450255743316L;
	
	public PatientNotFoundException(int patientid, String message, Throwable x) {
		super("Patient not found:"+patientid+"\n"+message,x);
	}

	public PatientNotFoundException(int patientid, String message) {
		super("Patient not found:"+patientid+"\n"+message);
	}
	
	public PatientNotFoundException(int patientid) {
		this(patientid, "");
	}

	public PatientNotFoundException(int patientid, Throwable x) {
		this(patientid,"", x);
	}

}
