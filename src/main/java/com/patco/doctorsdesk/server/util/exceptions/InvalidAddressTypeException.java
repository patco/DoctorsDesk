package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class InvalidAddressTypeException extends DoctorsDeskException {
	private static final long serialVersionUID = -4741058213040803799L;
	
	public InvalidAddressTypeException(int invalidtype, Throwable x) {
		super("Address type is invalid:"+invalidtype ,x);
	}

	public InvalidAddressTypeException(int invalidtype) {
		super("Address type is invalid:"+invalidtype);
	}

}
