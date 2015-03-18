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
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PatientHistory;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class ActivityTest {
	
	@Inject
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientDao;
	
	@Inject 
	ActivityDAO activityDao;
	
	@Inject 
	PatientHistoryDAO patientHistoryDao;
	
	@Inject 
	MedicalhistoryDAO medicalHistoryDao;
	
	@Inject
	DiscountDAO discount;
	
	@Inject 
	PricelistItemDAO priceListDao;
	
	@Inject 
	TestUtils testUtils;
	
	@Before
	@Transactional
	public void createDependencies(){
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
		d.addPatient(patient);
		patientDao.insert(patient);
	}
	
	
	@After
	@Transactional
	public void deleteDependencies(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		Patient p = patientDao.getDoctorsPatient(d).get(0);
		List<Activity> activities = activityDao.getPatientActivities(p);
		for (Activity activity:activities){
			activityDao.delete(activity);
		}
		patientDao.delete(p);
		doctordao.delete(d);
		assertEquals(new Long(0), patientDao.countAll());
		assertEquals(new Long(0), activityDao.countAll());
		assertEquals(new Long(0), patientHistoryDao.countAll());
		assertEquals(new Long(0), medicalHistoryDao.countAll());
	}
	
	
	@Test
	@Transactional
	public void createAndCount() {
		assertEquals(new Long(1), doctordao.countAll());
		assertEquals(new Long(1), patientDao.countAll());

		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		PricelistItem p = d.getPriceList().get(0);
		Discount disc = d.getDiscounts().get(0);
		Patient patient = patientDao.getDoctorsPatient(d).get(0);
		PatientHistory ph = patient.getPatientHistory();
		for (int i = 0; i < 10; i++) {
			Activity activity = new Activity();
			activity.setDescription("Activity " + i);

			activity.setDiscount(disc);
			activity.setStartdate(new Date());
			activity.setEnddate(new Date(System.currentTimeMillis() + 1000000));
			activity.setisOpen(false);
			activity.setPrice(BigDecimal.ZERO);
			activity.setPriceable(p);
			activity.setPatienthistory(ph);
			ph.addActivity(activity);
			activityDao.insert(activity);

		}

		List<Activity> activities = activityDao.getPatientActivities(patient);
		for (Activity activity : activities) {
			assertEquals(false, activity.isOpen());
			assertEquals(BigDecimal.ZERO, activity.getPrice());
		}

	}
	
	@Test
	@Transactional
	public void update(){
		
		Doctor d = doctordao.getDoctorByUserName("dpatakas");	
		PricelistItem p = d.getPriceList().get(0);
		Discount disc = d.getDiscounts().get(0);
		Patient patient = patientDao.getDoctorsPatient(d).get(0);
		PatientHistory ph = patient.getPatientHistory();
		Activity activity = new Activity();
		activity.setDescription("Activity");
		
		activity.setDiscount(disc);
		activity.setStartdate(new Date());
		activity.setisOpen(true);
		activity.setPrice(BigDecimal.ZERO);
		activity.setPriceable(p);
		activity.setPatienthistory(ph);
		ph.addActivity(activity);
		activityDao.insert(activity);

		activity.setDescription("Altered Activity");
		activity.setEnddate(new Date(System.currentTimeMillis()+1000000) );
		activity.setisOpen(false);
		activityDao.update(activity);
		
		
		assertEquals(false, activityDao.getPatientActivities(patient).get(0).isOpen());
		assertEquals(BigDecimal.ZERO, activityDao.getPatientActivities(patient).get(0).getPrice());
		assertEquals("Altered Activity", activityDao.getPatientActivities(patient).get(0).getDescription());
		
	}
	
	

}
