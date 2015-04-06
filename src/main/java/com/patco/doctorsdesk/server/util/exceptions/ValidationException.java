package com.patco.doctorsdesk.server.util.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;


public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -5217481808048778676L;
	private static final Logger logger = LoggerFactory.getLogger(DoctorsDeskException.class);
	public ValidationException(String msg, Throwable t) {
		super(msg, t);
		logger.error(msg, t);
	}
	
	public ValidationException(String msg) {
		super(msg);
		logger.error(msg);
	}

}
