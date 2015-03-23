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
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
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
public class PatientServiceTest {
	
	@Inject
	DoctorService doctorService;
	
	@Inject 
	PatientService patientService;
	
	@Inject 
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientdao;
	
	
	@Before
	public void setupTest() throws ValidationException, DoctorsDeskException{
		 doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "passwd").getId();
		 assertEquals(new Long(1), doctordao.countAll());
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
	}
	
	@Test
	public void createAndCountPatient() throws DoctorNotFoundException, PatientExistsException, ValidationException, DiscountNotFoundException, PricelistItemNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		for (int i=0;i<4;i++){
			patientService.createPatient(d.getId(), "Oumpa " + i, "Mpaloumpa " + i);	
		}
		
		List<Patient> patients = patientdao.findAll();
		for (int i = 0; i < patients.size(); i++) {
			Patient patient = patients.get(i);
			assertEquals("Oumpa " +i, patient.getName());
			assertEquals("Mpaloumpa "  +i, patient.getSurname());
		}
		
		assertEquals(new Long(4), patientdao.countPatientPerDoctor(d));
	}
	
	@Test(expected=DoctorNotFoundException.class)
    public void createPatientToNonExistingDoctor() throws DoctorNotFoundException, PatientExistsException, ValidationException{
		patientService.createPatient(-1, "Oumpa", "Mpaloumpa");
    }
	
	@Test
	public void createPatientInvalidValues() throws DoctorNotFoundException, PatientExistsException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		try {
			patientService.createPatient(d.getId(), null,null);
		} catch (ValidationException e) {
			String msg = e.getMessage();
			assertEquals(true, msg.contains("Property->NAME on Entity->PATIENT may not be null"));
			assertEquals(true, msg.contains("Property->NAME on Entity->PATIENT may not be empty"));
			assertEquals(true, msg.contains("Property->SURNAME on Entity->PATIENT may not be null"));
			assertEquals(true, msg.contains("Property->SURNAME on Entity->PATIENT may not be empty"));
		}
	}
	
	@Test
	public void deletePatient() throws DoctorNotFoundException, PatientExistsException, ValidationException, PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		for (int i=0;i<4;i++){
			patientService.createPatient(d.getId(), "Oumpa " + i, "Mpaloumpa " + i);	
		}
		assertEquals(new Long(4), patientdao.countPatientPerDoctor(d));
		List<Patient> patients = patientdao.getDoctorsPatient(d);
		for(Patient patient:patients){
			patientService.deletePatient(patient.getId());
		}
		assertEquals(new Long(0), patientdao.countPatientPerDoctor(d));
	}
	
	@Test(expected=PatientNotFoundException.class)
	public void deleteNonExistingPatient() throws PatientNotFoundException{
		patientService.deletePatient(-1);
	}
	
	
	
	
	
}
