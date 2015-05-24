package com.patco.doctorsdesk.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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
import com.patco.doctorsdesk.server.domain.dao.interfaces.LabDataEntryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.LabDataEntry;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.domain.services.PatientService;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class PatientServiceLabDataTest {
	@Inject
	DoctorService doctorService;

	@Inject
	PatientService patientService;

	@Inject
	DoctorDAO doctordao;

	@Inject
	PatientDAO patientdao;

	@Inject
	LabDataEntryDAO labdataentrydao;

	@Before
	public void setupTest() throws ValidationException, DoctorsDeskException {
		int docid = doctorService.createDoctor("Kostas", "Patakas", "kpatakas",
				"passwd").getId();
		patientService.createPatient(docid, "Oumpa", "Mpaloumpa");
		assertEquals(new Long(1), doctordao.countAll());
		assertEquals(new Long(1), patientdao.countAll());
	}

	@After
	public void cleanUp() throws PatientNotFoundException,DoctorNotFoundException {
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		if (d != null) {
			List<Patient> patients = patientdao.getDoctorsPatient(d);
			for (Patient patient : patients) {
				patientService.deletePatient(patient.getId());
			}
		}
		doctorService.deleteDoctor(d.getId());

		assertEquals(new Long(0), doctordao.countAll());
		assertEquals(new Long(0), patientdao.countAll());
		assertEquals(new Long(0), labdataentrydao.countAll());
	}
	
	@Test
	public void createLabData() throws PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2015, 5, 23);
		Date date = cal.getTime();
		HashMap<String, LabDataEntry> map= new HashMap<String, LabDataEntry>();
		map.put ("Ht",patientService.createLabEntry(p.getId(), 3.1245, "Ht",date));
		map.put ("Hb",patientService.createLabEntry(p.getId(), 4.1245, "Hb",date));
		map.put ("WBC",patientService.createLabEntry(p.getId(), 6.1245, "WBC",date));
		map.put ("PLT",patientService.createLabEntry(p.getId(), 2.1245, "PLT",date));
				
		
		assertEquals(new Long(4), labdataentrydao.countAll());
		assertEquals(new Long(4), labdataentrydao.countLabDataPerPatientAndDate(p, date));
		
		List<LabDataEntry> entries = labdataentrydao.getLabDataPerPatientAndDate(p, date);
		for (LabDataEntry entry: entries){
			LabDataEntry tocheck = map.get(entry.getId().getType());
			assertNotNull(tocheck);
			assertEquals(tocheck.getId().getType(), entry.getId().getType());
			assertEquals(tocheck.getId().getPatientId(), entry.getId().getPatientId());
			assertEquals(tocheck.getValue(), entry.getValue());
		}
		
		Date date1 = new Date();
	    patientService.createLabEntry(p.getId(), 3.1245, "Ht",date1);
		patientService.createLabEntry(p.getId(), 4.1245, "Hb",date1);
		
		assertEquals(new Long(2), labdataentrydao.countLabDataPerPatientAndDate(p, date1));
		
		assertEquals(new Long(6), labdataentrydao.countAll());
	}
	
	@Test(expected=PatientNotFoundException.class)
	public void createLabEntryNonExistingPatient() throws PatientNotFoundException{
		patientService.createLabEntry(-1, 3.1245, "Ht",new Date());
	}
	
	@Test
	public void deleteLabEntries() throws PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2015, 5, 23);
		Date date = cal.getTime();
		patientService.createLabEntry(p.getId(), 3.1245, "Ht",date);
		patientService.createLabEntry(p.getId(), 4.1245, "Hb",date);
		patientService.createLabEntry(p.getId(), 6.1245, "WBC",date);
		patientService.createLabEntry(p.getId(), 2.1245, "PLT",date);
		
		Date date1 = new Date();
	    patientService.createLabEntry(p.getId(), 10.1245, "Ht",date1);
		patientService.createLabEntry(p.getId(), 15.1245, "Hb",date1);
		LabDataEntry todelete = patientService.createLabEntry(p.getId(), 15.1245, "PLT",date1);
		
		assertEquals(new Long(7), labdataentrydao.countAll());
		
		patientService.deleteLabEntry(todelete);
		
		assertEquals(new Long(2), labdataentrydao.countLabDataPerPatientAndDate(p, date1));
		patientService.deleteLabEntriesPerDate(p.getId(), date1);
		
		assertEquals(new Long(4), labdataentrydao.countAll());
	}
	
	
	

}
