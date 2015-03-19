package com.patco.doctorsdesk.server.db;

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
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class PatientTest {
	
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
	}
	
	
	@After
	@Transactional
	public void deleteDependencies(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		List<Activity> activities = activityDao.findAll();
		for (Activity activity:activities){
			activityDao.delete(activity);
		}
		doctordao.delete(d);
		assertEquals(new Long(0), patientDao.countAll());
		assertEquals(new Long(0), activityDao.countAll());
		assertEquals(new Long(0), patientHistoryDao.countAll());
		assertEquals(new Long(0), medicalHistoryDao.countAll());
	}
	
	@Test
	@Transactional
	public void createAndCount(){
		assertEquals(new Long(1),doctordao.countAll());
		assertEquals(new Long(0), patientDao.countAll());
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		PricelistItem p = d.getPriceList().get(0);
		Discount disc = d.getDiscounts().get(0);
		for (int i = 0; i < 10; i++) {
			Patient patient = new Patient();
			patient.setDoctor(d);
			patient.setName("Name"+i);
			patient.setSurname("Surname"+i);
			patient.setComments("comments"+i);
			patient.setCreated(new Date());
			patient.setPatientHistory(testUtils.createDefaultPatientHistory(patient, testUtils.createDefaultActivity(d,p,disc)));
			patient.setMedicalhistory(testUtils.createDefaultMedicalHistory(patient));
			d.addPatient(patient);
			patientDao.insert(patient);
		}
		
		assertEquals(10, d.getPatientList().size());
		assertEquals(new Long(10),patientDao.countPatientPerDoctor(d));
		
		Patient patient = new Patient();
		patient.setDoctor(d);
		patient.setName("Name"+11);
		patient.setSurname("Surname"+11);
		patient.setComments("comments"+11);
		patient.setCreated(new Date());
		patient.setPatientHistory(testUtils.createDefaultPatientHistory(patient, testUtils.createDefaultActivity(d,p,disc)));
		patient.setMedicalhistory(testUtils.createDefaultMedicalHistory(patient));
		d.addPatient(patient);
		
		assertEquals(new Long(11),patientDao.countPatientPerDoctor(d));
		
	}
	
	@Test
	@Transactional
	public void update(){
		Doctor d=doctordao.getDoctorByUserName("dpatakas");
		PricelistItem p = d.getPriceList().get(0);
		Discount disc = d.getDiscounts().get(0);
		Patient patient = new Patient();
		patient.setDoctor(d);
		patient.setName("Name");
		patient.setSurname("Surname");
		patient.setComments("comments");
		patient.setCreated(new Date());
		patient.setPatientHistory(testUtils.createDefaultPatientHistory(patient, testUtils.createDefaultActivity(d,p,disc)));
		patient.setMedicalhistory(testUtils.createDefaultMedicalHistory(patient));
		d.addPatient(patient);
		doctordao.insert(d);	
		
		List<Patient> patients = patientDao.getDoctorsPatient(d);
		for (Patient apatient:patients){
			apatient.setName("altered Name");
			apatient.setSurname("altered Surname");
			apatient.setComments("altered comments");
		}
		
		List<Patient> altpatients = patientDao.getDoctorsPatient(d);
		for (Patient apatient:altpatients){
			assertEquals("altered comments", apatient.getComments());
			assertEquals("altered Surname", apatient.getSurname());
			assertEquals("altered Name", apatient.getName());
		}
		
	}
	
	@Test
	@Transactional
	public void delete(){
		Doctor d= doctordao.getDoctorByUserName("dpatakas");
		assertEquals(new Long(0), patientDao.countAll());
		PricelistItem p = d.getPriceList().get(0);
		Discount disc = d.getDiscounts().get(0);
		for (int i = 0; i < 10; i++) {
			Patient patient = new Patient();
			patient.setDoctor(d);
			patient.setName("Name"+i);
			patient.setSurname("Surname"+i);
			patient.setComments("comments"+i);
			patient.setCreated(new Date());
			patient.setPatientHistory(testUtils.createDefaultPatientHistory(patient, testUtils.createDefaultActivity(d,p,disc)));
			patient.setMedicalhistory(testUtils.createDefaultMedicalHistory(patient));
			d.addPatient(patient);
			patientDao.insert(patient);
		}
		
		assertEquals(new Long(10), patientDao.countPatientPerDoctor(d));
		
		List<Patient> patients = patientDao.getDoctorsPatient(d);
		for (Patient patient:patients){
			patientDao.delete(patient);
		}
		assertEquals(new Long(0), patientDao.countPatientPerDoctor(d));
	}
	
	

}
