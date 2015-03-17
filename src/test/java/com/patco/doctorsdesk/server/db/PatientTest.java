package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.patco.doctorsdesk.server.domain.entities.Medicalhistory;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PatientHistory;
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
	
	@Before
	@Transactional
	public void createDependencies(){
		Doctor d= new Doctor();
		d.setName("Dimitris");
		d.setSurname("Patakas");
		d.setPassword("12345");
		d.setUsername("dpatakas");
		
		Discount disc = createDefaultDiscount(d);
		d.addDiscount(disc);
		
		PricelistItem item = createDefaultPriceListItem(d);
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
	public void createAndCount(){
		try {
			patientDao.getEntityManager().getTransaction().begin();
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
				patient.setPatientHistory(createDefaultPatientHistory(patient, createDefaultActivity(d,p,disc)));
				patient.setMedicalhistory(createDefaultMedicalHistory(patient));
				d.addPatient(patient);
				patientDao.insert(patient);
			}
			assertEquals(new Long(10), patientDao.countPatientPerDoctor(d));

		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			patientDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			assertEquals(10, doctor.getPatientList().size());
			assertEquals(new Long(10),patientDao.countPatientPerDoctor(doctor));
			
		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			patientDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			PricelistItem p = doctor.getPriceList().get(0);
			Discount disc = doctor.getDiscounts().get(0);
			assertNotNull(doctor);
			assertEquals(10, doctor.getPatientList().size());
			Patient patient = new Patient();
			patient.setDoctor(doctor);
			patient.setName("Name"+11);
			patient.setSurname("Surname"+11);
			patient.setComments("comments"+11);
			patient.setCreated(new Date());
			patient.setPatientHistory(createDefaultPatientHistory(patient, createDefaultActivity(doctor,p,disc)));
			patient.setMedicalhistory(createDefaultMedicalHistory(patient));
			doctor.addPatient(patient);
		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			patientDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertEquals(new Long(11),patientDao.countPatientPerDoctor(doctor));
		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
	}
	
	@Test
	public void update(){
		try{
			patientDao.getEntityManager().getTransaction().begin();
			Doctor d=doctordao.getDoctorByUserName("dpatakas");
			PricelistItem p = d.getPriceList().get(0);
			Discount disc = d.getDiscounts().get(0);
			Patient patient = new Patient();
			patient.setDoctor(d);
			patient.setName("Name");
			patient.setSurname("Surname");
			patient.setComments("comments");
			patient.setCreated(new Date());
			patient.setPatientHistory(createDefaultPatientHistory(patient, createDefaultActivity(d,p,disc)));
			patient.setMedicalhistory(createDefaultMedicalHistory(patient));
			d.addPatient(patient);
			doctordao.insert(d);	
		}finally{
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			patientDao.getEntityManager().getTransaction().begin();
			Doctor d = doctordao.getDoctorByUserName("dpatakas");
			
			List<Patient> patients = patientDao.getDoctorsPatient(d);
			
			for (Patient patient:patients){
				patient.setName("altered Name");
				patient.setSurname("altered Surname");
				patient.setComments("altered comments");
			}
			
		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		
		try {
			patientDao.getEntityManager().getTransaction().begin();
            Doctor d = doctordao.getDoctorByUserName("dpatakas");
			List<Patient> patients = patientDao.getDoctorsPatient(d);
			for (Patient patient:patients){
				assertEquals("altered comments", patient.getComments());
				assertEquals("altered Surname", patient.getSurname());
				assertEquals("altered Name", patient.getName());
			}
		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
	}
	
	@Test
	public void delete(){
		try {
			patientDao.getEntityManager().getTransaction().begin();
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
				patient.setPatientHistory(createDefaultPatientHistory(patient, createDefaultActivity(d,p,disc)));
				patient.setMedicalhistory(createDefaultMedicalHistory(patient));
				d.addPatient(patient);
				patientDao.insert(patient);
			}
			assertEquals(new Long(10), patientDao.countPatientPerDoctor(d));

		} finally {
			patientDao.getEntityManager().getTransaction().commit();
		}
		
		try{
			patientDao.getEntityManager().getTransaction().begin();
			Doctor d = doctordao.getDoctorByUserName("dpatakas");
			List<Patient> patients = patientDao.getDoctorsPatient(d);
			for (Patient patient:patients){
				patientDao.delete(patient);
			}
			assertEquals(new Long(0), patientDao.countPatientPerDoctor(d));
			
		}finally{
			patientDao.getEntityManager().getTransaction().commit();
		}
	}
	
	
	private Activity createDefaultActivity(Doctor d,PricelistItem p,Discount disc){
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
	
	private Discount createDefaultDiscount(Doctor d){
		Discount disc = new Discount();
		disc.setDoctor(d);
		disc.setTitle("discount title");
		disc.setDiscount(new BigDecimal(10.0));
		disc.setDescription("some description...");
		d.addDiscount(disc);
		return disc;
	}
	
	private PricelistItem createDefaultPriceListItem(Doctor d){
		PricelistItem item = new PricelistItem();
		item.setDoctor(d);
		item.setDescription("some description...");
		item.setTitle("price title ");
		item.setPrice(new BigDecimal(112));
		return item;
	}
	
	private Medicalhistory createDefaultMedicalHistory(Patient p){
		Medicalhistory medhistory = new Medicalhistory();
		medhistory.setComments("Auto Generated");
		medhistory.setPatient(p);
		return medhistory;
	}
	
	private PatientHistory createDefaultPatientHistory(Patient p, Activity ac){
		PatientHistory history = new PatientHistory();
		history.setComments("auto generated");
		history.setStartdate(new Date());
		history.setPatient(p);
		history.addActivity(ac);
		return history;
	}
}
