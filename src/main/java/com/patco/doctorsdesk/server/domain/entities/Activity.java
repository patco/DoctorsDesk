package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@NamedQueries({
@NamedQuery(name="Activity.GetAll", query="SELECT a FROM Activity a"),
@NamedQuery(name="Activity.CountAll", query="SELECT count(a) FROM Activity a"),
@NamedQuery(name="Activity.CountPerPatient", query="SELECT count(a) FROM Activity a WHERE a.patienthistory.patient =:patient"),
@NamedQuery(name="Activity.GetAllPerPatient", query="SELECT a FROM Activity a WHERE a.patienthistory.patient =:patient")
})
public class Activity extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -278053229570688972L;

	public static final String DEFAULT_ACTIVITY_IDENTIFIER_DESCR = "def act| cdent";
    public static final String COUNT_PER_PATIENT="Activity.CountPerPatient";
	public static final String GETALL_PER_PATIENT="Activity.GetAllPerPatient";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date enddate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date startdate;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patienthistid")
	private PatientHistory patienthistory;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "priceable")
	private PricelistItem priceable;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discount")
	private Discount discount;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity", fetch = FetchType.EAGER)
	private List<Visit> visits;

	@NotNull
	private boolean isopen = true;
	

	@Column(precision = 131089)
	private BigDecimal price;

	public Activity() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getEnddate() {
		return enddate;
	}

	public void setEnddate(java.util.Date enddate) {
		if (enddate != null && enddate.getTime() > startdate.getTime()) {
			this.enddate = enddate;
			isopen = false;
		}
	}

	public java.util.Date getStartdate() {
		return startdate;
	}

	public void setStartdate(java.util.Date startdate) {
		this.startdate = startdate;
	}

	public PatientHistory getPatienthistory() {
		return patienthistory;
	}

	public void setPatienthistory(PatientHistory patienthistory) {
		this.patienthistory = patienthistory;
	}

	public PricelistItem getPriceable() {
		return priceable;
	}

	public void setPriceable(PricelistItem priceable) {
		this.priceable = priceable;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}
	
	public void addVisit(Visit v) {
		if (visits == null)
			visits = new ArrayList<Visit>();

		visits.add(v);
	}
	
	public void removeVisit(Visit v) {
		if (visits.contains(v))
			visits.remove(v);
	}

	public boolean isOpen() {
		return isopen;
	}

	public void setisOpen(boolean isopen) {
		this.isopen = isopen;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	@Override
	public String getXML() {
		StringBuilder ans= new StringBuilder("<activity></activity>");
		ans.insert(ans.indexOf("</activity"), "<description>"+description+"</description>");
		ans.insert(ans.indexOf("</activity"), "<price>"+priceable.getPrice()+"</price>");
		ans.insert(ans.indexOf("</activity"), "<isOpen>"+isopen+"</isOpen>");
		ans.insert(ans.indexOf("</activity"), "<startdate>"+startdate+"</startdate>");
		ans.insert(ans.indexOf("</activity"), "<enddate>"+enddate+"</enddate>");
		ans.insert(ans.indexOf("</activity"), "<visits>");
		if (visits != null)
		for (Visit visit : visits) {
			ans.insert(ans.indexOf("</activity"), visit.getXML());
		}
		ans.insert(ans.indexOf("</activity"), "</visits>");
		return ans.toString();
	}

}
