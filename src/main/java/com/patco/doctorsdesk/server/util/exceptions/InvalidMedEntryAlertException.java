package com.patco.doctorsdesk.server.util.exceptions;

public class InvalidMedEntryAlertException extends Exception {
	
	private static final long serialVersionUID = 2773823835698559717L;

	public InvalidMedEntryAlertException(int invalidtype, Throwable x) {
		super("Medican history entry AlertType is invalid:" + invalidtype, x);
	}

	public InvalidMedEntryAlertException(int invalidtype) {
		super("Medical history entry AlertType is invalid:" + invalidtype);
	}
}
