package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
public class Patienthistory extends DBEntity implements Serializable {

	private static final long serialVersionUID = 6217283569761594041L;

	private String comments;

	@Temporal(TemporalType.TIMESTAMP)
	private Date enddate;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date startdate;
	
	@Id
	@OneToOne
	@JoinColumn(name = "patientid")
	private Patient patient;

	@Override
	public String getXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
