package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class MedicalhistoryentryPK implements Serializable {

	private static final long serialVersionUID = -7622998320706018278L;
	
	private Integer id;
	
    @Temporal( TemporalType.TIMESTAMP)
	private Date added;
    
    public MedicalhistoryentryPK() {    }
    
	public Integer getId() { return this.id;	}
	public java.util.Date getAdded() {	return this.added;	}
	
	public void setAdded(java.util.Date added) {	this.added = added;	}
	public void setId(Integer id) {	this.id = id;	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MedicalhistoryentryPK)) {
			return false;
		}
		MedicalhistoryentryPK castOther = (MedicalhistoryentryPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.added.equals(castOther.added);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.added.hashCode();
		
		return hash;
    }

}
