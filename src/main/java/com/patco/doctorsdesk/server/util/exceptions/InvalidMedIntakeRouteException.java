package com.patco.doctorsdesk.server.util.exceptions;

public class InvalidMedIntakeRouteException extends Exception {
	
	private static final long serialVersionUID = 4983419371674637648L;

	public InvalidMedIntakeRouteException(int invalidtype, Throwable x) {
		super("Medicine intake route is invalid:" + invalidtype, x);
	}

	public InvalidMedIntakeRouteException(int invalidtype) {
		super("Medicine intake route is invalid:" + invalidtype);
	}
}
