package com.patco.doctorsdesk.server.util.exceptions;

public class InvalidContactInfoTypeException extends Exception {

	private static final long serialVersionUID = 2158392865895489109L;

	public InvalidContactInfoTypeException(int invalidtype, Throwable x) {
		super("Contact info type is invalid:" + invalidtype, x);
	}

	public InvalidContactInfoTypeException(int invalidtype) {
		super("Contact info type is invalid:" + invalidtype);
	}

}
