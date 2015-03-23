package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class ActivityNotFoundException extends DoctorsDeskException{

	private static final long serialVersionUID = -7327138607944930545L;

	public ActivityNotFoundException(int activityid) {
		super("Activity not found:"+activityid+"\n");
	}

	public ActivityNotFoundException(int activityid, String message) {
		super("Activity not found:"+activityid+"\n"+message);
	}

	public ActivityNotFoundException(int activityid, String message, Throwable x) {
		super("Activity not found:"+activityid+"\n"+message,x);
	}
	
	public ActivityNotFoundException(int activityid, Throwable x) {
		this(activityid,"", x);
	}

}
