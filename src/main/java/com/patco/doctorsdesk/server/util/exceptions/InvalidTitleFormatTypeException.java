package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class InvalidTitleFormatTypeException extends DoctorsDeskException {

	private static final long serialVersionUID = 8984308476844792876L;

	public InvalidTitleFormatTypeException(int invalidtype, Throwable x) {
		super("Title Format Type is invalid:" + invalidtype, x);
	}

	public InvalidTitleFormatTypeException(int invalidtype) {
		super("Title Format Type is invalid:" + invalidtype);
	}

}
