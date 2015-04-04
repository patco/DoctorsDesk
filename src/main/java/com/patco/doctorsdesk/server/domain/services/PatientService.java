package com.patco.doctorsdesk.server.domain.services;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.AddressDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ContactInfoDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryEntryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Address;
import com.patco.doctorsdesk.server.domain.entities.Contactinfo;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Medicalhistory;
import com.patco.doctorsdesk.server.domain.entities.Medicalhistoryentry;
import com.patco.doctorsdesk.server.domain.entities.MedicalhistoryentryPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PatientHistory;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.util.exceptions.ActivityNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DiscountNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PatientExistsException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PricelistItemNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;

public class PatientService {

	private final PatientDAO patientdao;
	private final DoctorDAO doctordao;
	private final ActivityDAO activitydao;
	private final DiscountDAO discountdao;
	private final PricelistItemDAO pricelistitemdao;
	private final MedicalhistoryEntryDAO medicalhistoryEntrydao;
	private final ContactInfoDAO contactInfodao;
	private final AddressDAO addressdao;
	

	@Inject
	public PatientService(PatientDAO patientdao,
			              DoctorDAO doctordao,
			              DiscountDAO discountdao,
			              ActivityDAO activitydao,
			              PricelistItemDAO pricelistitemdao,
			              MedicalhistoryEntryDAO medicalhistoryEntrydao,
			              ContactInfoDAO contactInfodao,
			              AddressDAO addressdao) {
		this.patientdao = patientdao;
		this.doctordao = doctordao;
		this.discountdao = discountdao;
		this.activitydao = activitydao;
		this.pricelistitemdao = pricelistitemdao;
		this.medicalhistoryEntrydao=medicalhistoryEntrydao;
		this.contactInfodao = contactInfodao;
		this.addressdao = addressdao;
	}

	@Transactional(ignore=ConstraintViolationException.class)
	public Patient createPatient(int doctorid, String name, String surname)
			throws DoctorNotFoundException, PatientExistsException,
			ValidationException {
		Doctor doctor = doctordao.findOrFail(doctorid);

		// patient
		long created = new Date().getTime();
		Patient p = new Patient();
		p.setCreated(new Date(created));
		p.setName(name);
		p.setSurname(surname);

		Medicalhistory medhistory = new Medicalhistory();
		medhistory.setComments("");
		medhistory.setPatient(p);

		PatientHistory patientlhistory = new PatientHistory();
		patientlhistory.setComments("");
		patientlhistory.setStartdate(new Date());
		patientlhistory.setPatient(p);
		
		p.setDoctor(doctor);
		p.setMedicalhistory(medhistory);
		p.setPatientHistory(patientlhistory);
		doctor.addPatient(p);

		patientdao.insert(p);
		return p;
	}
	
	@Transactional
	public void deletePatient (int patientid) throws PatientNotFoundException {
		Patient p = patientdao.findOrFail(patientid);
		p.getDoctor().removePatient(p);
		patientdao.delete(p);
	}
	
	@Transactional(ignore=ConstraintViolationException.class)
	public Activity createActivity(int patientid, String description,
			                       Date start, Date end, 
			                       int plitemid, int discountid, BigDecimal price)
			throws PatientNotFoundException, 
			       DiscountNotFoundException,
			       PricelistItemNotFoundException {
		Patient p = patientdao.findOrFail(patientid);
		Discount d = discountdao.findOrFail(discountid);
		PricelistItem plitem = pricelistitemdao.findOrFail(plitemid);

		PatientHistory ph = p.getPatientHistory();
		Activity ac = new Activity();

		ac.setDescription(description);
		ac.setStartdate(start);
		ac.setisOpen((end == null) ? true : false);
		ac.setEnddate(end);
		ac.setPriceable(plitem);
		ac.setDiscount(d);
		ac.setPatienthistory(ph);
		ac.setPrice(price);
		ph.addActivity(ac);

		activitydao.insert(ac);

		return ac;
	}
	
	@Transactional
	public void deleteActivity(int activityid) throws ActivityNotFoundException {
		Activity act = activitydao.findOrFail(activityid);
		act.getPatienthistory().removeActivity(act);
		activitydao.delete(act);
	}
	
	@Transactional
	public void deletePatientActivities(int patientid) throws PatientNotFoundException, ActivityNotFoundException {
		Patient p = patientdao.findOrFail(patientid);
		for (Activity activity:activitydao.getPatientActivities(p)){
			activitydao.delete(activity);
		}
	}
	
	@Transactional
	public Medicalhistoryentry createMedicalEntry(int patientID,String comment) throws PatientNotFoundException {
		Patient p = patientdao.findOrFail(patientID);
		MedicalhistoryentryPK id = new MedicalhistoryentryPK();
		id.setAdded(new Date());
		id.setId(p.getMedicalhistory().getId().getId());
		Medicalhistoryentry entry = new Medicalhistoryentry();
		entry.setComments(comment);
		entry.setId(id);
		p.getMedicalhistory().addMedicalEntry(entry);
		entry.setMedicalhistory(p.getMedicalhistory());
		medicalhistoryEntrydao.insert(entry);
		patientdao.update(p);
		return entry;
	}

	@Transactional
	public void deleteMedicalEntry(Medicalhistoryentry entry)
			throws PatientNotFoundException {
		Patient p = patientdao.findOrFail(entry.getId().getId());
		p.getMedicalhistory().deleteMedicalEntry(entry);
		medicalhistoryEntrydao.delete(entry);
	}
	
	@Transactional
	public Contactinfo createContactinfo(Contactinfo info) throws PatientNotFoundException {
		Patient p = patientdao.findOrFail(info.getId().getId());
		p.addContactInfo(info);
		info.setPatient(p);
		contactInfodao.insert(info);
		return info;
	}

	@Transactional
	public Address createAddress(Address address) throws PatientNotFoundException {
		Patient p = patientdao.findOrFail(address.getId().getId());
		
		// Address object enforces valid id.addressType , no need to check...
		p.addAddress(address);
		addressdao.insert(address);
		
		return address;
	}

}
