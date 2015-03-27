package com.patco.doctorsdesk.server.services;

import static org.junit.Assert.assertEquals;

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
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryEntryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Medicalhistoryentry;
import com.patco.doctorsdesk.server.domain.entities.MedicalhistoryentryPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.domain.services.PatientService;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;


@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class MedicalHistoryServiceTest {
	
	
	@Inject
	DoctorService doctorService;
	
	@Inject 
	PatientService patientService;
	
	@Inject 
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientdao;
	
	
	@Inject 
	MedicalhistoryEntryDAO medicalhistorydao;
	
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
		assertEquals(new Long(0), medicalhistorydao.countAll());
	}
	
	@Test
	public void createMedicalHistoryEntry() throws PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		for(int i=0;i<4;i++){
		  patientService.createMedicalEntry(p.getId(), "some comment " +i);
		}
		
		assertEquals(new Long(4), medicalhistorydao.countAll());
		assertEquals(4, p.getMedicalhistory().getEntries().size());
		
		List<Medicalhistoryentry> entries = medicalhistorydao.findAll();
		for (int i=0;i<entries.size();i++){
			Medicalhistoryentry entry = entries.get(i);
			assertEquals("some comment " +i, entry.getComments());
		}
	}
	
	@Test
	public void deleteMedicalHistoryEntry() throws PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		for(int i=0;i<4;i++){
		  patientService.createMedicalEntry(p.getId(), "some comment " +i);
		}
		assertEquals(new Long(4), medicalhistorydao.countAll());
		
		for (Medicalhistoryentry entry:medicalhistorydao.findAll()){
			patientService.deleteMedicalEntry(entry);
		}
		
		assertEquals(new Long(0), medicalhistorydao.countAll());
	}
	
	@Test(expected=PatientNotFoundException.class)
	public void deleteMedicalEntryNonExistingPatient() throws PatientNotFoundException{
		Medicalhistoryentry entry = new Medicalhistoryentry();
		MedicalhistoryentryPK pk = new MedicalhistoryentryPK();
		pk.setId(-1);
		entry.setId(pk);
		patientService.deleteMedicalEntry(entry);
	}

}
