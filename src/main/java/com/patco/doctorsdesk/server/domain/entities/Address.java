package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;

@Entity
public class Address extends DBEntity implements Serializable{

	private static final long serialVersionUID = -5130522884016048277L;
	@EmbeddedId
	private AddressPK id;
	private String city;
	private String country;
	private Integer number;
	private String postalcode;
	private String street;
	
    @ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name="id", insertable=false, updatable=false)
	private Patient patient;
    
    public Address() {}

	public AddressPK getId() 	{	return this.id;	}
	public String getCity() 	{	return this.city;	}
	public String getCountry() 	{	return this.country;	}
	public Integer getNumber() 	{	return this.number;	}
	public String getStreet() 	{	return this.street;	}
	public Patient getPatient() {	return this.patient;	}
	public String getPostalcode() {	return this.postalcode;	}
	
	public void setPostalcode(String postalcode) {		this.postalcode = postalcode;	}
	public void setPatient(Patient patient) {	this.patient = patient;	}
	public void setStreet(String street) 	{	this.street = street;	}
	public void setNumber(Integer number) 	{	this.number = number;	}
	public void setCountry(String country) 	{	this.country = country;	}
	public void setCity(String city) 		{	this.city = city;	}
	public void setId(AddressPK id) 		{	this.id = id;	}
	
	@Override
	public String getXML() {
		StringBuilder ans= new StringBuilder("<address></address>");
		ans.insert(ans.indexOf("</address"), "<type>"+DoctorsDeskUtils.findAddressTypeDescr(getId().getAdrstype())+"</type>");
		ans.insert(ans.indexOf("</address"), "<street>"+street+"</street>");
		ans.insert(ans.indexOf("</address"), "<number>"+number+"</number>");
		ans.insert(ans.indexOf("</address"), "<country>"+country+"</country>");
		ans.insert(ans.indexOf("</address"), "<city>"+city+"</city>");
		ans.insert(ans.indexOf("</address"), "<pcode>"+postalcode+"</pcode>");
		return ans.toString();
	}
	
	public String getUIFriendlyString() {
		return number+","+street+" "+postalcode+"    - "+country+", "+city;
	}

}
