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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name = "pricelist")
public class PricelistItem extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -7348816246853225500L;

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
