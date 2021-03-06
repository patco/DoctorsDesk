package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name="patienthistory")
@NamedQueries({
@NamedQuery(name="PatientHistory.GetAll", query="SELECT p FROM PatientHistory p"),
@NamedQuery(name="PatientHistory.CountAll", query="SELECT count(p) FROM PatientHistory p")
})
public class PatientHistory extends DBEntity<Patient> implements Serializable {

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

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "patienthistory",fetch = FetchType.LAZY)
	private List<Activity> activities;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true, mappedBy = "patienthistory", fetch = FetchType.LAZY)
	private List<Prescription> prescriptions;

	public PatientHistory() {
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public void setActivities(List<Activity> activities) {
		if (activities == null)
			activities = new ArrayList<Activity>();
		else
			activities.clear();
		for (Activity activity : activities) {
			addActivity(activity);
		}
	}

	public void addActivity(Activity activity) {
		if (activities == null)
			activities = new ArrayList<Activity>();

		activity.setPatienthistory(this);
		activities.add(activity);
	}
	
	public void removeActivity(Activity a) {
		if (activities.contains(a))
			activities.remove(a);
	}


	public List<Prescription> getPrescriptions() {
		return this.prescriptions;
	}

	public void setPrescriptions(List<Prescription> prescriptions) {
		this.prescriptions = prescriptions;
	}
	
	public void  addPrescription(Prescription prescription){
		if (prescriptions == null)
			prescriptions = new ArrayList<Prescription>();

		prescription.setPatienthistory(this);
		prescriptions.add(prescription);
	}
	
	public void removePrescription(Prescription prescription){
		if (prescriptions.contains(prescription))
			prescriptions.remove(prescription);
	}
	
	
	@Override
	public Patient getId() {
		return patient;
	}

	public String getComments() {
		return this.comments;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public List<Activity> getActivities() {
		return this.activities;
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<patienthistory></patienthistory>");
		ans.insert(ans.indexOf("</patienthistory"), "<comments>" + comments+ "</comments>");
		ans.insert(ans.indexOf("</patienthistory"), "<startdate>" + startdate+ "</startdate>");
		ans.insert(ans.indexOf("</patienthistory"), "<enddate>" + enddate+ "</enddate>");
		ans.insert(ans.indexOf("</patienthistory"), "<activities>");
		for (Activity activity : activities) {
			ans.insert(ans.indexOf("</patienthistory"), activity.getXML());
		}
		ans.insert(ans.indexOf("</patienthistory"), "</activities>");
		return ans.toString();
	}


}
