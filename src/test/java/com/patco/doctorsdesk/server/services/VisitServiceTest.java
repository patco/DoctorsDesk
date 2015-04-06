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
import com.patco.doctorsdesk.server.domain.dao.interfaces.VisitDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.domain.services.PatientService;
import com.patco.doctorsdesk.server.util.exceptions.ActivityNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DiscountNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PricelistItemNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class VisitServiceTest {

	@Inject
	PatientService patientService;
	
	@Inject
	DoctorService doctorService;
	
	@Inject
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientdao;
	
	@Inject 
	ActivityDAO activitydao;
	
	@Inject 
	VisitDAO visitdao;
	
	private long start;
	private PricelistItem item;
	private Discount discount;
	
	@Before
	public void setupTest() throws ValidationException, DoctorsDeskException {
		int docid = doctorService.createDoctor("Kostas", "Patakas", "kpatakas",
				"passwd").getId();
		Patient p =patientService.createPatient(docid, "Oumpa", "Mpaloumpa");

		item = doctorService.createPricelistItem(docid,"Price Item 1", "some price item", 10.0);
		discount = doctorService.createDiscount(docid,"Discount 1", "some discount", 10.0);
		start= System.currentTimeMillis();
		patientService.createActivity(p.getId(), "activity 1",new Date(start), new Date(start + 1000000),item.getId(), discount.getId(), null);
		assertEquals(new Long(1), doctordao.countAll());
		assertEquals(new Long(1), patientdao.countAll());
		assertEquals(new Long(1), activitydao.countAll());
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
	public void createVisit() throws ActivityNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Activity act= p.getPatientHistory().getActivities().get(0);
		patientService.createVisit(act.getId(), "some Comment 1", "Visit 1", new Date(start+50000), new Date(start+60000), 0, 1);
		patientService.createVisit(act.getId(), "some Comment 2", "Visit 2", new Date(start+80000), new Date(start+90000), 0, 1);
		patientService.createVisit(act.getId(), "some Comment 3", "Visit 3", new Date(start+20000), new Date(start+30000), 0, 1);

		assertEquals(new Long(3), visitdao.countAll());
	}
	
	@Test(expected=ActivityNotFoundException.class)
	public void createVisitInvalidActivity() throws ActivityNotFoundException{
		patientService.createVisit(-1, "some Comment 1", "Visit 1", null, new Date(start+60000), 0, 1);
	}
	
	@Test(expected=ValidationException.class)
	public void createVisitNullStartDate() throws ActivityNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Activity act= p.getPatientHistory().getActivities().get(0);
		patientService.createVisit(act.getId(), "some Comment 1", "Visit 1", null, new Date(start+60000), 0, 1);
	}
	
	@Test(expected=ValidationException.class)
	public void createVisitEndDateLessThanStart() throws ActivityNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Activity act= p.getPatientHistory().getActivities().get(0);
		patientService.createVisit(act.getId(), "some Comment 1", "Visit 1", new Date(start+70000), new Date(start+60000), 0, 1);
		
	}
	
	@Test(expected=ValidationException.class)
	public void createVisitStartDateLessThanActivityStartDate() throws PatientNotFoundException, DiscountNotFoundException, PricelistItemNotFoundException, ActivityNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Activity act=patientService.createActivity(p.getId(), "activity 2",new Date(start), null,item.getId(), discount.getId(), null);
		patientService.createVisit(act.getId(), "some Comment 1", "Visit 1", new Date(start-50000), new Date(start+60000), 0, 1);
	}
	
	@Test(expected=ValidationException.class)
	public void createVisitDatesNotBetweenActivityDates() throws PatientNotFoundException, DiscountNotFoundException, PricelistItemNotFoundException, ActivityNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Activity act=patientService.createActivity(p.getId(), "activity 3",new Date(start), new Date(start + 1000000),item.getId(), discount.getId(), null);
		patientService.createVisit(act.getId(), "some Comment 1", "Visit 1", new Date(start + 50000), new Date(start+1000001), 0, 1);
	}
	
}
