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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name = "pricelist")
@NamedQueries({
@NamedQuery(name="PricelistItem.GetAll", query="SELECT p FROM PricelistItem p"),
@NamedQuery(name="PricelistItem.CountAll", query="SELECT count(p) FROM PricelistItem p"),
@NamedQuery(name="PricelistItem.CountPerDoctor", query="SELECT count(p) FROM PricelistItem p WHERE p.doctor =:doctor"),
@NamedQuery(name="PricelistItem.GetAllPerDoctor", query="SELECT p FROM PricelistItem p WHERE p.doctor =:doctor")
})
public class PricelistItem extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -7348816246853225500L;
	
	public static final String COUNT_PER_DOCTOR="PricelistItem.CountPerDoctor";
	public static final String GETALL_PER_DOCTOR="PricelistItem.GetAllPerDoctor";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true)
	private Integer id;

	@Column(length = 256)
	private String description;

	@NotNull
	@Column(updatable = false, precision = 131089)
	private BigDecimal price;

	@NotNull
	@Column(length = 80)
	private String title;

	// bi-directional many-to-one association to Doctor
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctorid", nullable = true, insertable = true, updatable = false)
	private Doctor doctor;

	public PricelistItem() {
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public BigDecimal getPrice() {
		return this.price;
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

	public void setPrice(BigDecimal price) {
		this.price = price;
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
		StringBuilder ans = new StringBuilder("<pricable></pricable>");
		ans.insert(ans.indexOf("</pricable"), "<title>" + title + "</title>");
		ans.insert(ans.indexOf("</pricable"), "<description>" + description
				+ "</description>");
		ans.insert(ans.indexOf("</pricable"), "<price>" + price + "</price>");
		return ans.toString();
	}
	
	

}
