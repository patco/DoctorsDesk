package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.InvalidMedEntryAlertException;

@Entity
public class Medicalhistoryentry extends DBEntity implements Serializable {

	private static final long serialVersionUID = 7097652919235719709L;

	@EmbeddedId
	private MedicalhistoryentryPK id;
	private Integer alert;
	private String comments;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private Medicalhistory medicalhistory;

	public Medicalhistoryentry() {
	}

	public Integer getAlert() {
		return this.alert;
	}

	public String getComments() {
		return this.comments;
	}

	public MedicalhistoryentryPK getId() {
		return this.id;
	}

	public Medicalhistory getMedicalhistory() {
		return this.medicalhistory;
	}

	public void setMedicalhistory(Medicalhistory medicalhistory) {
		this.medicalhistory = medicalhistory;
	}

	public void setId(MedicalhistoryentryPK id) {
		this.id = id;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setAlert(Integer alert) throws InvalidMedEntryAlertException {
		if (DoctorsDeskUtils.isMedEntryAlertValid(alert)) {
			this.alert = alert;
			return;
		}
		throw new InvalidMedEntryAlertException(alert);
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<entry></entry>");
		ans.insert(ans.indexOf("</entry"), "<added>" + id.getAdded()
				+ "</added>");
		ans.insert(ans.indexOf("</entry"), "<comments>" + comments
				+ "</comments>");
		ans.insert(ans.indexOf("</entry"),
				"<alert>" + DoctorsDeskUtils.findMedEntryAlertDescr(alert)
						+ "</alert>");
		return ans.toString();
	}

}
