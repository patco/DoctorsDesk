package com.patco.doctorsdesk.server.db.utils;

import java.math.BigDecimal;
import java.util.Date;

import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.LabDataHistory;
import com.patco.doctorsdesk.server.domain.entities.Medicalhistory;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PatientHistory;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

public class TestUtils {
	public Activity createDefaultActivity(Doctor d,PricelistItem p,Discount disc){
		Activity ac = new Activity();
		ac.setDescription(Activity.DEFAULT_ACTIVITY_IDENTIFIER_DESCR);
		
		ac.setDiscount(disc);
		ac.setEnddate(null);
		ac.setisOpen(true);
		ac.setPrice(BigDecimal.ZERO);
		ac.setPriceable(p);
		ac.setStartdate(new Date());
		return ac;
	}
	
	public Discount createDefaultDiscount(Doctor d){
		Discount disc = new Discount();
		disc.setDoctor(d);
		disc.setTitle("discount title");
		disc.setDiscount(new BigDecimal(10.0));
		disc.setDescription("some description...");
		d.addDiscount(disc);
		return disc;
	}
	
	public PricelistItem createDefaultPriceListItem(Doctor d){
		PricelistItem item = new PricelistItem();
		item.setDoctor(d);
		item.setDescription("some description...");
		item.setTitle("price title ");
		item.setPrice(new BigDecimal(112));
		return item;
	}
	
	public Medicalhistory createDefaultMedicalHistory(Patient p){
		Medicalhistory medhistory = new Medicalhistory();
		medhistory.setComments("Auto Generated");
		medhistory.setPatient(p);
		return medhistory;
	}
	
	public PatientHistory createDefaultPatientHistory(Patient p, Activity ac) {
		PatientHistory history = new PatientHistory();
		history.setComments("auto generated");
		history.setStartdate(new Date());
		history.setPatient(p);
		if (ac != null) {
			history.addActivity(ac);
		}
		return history;
	}
	
	public LabDataHistory createDefaultLabDataHistory(Patient p){
		LabDataHistory labdatahistory = new LabDataHistory();
		labdatahistory.setPatient(p);
		return labdatahistory;
	}

}
