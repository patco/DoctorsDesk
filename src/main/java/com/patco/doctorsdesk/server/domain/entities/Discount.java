package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;

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
import javax.persistence.PreRemove;
import javax.validation.constraints.NotNull;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@NamedQueries({
@NamedQuery(name="Discount.GetAll", query="SELECT d FROM Discount d"),
@NamedQuery(name="Discount.CountAll", query="SELECT count(d) FROM Discount d"),
@NamedQuery(name="Discount.CountPerDoctor", query="SELECT count(d) FROM Discount d WHERE d.doctor =:doctor"),
@NamedQuery(name="Discount.GetAllPerDoctor", query="SELECT d FROM Discount d WHERE d.doctor =:doctor")
})
public class Discount extends DBEntity<Integer> implements Serializable {
	
	private static final long serialVersionUID = 4216505498085131594L;
	
	public static final String COUNT_PER_DOCTOR="Discount.CountPerDoctor";
	
	public static final String GETALL_PER_DOCTOR="Discount.GetAllPerDoctor";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	@Column(length = 256)
	private String description;

	@NotNull
	@Column(length = 80)
	private String title;

	@NotNull
	@Column(updatable = false, precision = 131089)
	private BigDecimal discount;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctorid", insertable = true, updatable = false)
	private Doctor doctor;

	public Discount() {
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public String getDescription() {
		return this.description;
	}

	public Doctor getDoctor() {
		return this.doctor;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	

	@PreRemove
	public void preRemove(){
	   setDoctor(null);	
	}

	@Override
	public String getXML() {
		StringBuilder ans = new StringBuilder("<discount></discount>");
		ans.insert(ans.indexOf("</discount"), "<title>" + title + "</title>");
		ans.insert(ans.indexOf("</discount"), "<description>" + description+ "</description>");
		ans.insert(ans.indexOf("</discount"), "<value>" + discount + "</value>");
		return ans.toString();
	}

}
