package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class DoctorTest {

	@Inject
	DoctorDAO doctordao;

	@Test
	public void createDoctors() {
		try {
			doctordao.getEntityManager().getTransaction().begin();
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
		} finally {
			doctordao.getEntityManager().getTransaction().rollback();
		}
	}

	@Test
	public void deleteDoctors() {
		try {
			doctordao.getEntityManager().getTransaction().begin();
			for (int i = 0; i < 10; i++) {
				Doctor d = new Doctor();
				d.setName("DoctorName" + i);
				d.setSurname("DoctorSurName" + i);
				d.setPassword("DoctorPasswd" + i);
				d.setUsername("doctor" + i);
				doctordao.insert(d);
			}	
			assertEquals(new Long(10), doctordao.countAll());
		} finally {
			doctordao.getEntityManager().getTransaction().commit();
		}
		
		try {
			doctordao.getEntityManager().getTransaction().begin();
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
		} finally{
			doctordao.getEntityManager().getTransaction().commit();
		}
	}
	
	
	@Test
	public void updateDoctors(){
		try {
			doctordao.getEntityManager().getTransaction().begin();
			Doctor d = new Doctor();
			d.setName("Dimitris");
			d.setSurname("Patakas");
			d.setPassword("12345");
			d.setUsername("dpatakas");
			doctordao.insert(d);
		} finally {
			doctordao.getEntityManager().getTransaction().commit();
		}
		
		try {
			doctordao.getEntityManager().getTransaction().begin();
			Doctor doctor = doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			doctor.setName("Athanasia");
			doctor.setSurname("Pataka");
			doctor.setPassword("34567");
			doctor.setUsername("apataka");
			doctordao.update(doctor);
		} finally {
			doctordao.getEntityManager().getTransaction().commit();
		}
		
		try{
			doctordao.getEntityManager().getTransaction().begin();
			Doctor altered = doctordao.getDoctorByUserName("apataka");
			assertNotNull(altered);
			assertEquals("Athanasia", altered.getName());
			assertEquals("Pataka",altered.getSurname());
			assertEquals("34567",altered.getPassword());
			assertEquals("apataka",altered.getUsername());
			doctordao.delete(altered);
			assertEquals(new Long(0), doctordao.countAll());
		}finally{
			doctordao.getEntityManager().getTransaction().commit();
		}	
	}

}
