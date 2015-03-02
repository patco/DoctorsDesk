package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

@Entity
@Table(name="doctor")
public class Doctor extends DBEntity implements Serializable {

	private static final long serialVersionUID = -4725822810854224357L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true)
	private Integer id;

	@NotNull
	@NotEmpty
	@Column(length=80)
	private String name;
	
	@NotNull
	@NotEmpty
	@Column(length=16)
	private String password;
	
	@NotNull
	@NotEmpty
	@Column(length=80)
	private String surname;
	
	@NotNull
	@NotEmpty
	@Column(unique=true, length=16)
	private String username;
	
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="doctor", fetch=FetchType.LAZY)
	private Collection<PricelistItem> priceables;
	

	@Override
	public String getXML() {
		// TODO Auto-generated method stub
		return null;
	}

}
