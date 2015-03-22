package com.patco.doctorsdesk.server.util.exceptions;


public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -5217481808048778676L;
	
	public ValidationException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public ValidationException(String msg) {
		super(msg);
	}

}
