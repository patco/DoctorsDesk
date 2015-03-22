package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class DiscountNotFoundException extends DoctorsDeskException {

	private static final long serialVersionUID = 5131021629516222487L;
	
	public DiscountNotFoundException(String title, String message, Throwable x) {
		super("Discount not found:"+title+"\n"+message,x);
	}

	public DiscountNotFoundException(String title, String message) {
		super("Discount not found:"+title+"\n"+message);
	}

	public DiscountNotFoundException(int id, String message) {
		super("Discount not found:"+id+"\n"+message);
	}

	public DiscountNotFoundException(int id) {
		this (id, "");
	}

	public DiscountNotFoundException(String title) {
		this(title, "");
	}

	public DiscountNotFoundException(String title, Throwable x) {
		this(title,"", x);
	}
}
