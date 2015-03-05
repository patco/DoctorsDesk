package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class ValidationException extends DoctorsDeskException {

	private static final long serialVersionUID = -5217481808048778676L;
	
	public ValidationException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public ValidationException(String msg) {
		super(msg);
	}

}
