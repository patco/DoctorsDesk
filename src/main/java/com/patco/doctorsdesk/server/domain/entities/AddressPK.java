package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.InvalidAddressTypeException;

@Embeddable
public class AddressPK implements Serializable {

	private static final long serialVersionUID = -3319613554217181047L;
	
	private Integer id;
	private Integer adrstype;
	
    public AddressPK() {}
    
	public Integer getId() 			{	return this.id;	}
	public Integer getAdrstype() 	{	return this.adrstype;	}
	
	public void setId(Integer id) 	{	this.id = id;	}
	
	public void setAdrstype(Integer adrstype)  throws InvalidAddressTypeException {
		if (DoctorsDeskUtils.isAddressTypeValid(adrstype)) {
			this.adrstype = adrstype;	
	    	return;
		}
    	throw new InvalidAddressTypeException(adrstype);
   	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AddressPK)) {
			return false;
		}
		AddressPK castOther = (AddressPK)other;
		return 
			this.id.equals(castOther.id)
			&& this.adrstype.equals(castOther.adrstype);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.adrstype.hashCode();
		
		return hash;
    }
	
	

}
