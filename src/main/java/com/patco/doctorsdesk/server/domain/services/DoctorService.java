package com.patco.doctorsdesk.server.domain.services;

import java.math.BigDecimal;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.domain.entities.UserPreferences;
import com.patco.doctorsdesk.server.util.exceptions.DoctorExistsException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PricelistItemNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

public class DoctorService {
	
	
	private final DoctorDAO doctordao;
	private final DiscountDAO discountdao;
	private final PricelistItemDAO pricelistItemdao;
	
	@Inject
	public DoctorService(DoctorDAO doctordao,
			             DiscountDAO discountdao,
			             PricelistItemDAO pricelistItemdao) {
		this.doctordao=doctordao;
		this.discountdao = discountdao;
		this.pricelistItemdao=pricelistItemdao;
	}
	
	@Transactional
	public Doctor createDentist(String name, String surname, 
			                    String username,String password) throws DoctorsDeskException {
		if (doctordao.getDoctorByUserName(username) != null)
			throw new DoctorExistsException(username);

		Doctor d = new Doctor();
		d.setName(name);
		d.setSurname(surname);
		d.setUsername(username);
		d.setPassword(password);

		UserPreferences prefs = new UserPreferences();
		prefs.setDailyreports(UserPreferences.DEFAULT_USER_DAILYREPORTS);
		prefs.setEmailcontent(UserPreferences.DEFAULT_USER_EMAILCONTENT);
		prefs.setEmailnotification(UserPreferences.DEFAULT_USER_EMAILNOTIFICATIONS);
		prefs.setEventTitleFormatType(UserPreferences.DEFAULT_USER_EVTITLEFORMAT);
		prefs.setTheme(UserPreferences.DEFAULT_USER_THEME);
		prefs.setSchedulerMaxHour(UserPreferences.DEFAULT_USER_SCHEDMAXHR);
		prefs.setSchedulerMinHour(UserPreferences.DEFAULT_USER_SCHEDMINHR);
		prefs.setSchedulerStartHour(UserPreferences.DEFAULT_USER_SCHEDSTARTHR);
		prefs.setSchedulerSlotMins(UserPreferences.DEFAULT_USER_SCHEDSLOTMINS);
		prefs.setPrescriptionHeader(UserPreferences.DEFAULT_USER_PRESCRIPTIONHEADER);
		prefs.setReportemail(UserPreferences.DEFAULT_USER_REPORTEMAIL);
		prefs.setDoctor(d);

		return d;
	}
	
	@Transactional
    public void deleteDentistByUsername(String username) throws DoctorNotFoundException {
    	Doctor d;
    	if ((d =doctordao.getDoctorByUserName(username)) == null)
			throw new DoctorNotFoundException(username);
    	
    	doctordao.delete(d);
    }
	
	//DISCOUNTS
	@Transactional
	public Discount createDiscount(int doctorid, String title, String description, double value) 
											throws DoctorNotFoundException, ValidationException {
		Doctor doctor = findDoctor(doctorid);
		
		if (doctor==null) {
			throw new DoctorNotFoundException(doctorid);
		}
		
		Discount d = new Discount();
		if (description == null)
			d.setDescription("");
		else
			d.setDescription(description);
		d.setTitle(title);
		d.setDiscount(BigDecimal.valueOf(value));
		d.setDoctor(doctor);
		doctor.addDiscount(d);
		discountdao.insert(d);
		return d;
	}
	
	
    //PRICABLES
	@Transactional
    public PricelistItem createPricelistItem(int doctorid, String title, String description, double value) 
														throws DoctorNotFoundException, ValidationException {
    	Doctor doctor = findDoctor(doctorid);
		
		PricelistItem item = new PricelistItem();
		if (description == null)
			item.setDescription("");
		else
			item.setDescription(description);
		item.setTitle(title);
		item.setPrice(BigDecimal.valueOf(value));
		item.setDoctor(doctor);
		doctor.addPricelistItem(item);

		pricelistItemdao.insert(item);
		return item;
	}
	
	@Transactional
	public void deletePricelistItem(int id) throws PricelistItemNotFoundException {
		PricelistItem item = pricelistItemdao.find(id);
		if (item==null)
			throw new PricelistItemNotFoundException(id);
		item.getDoctor().removePricelistItem(item);
		pricelistItemdao.delete(item);
		
	}

	@Transactional
	public void deletePricelist (int dentistid) throws DoctorNotFoundException {
		Doctor d = findDoctor(dentistid);
		for (PricelistItem item : d.getPriceList()) {
			pricelistItemdao.delete(item);
		}
	}
	
    
	private Doctor findDoctor(int doctorid) throws DoctorNotFoundException{
		Doctor doctor = doctordao.find(doctorid);
		if (doctor == null) {
			throw new DoctorNotFoundException(doctorid);
		}
		return doctor;
	}
	
	

}
