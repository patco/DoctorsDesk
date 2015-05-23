package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PreRemove;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@NamedQueries({
@NamedQuery(name="LabDataEntry.GetAll", query="SELECT l FROM LabDataEntry l"),
@NamedQuery(name="LabDataEntry.CountAll", query="SELECT count(l) FROM LabDataEntry l"),
@NamedQuery(name="LabDataEntry.CountPerPatientAndDate", query="SELECT count(l) FROM LabDataEntry l WHERE l.id.patientid =:patientid AND l.id.added =:added"),
@NamedQuery(name="LabDataEntry.GetPerPatientAndDate", query="SELECT l FROM LabDataEntry l WHERE l.id.patientid =:patientid AND l.id.added =:added")})
public class LabDataEntry extends DBEntity<LabEntryPK> implements Serializable{

	private static final long serialVersionUID = -7704650633684807920L;
	
	public static final String COUNT_PER_PATIENT_AND_DATE="LabDataEntry.CountPerPatientAndDate";
	public static final String GET_PER_PATIENT_AND_DATE="LabDataEntry.GetPerPatientAndDate";
	
	
	@EmbeddedId
	private LabEntryPK id;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private LabDataHistory labdatahistory;
	
	private Double value;
	
	
	@Override
	public LabEntryPK getId() {
		return id;
	}


	public void setId(LabEntryPK id) {
		this.id = id;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
		

	public LabDataHistory getLabdatahistory() {
		return labdatahistory;
	}

	public void setLabdatahistory(LabDataHistory labdatahistory) {
		this.labdatahistory = labdatahistory;
	}
	
	
	
	@PreRemove
	void preRemove(){
		setLabdatahistory(null);
	}
	
	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<entry></entry>");
		ans.insert(ans.indexOf("</entry"), "<added>" + id.getAdded()
				+ "</added>");
		ans.insert(ans.indexOf("</entry"), "<type>" + id.getType()
				+ "</type>");
		ans.insert(ans.indexOf("</entry"), "<value>" + value
				+ "</value>");
		return ans.toString();
	}





}
