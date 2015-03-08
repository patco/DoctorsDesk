package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;

@Entity
public class Contactinfo extends DBEntity<ContactinfoPK> implements Serializable {

	private static final long serialVersionUID = -6983189179488067227L;

	@EmbeddedId
	private ContactinfoPK id;
	
	private String info;
	
	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Patient patient;

	public Contactinfo() {
	}

	public ContactinfoPK getId() {
		return this.id;
	}

	public String getInfo() {
		return this.info;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setId(ContactinfoPK id) {
		this.id = id;
	}

	@Override
	public String getXML() {
		String type = "ct"
				+ DoctorsDeskUtils.findContactInfoTypeDescr(id.getInfotype());
		return "<" + type + " value='" + info + "'/>";
	}

}
