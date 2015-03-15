package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@NamedQueries({
@NamedQuery(name="Medicalhistory.GetAll", query="SELECT m FROM Medicalhistory m"),
@NamedQuery(name="Medicalhistory.CountAll", query="SELECT count(m) FROM Medicalhistory m")
})
public class Medicalhistory extends DBEntity<Patient> implements Serializable {

	private static final long serialVersionUID = 6648738534169907793L;

	@Id
	@OneToOne
	@JoinColumn(name = "id")
	private Patient patient;

	@Column(nullable = true, length = 1024)
	private String comments;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "medicalhistory")
	private Collection<Medicalhistoryentry> entries;

	public Patient getId() {
		return patient;
	}

	public String getComments() {
		return this.comments;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public Collection<Medicalhistoryentry> getEntries() {
		return this.entries;
	}

	public void addMedicalEntry(Medicalhistoryentry entry) {
		if (entries == null)
			entries = new ArrayList<Medicalhistoryentry>();

		entries.add(entry);
	}

	public void setEntries(Set<Medicalhistoryentry> entries) {
		if (entries != null)
			entries.clear();

		for (Medicalhistoryentry medicalhistoryentry : entries) {
			addMedicalEntry(medicalhistoryentry);
		}
	}

	public void deleteMedicalEntry(Medicalhistoryentry entry) {
		if (entries.contains(entry))
			entries.remove(entry);
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<medhistory></medhistory>");
		ans.insert(ans.indexOf("</medhistory"), "<comments>" + comments
				+ "</comments>");
		Collection<Medicalhistoryentry> entries = getEntries();
		for (Medicalhistoryentry entry : entries) {
			ans.insert(ans.indexOf("</medhistory"), entry.getXML());
		}
		return ans.toString();
	}

}
