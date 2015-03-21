package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class DoctorTest {

	@Inject
	DoctorDAO doctordao;
	
	@After
	public void cleanUp(){
		if (doctordao.countAll()>0){
			List<Doctor> doctors = doctordao.findAll();
			for (Doctor doctor:doctors){
				doctordao.delete(doctor);
			}
		}
	}

	@Test
	@Transactional
	public void createDoctors() throws ValidationException {
		assertEquals(new Long(0), doctordao.countAll());
		for (int i = 0; i < 10; i++) {
			Doctor d = new Doctor();
			d.setName("DoctorName" + i);
			d.setSurname("DoctorSurName" + i);
			d.setPassword("DoctorPasswd" + i);
			d.setUsername("doctor" + i);
			doctordao.insert(d);
		}
		assertEquals(new Long(10), doctordao.countAll());
	}

	@Test
	@Transactional
	public void deleteDoctors() throws ValidationException {
		for (int i = 0; i < 10; i++) {
			Doctor d = new Doctor();
			d.setName("DoctorName" + i);
			d.setSurname("DoctorSurName" + i);
			d.setPassword("DoctorPasswd" + i);
			d.setUsername("doctor" + i);
			doctordao.insert(d);
		}
		
		assertEquals(new Long(10), doctordao.countAll());
		
		Doctor d1=doctordao.getDoctorByUserName("doctor" + 0);
		assertNotNull(d1);
		doctordao.delete(d1);
		assertEquals(new Long(9),doctordao.countAll());
		
		for(int i=1;i<10;i++){
			Doctor d=doctordao.getDoctorByUserName("doctor" + i);
			assertNotNull(d);
			doctordao.delete(d);
			assertEquals(new Long(9-i), doctordao.countAll());
		}
	}
	
	
	@Test
	@Transactional
	public void updateDoctors() throws ValidationException{
		Doctor d = new Doctor();
		d.setName("Dimitris");
		d.setSurname("Patakas");
		d.setPassword("12345");
		d.setUsername("dpatakas");
		doctordao.insert(d);
		
		Doctor doctor = doctordao.getDoctorByUserName("dpatakas");
		assertNotNull(doctor);
		doctor.setName("Athanasia");
		doctor.setSurname("Pataka");
		doctor.setPassword("34567");
		doctor.setUsername("apataka");
		doctordao.update(doctor);
		
		Doctor altered = doctordao.getDoctorByUserName("apataka");
		assertNotNull(altered);
		assertEquals("Athanasia", altered.getName());
		assertEquals("Pataka",altered.getSurname());
		assertEquals("34567",altered.getPassword());
		assertEquals("apataka",altered.getUsername());
	}

}
