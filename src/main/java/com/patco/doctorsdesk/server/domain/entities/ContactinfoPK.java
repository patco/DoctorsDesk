package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.InvalidContactInfoTypeException;

@Embeddable
public class ContactinfoPK implements Serializable {

	private static final long serialVersionUID = -5787308408437828109L;

	private Integer id;
	private Integer infotype;

	public ContactinfoPK() {
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getInfotype() {
		return this.infotype;
	}

	public void setInfotype(Integer infotype)
			throws InvalidContactInfoTypeException {
		if (DoctorsDeskUtils.isContactInfoTypeValid(infotype)) {
			this.infotype = infotype;
			return;
		}
		throw new InvalidContactInfoTypeException(infotype);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContactinfoPK)) {
			return false;
		}
		ContactinfoPK castOther = (ContactinfoPK) other;
		return this.id.equals(castOther.id) && this.infotype.equals(castOther.infotype);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.infotype.hashCode();

		return hash;
	}

}
