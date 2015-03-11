package com.patco.doctorsdesk.server.db;


import static org.junit.Assert.assertEquals;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;


@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class) 
public class DoctorTest {
	
	@Inject
	DoctorDAO doctordao;

	
	@Before
	public void setUp(){
		doctordao.getEntityManager().getTransaction().begin();
	}
	
	 @After
	 public void tearDown() throws Exception
	 {
		 doctordao.getEntityManager().getTransaction().rollback();
	 }
	
	@Test
	public void create(){
		assertEquals(new Long(0), doctordao.countAll());
		for (int i=0;i<10;i++){
			Doctor d = new Doctor();
			d.setName("DoctorName" +i);
			d.setSurname("DoctorSurName" +i);
			d.setPassword("DoctorPasswd" +i);
			d.setUsername("doctor" +i);
			doctordao.insert(d);
		}
		
		assertEquals(new Long(10), doctordao.countAll());
	}
	

}
