package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.db.utils.TestUtils;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientHistoryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.VisitDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.domain.entities.Visit;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;


@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class VisitTest {
	
	@Inject
	DoctorDAO doctordao;
	
	@Inject 
	PatientHistoryDAO patientHistoryDao;
	
	@Inject 
	PatientDAO patientDao;
	
	@Inject 
	MedicalhistoryDAO medicalHistoryDao;
	
	@Inject
	DiscountDAO discount;
	
	@Inject 
	PricelistItemDAO priceListDao;
	
	@Inject 
	ActivityDAO activityDao;
	
	@Inject
	VisitDAO visitDao;
	
	@Inject 
	TestUtils testUtils;
	
	private long start;
	
	@Before
	@Transactional
	public void setupTest() throws ValidationException{
		Doctor d= new Doctor();
		d.setName("Dimitris");
		d.setSurname("Patakas");
		d.setPassword("12345");
		d.setUsername("dpatakas");
		
		Discount disc = testUtils.createDefaultDiscount(d);
		d.addDiscount(disc);
		
		PricelistItem item = testUtils.createDefaultPriceListItem(d);
		d.addPricelistItem(item);
		doctordao.insert(d);
		
		Patient patient = new Patient();
		patient.setDoctor(d);
		patient.setName("Dummy");
		patient.setSurname("Patient");
		patient.setComments("comments for dummy");
		patient.setCreated(new Date());
		patient.setPatientHistory(testUtils.createDefaultPatientHistory(patient, null));
		patient.setMedicalhistory(testUtils.createDefaultMedicalHistory(patient));
		patient.setLabDataHistory(testUtils.createDefaultLabDataHistory(patient));
		d.addPatient(patient);
		patientDao.insert(patient);
		
		Activity activity = new Activity();
		activity.setDescription("Activity 1");
		
		start= System.currentTimeMillis();

		activity.setDiscount(disc);
		activity.setStartdate(new Date());
		activity.setEnddate(new Date(start + 1100000));
		activity.setisOpen(false);
		activity.setPrice(BigDecimal.ZERO);
		activity.setPriceable(item);
		activity.setPatienthistory(patient.getPatientHistory());
		patient.getPatientHistory().addActivity(activity);
		activityDao.insert(activity);
		
		assertEquals(new Long(1),doctordao.countAll());
		assertEquals(new Long(1),activityDao.countAll());
		
	}
	
	@After
	@Transactional
	public void cleanupTest(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		Patient p = patientDao.getDoctorsPatient(d).get(0);
		List<Activity> activities = activityDao.getPatientActivities(p);
		for (Activity activity:activities){
			activityDao.delete(activity);
		}
		patientDao.delete(p);
		doctordao.delete(d);
		assertEquals(new Long(0), doctordao.countAll());
		assertEquals(new Long(0), patientDao.countAll());
		assertEquals(new Long(0), activityDao.countAll());
		assertEquals(new Long(0), patientHistoryDao.countAll());
		assertEquals(new Long(0), medicalHistoryDao.countAll());
		assertEquals(new Long(0), visitDao.countAll());
	}
	
	
	@Test
	@Transactional
	public void createAndCount() throws ValidationException{		
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		Patient p = d.getPatientList().get(0);
		Activity act = activityDao.getPatientActivities(p).get(0);
		Visit v1 =createVisit(p, act, new Date(start+50000), new Date(start+60000) ,1);
		visitDao.insert(v1);
		Visit v2 =createVisit(p, act, new Date(start+80000), new Date(start+90000) ,1);
		visitDao.insert(v2);
		Visit v3 =createVisit(p, act, new Date(start+20000), null ,1);
		visitDao.insert(v3);
		
		List<Visit> visits = visitDao.getActivityVisits(act);
		for (Visit visit:visits){
			assertEquals(true, visit.getTitle().contains("Visit"));
			assertEquals(true, visit.getComments().contains("some comment"));
		}
		
		assertEquals(new Long(3), visitDao.countAll());
		assertEquals(new Long(3), visitDao.countActivityVisits(act));
	}
	
	@Test
	@Transactional
	public void delete() throws ValidationException{
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		Patient p = d.getPatientList().get(0);
		Activity act = activityDao.getPatientActivities(p).get(0);
		Visit v1 =createVisit(p, act, new Date(start+50000), new Date(start+60000) ,1);
		visitDao.insert(v1);
		Visit v2 =createVisit(p, act, new Date(start+80000), new Date(start+90000) ,1);
		visitDao.insert(v2);
		Visit v3 =createVisit(p, act, new Date(start+20000), null ,1);
		visitDao.insert(v3);
		
		assertEquals(3, act.getVisits().size());
		
		for (Visit visit:visitDao.getActivityVisits(act)){
			visitDao.delete(visit);
		}
		
		assertEquals(0, act.getVisits().size());
		assertEquals(new Long(0), visitDao.countActivityVisits(act));
		assertEquals(new Long(0), visitDao.countAll());
	}
	
	
	
	private Visit createVisit(Patient p,Activity act, Date start, Date end,int i) {
		Visit v = new Visit();
		v.setComments("some comment " +i);
		v.setVisitdate(start);
		if (end!=null)
		  v.setEnddate(end);
		v.setColor(2);
		v.setTitle("Visit "+i);
		v.setDeposit(BigDecimal.ZERO);
		v.setActivity(act);
		act.addVisit(v);
		
		return v;
	}
	
	

}
