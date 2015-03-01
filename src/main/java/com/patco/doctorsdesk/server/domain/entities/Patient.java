package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
public class Patient extends DBEntity implements Serializable {

	private static final long serialVersionUID = 6797130500240757054L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String comments;
	
	@NotNull
	@Temporal( TemporalType.TIMESTAMP)
	private Date created;
	
	@NotNull
	@NotEmpty
	private String name;
	
	@NotNull
	@NotEmpty
	private String surname;
	
	
	
	
	@Override
	public String getXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
