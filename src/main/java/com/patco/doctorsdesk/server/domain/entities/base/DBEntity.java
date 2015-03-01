package com.patco.doctorsdesk.server.domain.entities.base;

public abstract class DBEntity {
	public abstract String getXML() ;
	public String getUIFriendlyString() {
		return "";
	}
}
