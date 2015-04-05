package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class VisitNotFoundException extends DoctorsDeskException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7473924990268461395L;

	public VisitNotFoundException(int visitid) {
		super("Visit not found:"+visitid);
	}

	public VisitNotFoundException(int visitid, String message) {
		super("Visit not found:"+visitid+"\n"+message);
	}

	public VisitNotFoundException(int visitid, String message, Throwable x) {
		super("Visit not found:"+visitid+"\n"+message,x);
	}
	
	public VisitNotFoundException(int visitid, Throwable x) {
		this(visitid,"", x);
	}
}
