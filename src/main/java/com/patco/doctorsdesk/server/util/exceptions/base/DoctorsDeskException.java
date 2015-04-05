package com.patco.doctorsdesk.server.util.exceptions.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoctorsDeskException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994158992126156476L;
	public static final String EXCEPTION_NODE = "<exception>";
	public static final String EXCEPTION_ENDNODE = "</exception>";
	
	private static final Logger logger = LoggerFactory.getLogger(DoctorsDeskException.class);

	public DoctorsDeskException() {
		super();
	}

	public DoctorsDeskException(String message) {		
		super(message);
		logger.error(message);
	}

	public DoctorsDeskException(Throwable t) {
		super(t);
		logger.error(t.getMessage());
	}

	public DoctorsDeskException(String message, Throwable t) {
		super(message, t);
		logger.error(message,t);
	}

	public String getXML() {
		StringBuffer ans = new StringBuffer(EXCEPTION_NODE + EXCEPTION_ENDNODE);
		ans.insert(ans.indexOf(EXCEPTION_ENDNODE), getMessage());
		return ans.toString();
	}
}
