package com.patco.doctorsdesk.server.util.exceptions;

import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class PricelistItemNotFoundException extends DoctorsDeskException{

	private static final long serialVersionUID = -2299257793540458595L;
	
	public PricelistItemNotFoundException(String title, String message, Throwable x) {
		super("Pricelist Item not found:"+title+"\n"+message,x);
	}

	public PricelistItemNotFoundException(String title, String message) {
		super("Pricelist Item not found:"+title+"\n"+message);
	}

	public PricelistItemNotFoundException(int id, String message) {
		super("Pricelist Item id not found:"+id+"\n"+message);
	}

	public PricelistItemNotFoundException(int id) {
		this (id, "");
	}

	public PricelistItemNotFoundException(String title) {
		this(title, "");
	}

	public PricelistItemNotFoundException(String title, Throwable x) {
		this(title,"", x);
	}

}
