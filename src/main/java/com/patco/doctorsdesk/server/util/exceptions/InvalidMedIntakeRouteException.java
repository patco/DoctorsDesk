package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class InvalidMedIntakeRouteException extends DoctorsDeskException {
	
	private static final long serialVersionUID = 4983419371674637648L;

	public InvalidMedIntakeRouteException(int invalidtype, Throwable x) {
		super("Medicine intake route is invalid:" + invalidtype, x);
	}

	public InvalidMedIntakeRouteException(int invalidtype) {
		super("Medicine intake route is invalid:" + invalidtype);
	}
}
