package com.patco.doctorsdesk.server.domain.entities.base;

public abstract class DBEntity<P> {
	public abstract String getXML() ;
	public String getUIFriendlyString() {
		return "";
	}
	
	public abstract P getId();
}
