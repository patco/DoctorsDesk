package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class DoctorExistsException extends DoctorsDeskException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2280901092221502236L;

	public DoctorExistsException(String dentistUserName, String message, Throwable x) {
		super("Dentist already Exists:"+dentistUserName+"\n"+message,x);
	}

	public DoctorExistsException(String dentistUserName, String message) {
		super("Dentist already Exists:"+dentistUserName+"\n"+message);
	}
	
	public DoctorExistsException(String dentistUserName) {
		this(dentistUserName, "");
	}

	public DoctorExistsException(String dentistUserName, Throwable x) {
		this(dentistUserName,"", x);
	}
}
