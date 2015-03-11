package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name="medicine")
@NamedQueries({
@NamedQuery(name="Medicine.GetAll", query="SELECT m FROM Medicine m"),
@NamedQuery(name="Medicine.CountAll", query="SELECT count(m) FROM Medicine m")
})
public class Medicine extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 5540835858940264498L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String activeingredient;
	private String name;

	//GETTERS/SETTERS
	public void setId(Integer id) {	this.id = id;	}
	public void setActiveingredient(String activeingredient) {	this.activeingredient = activeingredient;	}
	public void setName(String name) {	this.name = name;	}

	public Integer getId() {	return this.id;	}
	public String getActiveingredient() {	return this.activeingredient;	}
	public String getName() {	return this.name;	}

	//INTERFACE
	@Override
	public String getXML() {
		return "";
	}
	
	@Override
	public String getUIFriendlyString() {
		return name+" - "+activeingredient;
	}
}
