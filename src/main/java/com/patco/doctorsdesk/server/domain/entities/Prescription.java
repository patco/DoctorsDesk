package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name = "prescriptions")
@NamedQueries({
@NamedQuery(name="Prescription.GetAll", query="SELECT p FROM Prescription p"),
@NamedQuery(name="Prescription.CountAll", query="SELECT count(p) FROM Prescription p")
})
public class Prescription extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 2321514349568233099L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Timestamp created;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "prescription", fetch = FetchType.LAZY)
	private List<PrescriptionEntry> prescriptionentries;

	@ManyToOne
	@JoinColumn(name = "doctorid")
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "patienthistid")
	private PatientHistory patienthistory;

	public Integer getId() {
		return this.id;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public Timestamp getCreated() {
		return this.created;
	}

	public List<PrescriptionEntry> getPrescriptionEntries() {
		return this.prescriptionentries;
	}

	public PatientHistory getPatienthistory() {
		return this.patienthistory;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public void setPatienthistory(PatientHistory patienthistory) {
		this.patienthistory = patienthistory;
	}

	public void setPrescriptionrows(
			List<PrescriptionEntry> prescriptionrows) {
		if (this.prescriptionentries == null)
			this.prescriptionentries = new ArrayList<PrescriptionEntry>();

		for (PrescriptionEntry prescriptionentry : prescriptionentries) {
			addPrescriptionRow(prescriptionentry);
		}
	}

	public void addPrescriptionRow(PrescriptionEntry entry) {
		entry.setPrescription(this);
		prescriptionentries.add(entry);
	}
	
	@PreRemove
	public void preRemove(){
		setPatienthistory(null);
	}

	@Override
	public String getXML() {
		return null;
	}

}
