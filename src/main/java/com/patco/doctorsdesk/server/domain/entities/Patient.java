package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;

@Entity
@NamedQueries({
@NamedQuery(name="Patient.GetAll", query="SELECT p FROM Patient p"),
@NamedQuery(name="Patient.CountAll", query="SELECT count(p) FROM Patient p"),
@NamedQuery(name="Patient.CountPerDoctor", query="SELECT count(p) FROM Patient p WHERE p.doctor =:doctor"),
@NamedQuery(name="Patient.GetAllPerDoctor", query="SELECT p FROM Patient p WHERE p.doctor =:doctor")
})
public class Patient extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 6797130500240757054L;
    public static final String COUNT_PER_DOCTOR="Patient.CountPerDoctor";
	public static final String GETALL_PER_DOCTOR="Patient.GetAllPerDoctor";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String comments;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String surname;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
	private Medicalhistory medicalhistory;

	@NotNull
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "patient")
	private PatientHistory patientHistory;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true,mappedBy = "patient", fetch = FetchType.LAZY)
	private Set<Address> addresses;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval=true,mappedBy = "patient", fetch = FetchType.LAZY)
	private Set<Contactinfo> contactinfo;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "doctorid")
	private Doctor doctor;

	public Patient() {
	}

	public Date getCreated() {
		return this.created;
	}

	public Integer getId() {
		return this.id;
	}

	public String getComments() {
		return this.comments;
	}

	public String getName() {
		return this.name;
	}

	public String getSurname() {
		return this.surname;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public Medicalhistory getMedicalhistory() {
		return this.medicalhistory;
	}

	public PatientHistory getPatientHistory() {
		return this.patientHistory;
	}

	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public Set<Contactinfo> getContactInfo() {
		return this.contactinfo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public void setMedicalhistory(Medicalhistory medicalhistory) {
		this.medicalhistory = medicalhistory;
	}
	
	public void setPatientHistory(PatientHistory patientHistory) 	{
		patientHistory.setPatient(this);
		this.patientHistory= patientHistory;	
	}
	public void setAddresses(Set<Address> adrs) {	
		if (addresses == null)
			addresses = new HashSet<Address>();
		else
			addresses.clear();
		
		for (Address address : adrs) {
			addAddress(address);
		}
	}
	public void addAddress(Address adrs) {	
		if (addresses == null)
			addresses = new HashSet<Address>();
		
		for (Address address : addresses) {
			if (address.getId().getAdrstype() == adrs.getId().getAdrstype()
					&& address.getCity().equals(adrs.getCity())
					&& address.getCountry().equals(adrs.getCountry())
					&& address.getNumber().equals(adrs.getNumber())
					&& address.getPostalcode().equals(adrs.getPostalcode())
					&& address.getStreet().equals(adrs.getStreet())) {
				return;
			}
		}
		addresses.add(adrs);	
	}

	public void setContactInfo(Set<Contactinfo> cnt) {	
		if (contactinfo == null)
			contactinfo = new HashSet<Contactinfo>();
		else
			contactinfo.clear();
		for (Contactinfo contactinfo : cnt) {
			addContactInfo(contactinfo);
		}
	}
	public void addContactInfo(Contactinfo cnt) {	
		if (contactinfo == null)
			contactinfo = new HashSet<Contactinfo>();
		
		cnt.setPatient(this);
		contactinfo.add(cnt);
	}
	
	public void updateContactInfo(Contactinfo cnt) {
		for (Contactinfo info : contactinfo) {
			if (info.getId().getInfotype().equals(cnt.getId().getInfotype())) { //replace
				info.setId(cnt.getId());
				info.setInfo(cnt.getInfo());
				return;
			}
		}
	}


	public Contactinfo getFax() { return getCinfo(DoctorsDeskUtils.ContactInfoType.FAX.getValue());	}
	public Contactinfo getEmail() { return getCinfo(DoctorsDeskUtils.ContactInfoType.EMAIL.getValue());	}
	public Contactinfo getHomeNumber() { return getCinfo(DoctorsDeskUtils.ContactInfoType.HOME.getValue());	}
	public Contactinfo getOfficeNumber() { return getCinfo(DoctorsDeskUtils.ContactInfoType.OFFICE.getValue());	}
	public Contactinfo getMobileNumber() { return getCinfo(DoctorsDeskUtils.ContactInfoType.MOBILE.getValue());	}

	public Address getOfficeAddress() {	return getAddress(DoctorsDeskUtils.AddressType.OFFICE.getValue()); }
	public Address getBillingAddress() {	return getAddress(DoctorsDeskUtils.AddressType.BILLING.getValue()); }
	public Address getHomeAddress() {	return getAddress(DoctorsDeskUtils.AddressType.HOME.getValue()); }
	
	private Contactinfo getCinfo(int type) {
		for (Contactinfo cinfo : contactinfo) {
			if (cinfo.getId().getInfotype() == type)
				return cinfo;
		}
		return null;
	}
	
	private Address getAddress(int type) {
		for (Address adrs : addresses) 
			if (adrs.getId().getAdrstype() == type)
				return adrs;
		return null;
		
	}
	
	@Override
	public String getXML() {
		StringBuilder ans= new StringBuilder("<patient></patient>");
		ans.insert(ans.indexOf("</patient"), "<id>"+id+"</id>");
		ans.insert(ans.indexOf("</patient"), "<name>"+name+"</name>");
		ans.insert(ans.indexOf("</patient"), "<surname>"+surname+"</surname>");
		ans.insert(ans.indexOf("</patient"), "<created>"+created+"</created>");
		ans.insert(ans.indexOf("</patient"), "<comments>"+comments+"</comments>");
		
		ans.insert(ans.indexOf("</patient"), "<contactinfo>");
		for (Contactinfo info: contactinfo) {
			ans.insert(ans.indexOf("</patient"), info.getXML());
		}
		for (Address address : addresses) {
			ans.insert(ans.indexOf("</patient"), address.getXML());
		}
		ans.insert(ans.indexOf("</patient"), "</contactinfo>");
		
		ans.insert(ans.indexOf("</patient"), medicalhistory.getXML());
		ans.insert(ans.indexOf("</patient"), "<mouth>");

		ans.insert(ans.indexOf("</patient"), "</mouth>");
		ans.insert(ans.indexOf("</patient"), patientHistory.getXML());
		return ans.toString();
	}


}
