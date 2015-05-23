package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
public class LabDataHistory extends DBEntity<Patient> implements Serializable {

	private static final long serialVersionUID = 2605611435296170641L;

	@Id
	@OneToOne
	@JoinColumn(name = "id")
	private Patient patient;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "labdatahistory")
	private List<LabDataEntry> entries;

	@Override
	public Patient getId() {
		return patient;
	}
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public List<LabDataEntry> getEntries() {
		return entries;
	}

	public void addLabEntry(LabDataEntry entry) {
		if (entries == null)
			entries = new ArrayList<LabDataEntry>();

		entries.add(entry);
	}

	public void setEntries(Set<LabDataEntry> entries) {
		if (entries != null)
			entries.clear();

		for (LabDataEntry labDataEntry : entries) {
			addLabEntry(labDataEntry);
		}
	}

	public void deleteLabEntry(LabDataEntry entry) {
		if (entries.contains(entry))
			entries.remove(entry);
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder(
				"<labdatahistory></labdatahistory>");
		List<LabDataEntry> entries = getEntries();
		for (LabDataEntry entry : entries) {
			ans.insert(ans.indexOf("</labdatahistory"), entry.getXML());
		}
		return ans.toString();
	}



}
