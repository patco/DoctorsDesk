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
import com.patco.doctorsdesk.server.domain.dao.interfaces.AddressDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ContactInfoDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Address;
import com.patco.doctorsdesk.server.domain.entities.AddressPK;
import com.patco.doctorsdesk.server.domain.entities.Contactinfo;
import com.patco.doctorsdesk.server.domain.entities.ContactinfoPK;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.domain.services.PatientService;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidAddressTypeException;
import com.patco.doctorsdesk.server.util.exceptions.InvalidContactInfoTypeException;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class PatientAddressAndContactInfoTest {
	
	@Inject 
	PatientService patientService;
	
	@Inject 
	DoctorService doctorService;
	
	@Inject 
	DoctorDAO doctordao;
	
	@Inject 
	PatientDAO patientdao;
	
	@Inject 
	AddressDAO addressdao;
	
	@Inject 
	ContactInfoDAO contactinfodao;
	
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
		assertEquals(new Long(0), addressdao.countAll());
	}
	
	@Test
	public void createAddress() throws InvalidAddressTypeException, PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		for (int i=0;i<3;i++){
			Address a = new Address();
			AddressPK pk = new AddressPK();
			pk.setAdrstype(i);
			pk.setId(p.getId());
			a.setId(pk);
			a.setCity("City" +i);
			a.setNumber(i);
			a.setCountry("Country" +i);
			a.setPatient(p);
			a.setStreet("Street" +i);
			a.setPostalcode("14231");
			patientService.createAddress(a);
		} 
		
		assertEquals(new Long(3), addressdao.countAll());
		List<Address> addresses = addressdao.findAll();
		for (int i=0;i<addresses.size();i++){
			Address a = addresses.get(i);
			assertEquals("14231", a.getPostalcode());
			assertEquals("Country" +i, a.getCountry());
			assertEquals("Street" +i, a.getStreet());
		}
		
	}
	
	@Test(expected=PatientNotFoundException.class)
	public void createAdressInvalidPatient() throws InvalidAddressTypeException, PatientNotFoundException{
		Address a = new Address();
		AddressPK pk = new AddressPK();
		pk.setAdrstype(1);
		pk.setId(-1);
		a.setId(pk);
		a.setCity("City 1");
		a.setNumber(1);
		a.setCountry("Country");
		a.setStreet("Street");
		a.setPostalcode("14231");
		patientService.createAddress(a);
	}
	
	@Test(expected=InvalidAddressTypeException.class)
	public void createAddressInvalidAddressType() throws InvalidAddressTypeException, PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Address a = new Address();
		AddressPK pk = new AddressPK();
		pk.setAdrstype(-14);
		pk.setId(p.getId());
		a.setId(pk);
		a.setCity("City 1");
		a.setNumber(1);
		a.setCountry("Country 1");
		a.setPatient(p);
		a.setStreet("Street 1");
		a.setPostalcode("14231");
		patientService.createAddress(a);
	}
	
	
	@Test
	public void createContactInfo() throws InvalidContactInfoTypeException, PatientNotFoundException {
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		for (int i = 0; i < 5; i++) {
			Contactinfo info = new Contactinfo();
			ContactinfoPK pk = new ContactinfoPK();
			pk.setId(p.getId());
			pk.setInfotype(i);
			info.setId(pk);
			info.setInfo("Some info" +i);
			patientService.createContactinfo(info);
		}
		assertEquals(new Long(5),contactinfodao.countAll());
		List<Contactinfo> infos = contactinfodao.findAll();
		for (int i=0;i<infos.size();i++){
			Contactinfo info = infos.get(i);
			assertEquals("Some info" +i, info.getInfo());
		}

	}
	
	@Test(expected=PatientNotFoundException.class)
	public void createContactInfoInvalidPatient() throws InvalidContactInfoTypeException, PatientNotFoundException{
		Contactinfo info = new Contactinfo();
		ContactinfoPK pk = new ContactinfoPK();
		pk.setId(-1);
		pk.setInfotype(1);
		info.setId(pk);
		info.setInfo("Some info");
		patientService.createContactinfo(info);
	}
	
	@Test(expected=InvalidContactInfoTypeException.class)
	public void createContactInfoInvalidInfoType() throws InvalidContactInfoTypeException, PatientNotFoundException{
		Doctor d = doctordao.getDoctorByUserName("kpatakas");
		Patient p = patientdao.getDoctorsPatient(d).get(0);
		Contactinfo info = new Contactinfo();
		ContactinfoPK pk = new ContactinfoPK();
		pk.setId(p.getId());
		pk.setInfotype(-1);
		info.setId(pk);
		info.setInfo("Some info");
		patientService.createContactinfo(info);
	}
	
	
	
	
	
	

}
