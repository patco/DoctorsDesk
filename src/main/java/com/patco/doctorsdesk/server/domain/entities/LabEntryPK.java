package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class LabEntryPK implements Serializable{

	private static final long serialVersionUID = -4176467679285334598L;
	
	private Integer patientid;
	
	@Temporal( TemporalType.TIMESTAMP)
	private Date added;
	
	private String type;

	public Integer getPatientId() {
		return patientid;
	}

	public void setPatientId(Integer id) {
		this.patientid = id;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LabEntryPK)) {
			return false;
		}
		LabEntryPK castOther = (LabEntryPK)other;
		return this.patientid.equals(castOther.patientid) && 
			   this.added.equals(castOther.added) && 
			   this.type.equals(castOther.type);
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.patientid.hashCode();
		hash = hash * prime + this.type.hashCode();
		hash = hash * prime + this.added.hashCode();
		
		return hash;
    }


    
    
	

}
