package com.patco.doctorsdesk.server.util.exceptions.base;

public class DoctorsDeskException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994158992126156476L;
	public static final String EXCEPTION_NODE = "<exception>";
	public static final String EXCEPTION_ENDNODE = "</exception>";

	public DoctorsDeskException() {
		super();
	}

	public DoctorsDeskException(String message) {
		super(message);
	}

	public DoctorsDeskException(Throwable t) {
		super(t);
	}

	public DoctorsDeskException(String message, Throwable t) {
		super(message, t);
	}

	public String getXML() {
		StringBuffer ans = new StringBuffer(EXCEPTION_NODE + EXCEPTION_ENDNODE);
		ans.insert(ans.indexOf(EXCEPTION_ENDNODE), getMessage());
		return ans.toString();
	}
}
