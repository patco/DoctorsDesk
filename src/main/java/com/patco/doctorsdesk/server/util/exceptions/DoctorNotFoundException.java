package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class DoctorNotFoundException extends DoctorsDeskException {

	private static final long serialVersionUID = -1850121005717603591L;
	
	public DoctorNotFoundException(String username, String message, Throwable x) {
		super("Dentist not found:"+username+"\n"+message,x);
		
	}

	public DoctorNotFoundException(String username, String message) {
		super("Dentist not found:"+username+"\n"+message);
		
	}

	public DoctorNotFoundException(int dentistid, String message) {
		super("Dentist id not found:"+dentistid+"\n"+message);
	}

	public DoctorNotFoundException(int dentistid) {
		this (dentistid, "");
	}

	public DoctorNotFoundException(String username) {
		this(username, "");
	}

	public DoctorNotFoundException(String username, Throwable x) {
		this(username,"", x);
	}

}
