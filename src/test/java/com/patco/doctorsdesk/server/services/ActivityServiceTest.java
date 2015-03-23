package com.patco.doctorsdesk.server.services;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.domain.services.PatientService;
import com.patco.doctorsdesk.server.util.exceptions.DiscountNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PatientExistsException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PricelistItemNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;


@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class ActivityServiceTest {
	
	@Inject
	DoctorService doctorService;
	
	@Inject 
	PatientService patientService;
	
	@Inject 
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientdao;
	
	@Inject 
	ActivityDAO activitydao;
	
	@Before
	public void setupTest() throws ValidationException, DoctorsDeskException{
		 int docid = doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "passwd").getId();
		 patientService.createPatient(docid, "Oumpa","Mpaloumpa");
		 assertEquals(new Long(1), doctordao.countAll());
		 assertEquals(new Long(1), patientdao.countAll());
	}
	
	@After
	public void cleanUp() throws PatientNotFoundException, DoctorNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		if (d!=null){
			List<Patient> patients = patientdao.getDoctorsPatient(d);
			for(Patient patient:patients){
				patientService.deletePatient(patient.getId());
			}			
		}
		doctorService.deleteDoctor(d.getId());
		
		assertEquals(new Long(0), doctordao.countAll());
		assertEquals(new Long(0), patientdao.countAll());
		assertEquals(new Long(0), activitydao.countAll());
	}
	
	@Test
	public void createActivity() throws DoctorNotFoundException,
			PatientExistsException, ValidationException,
			PatientNotFoundException, DiscountNotFoundException,
			PricelistItemNotFoundException {
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		PricelistItem item = doctorService.createPricelistItem(d.getId(),
				"Price Item 1", "some price item", 10.0);
		Discount discount = doctorService.createDiscount(d.getId(),
				"Discount 1", "some discount", 10.0);

		for (int i = 0; i < 3; i++) {
			patientService.createActivity(p.getId(), "activity " + i,
					new Date(), new Date(System.currentTimeMillis() + 1000000),
					item.getId(), discount.getId(), null);
		}
		
		assertEquals(new Long(3), activitydao.countPatientActivities(p));
		
		List<Activity> activities = activitydao.getPatientActivities(p);
		for (int i=0;i<activities.size();i++){
			Activity act = activities.get(i);
			assertEquals("activity "+i, act.getDescription());
			assertEquals(false, act.isOpen());
			assertEquals(null, act.getPrice());
		}
	}
	
	@Test
	public void createActivityInvalidValues() throws PatientNotFoundException,
			DiscountNotFoundException, PricelistItemNotFoundException,
			DoctorNotFoundException, ValidationException {
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		PricelistItem item = doctorService.createPricelistItem(d.getId(),
				"Price Item 1", "some price item", 10.0);
		Discount discount = doctorService.createDiscount(d.getId(),
				"Discount 1", "some discount", 10.0);
		try {
			patientService.createActivity(p.getId(), null, null, null,
					item.getId(), discount.getId(), null);
		} catch (ValidationException e) {
			String msg = e.getMessage();
			assertEquals(true,msg.contains("Property->DESCRIPTION on Entity->ACTIVITY may not be null"));
			assertEquals(true,msg.contains("Property->STARTDATE on Entity->ACTIVITY may not be null"));
			//assertEquals(true,msg.contains("Property->ENDDATE on Entity->ACTIVITY may not be null"));
		}

	}
	

}
