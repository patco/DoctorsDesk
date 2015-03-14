package com.patco.doctorsdesk.server.domain.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.InvalidMedIntakeRouteException;

@Entity
@Table(name="prescriptionentries")
public class PrescriptionEntry extends DBEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -5203102117832830396L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer duration;
	private Integer durunit;
	private Integer frequency;
	private Integer frequnit;
	@OneToOne
	@JoinColumn(name="medicine")
	private Medicine medicine;
	
	private Integer route;

	//bi-directional many-to-one association to Prescription
    @ManyToOne
	@JoinColumn(name="prescriptionid")
	private Prescription prescription;

    //GETTERS/SETTERS
	public Integer getId() {	return this.id;	}
	public int getRoute() {	return this.route;	}
	public String getRouteDescription() {	return DoctorsDeskUtils.findMedIntakeRouteDescr(this.route);	}
	public String getFreqUnitDescription() {	return DoctorsDeskUtils.findPrescrRowTimeunitFreqDescr(this.frequnit);	}
	public String getDurUnitDescription() {	return DoctorsDeskUtils.findPrescrRowTimeunitDurDescr(this.durunit);	}
	public int getDuration() {	return this.duration;	}
	public int getDurunit() {	return this.durunit;	}
	public int getFrequency() {	return this.frequency;	}
	public int getFrequnit() {	return this.frequnit;	}
	public Medicine getMedicine() {	return this.medicine;	}
	public Prescription getPrescription() {	return this.prescription;	}

	public void setId(Integer id) {	this.id = id;	}
	public void setRoute(int route) throws InvalidMedIntakeRouteException {
		if (!DoctorsDeskUtils.isMedIntakeRouteValid(route))
			throw new InvalidMedIntakeRouteException(route);
		
		this.route = route;	
	}
	public void setDuration(int duration) {		this.duration = duration;	}
	public void setDurunit(int durunit) {		this.durunit = durunit;	}
	public void setFrequency(int frequency) {	this.frequency = frequency;	}
	public void setFrequnit(int frequnit) {		this.frequnit = frequnit;	}
	public void setMedicine(Medicine medicine) {	this.medicine = medicine;	}
	public void setPrescription(Prescription prescription) {	this.prescription = prescription;	}
	
	@PreRemove
	public void preRemove(){
	   setPrescription(null);	
	}

	
	@Override
	public String getXML() {
		return null;
	}

	@Override
	public String getUIFriendlyString() {
		return medicine.getUIFriendlyString()+" | "+getRouteDescription()
				+" ["+frequency+"("+frequnit+")] "
				+"["+duration+"("+durunit+")]";
	}

}
