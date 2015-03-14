package com.patco.doctorsdesk.server.db;


import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;


@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class) 
public class DoctorTest {
	
	@Inject
	DoctorDAO doctordao;

	@Inject  
	DiscountDAO discountdao;
	
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
	public void createDoctors(){
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
	
	@Test
	public void createAndDeleteDentistDiscount(){
		Doctor d = new Doctor();
		d.setName("DoctorName");
		d.setSurname("DoctorSurName");
		d.setPassword("DoctorPasswd");
		d.setUsername("doctor");
		
		Discount disc1 = new Discount();
		disc1.setDescription("disc1");
		disc1.setDiscount(new BigDecimal(10));
		disc1.setTitle("disc1");
		disc1.setDoctor(d);
		d.addDiscount(disc1);
		
		Discount disc2 = new Discount();
		disc2.setDescription("disc2");
		disc2.setDiscount(new BigDecimal(11));
		disc2.setTitle("disc2");
		disc2.setDoctor(d);
		d.addDiscount(disc2);
		
		Discount disc3 = new Discount();
		disc3.setDescription("disc3");
		disc3.setDiscount(new BigDecimal(10));
		disc3.setTitle("disc3");
		disc3.setDoctor(d);
		d.addDiscount(disc3);
		
		doctordao.insert(d);
		
		assertEquals(new Long(3), discountdao.countDoctorDiscounts(d));
		
		d.removeDiscount(disc3);
		
		doctordao.update(d);
		
		assertEquals(new Long(2), discountdao.countDoctorDiscounts(d));
		
	}
	
	@Test
	public void createAndDeleteDiscount(){
		Doctor d = new Doctor();
		d.setName("DoctorName");
		d.setSurname("DoctorSurName");
		d.setPassword("DoctorPasswd");
		d.setUsername("doctor");
		
		Discount disc1 = new Discount();
		disc1.setDescription("disc1");
		disc1.setDiscount(new BigDecimal(10));
		disc1.setTitle("disc1");
		disc1.setDoctor(d);
		d.addDiscount(disc1);
		
		Discount disc2 = new Discount();
		disc2.setDescription("disc2");
		disc2.setDiscount(new BigDecimal(11));
		disc2.setTitle("disc2");
		disc2.setDoctor(d);
		d.addDiscount(disc2);
		
		Discount disc3 = new Discount();
		disc3.setDescription("disc3");
		disc3.setDiscount(new BigDecimal(10));
		disc3.setTitle("disc3");
		disc3.setDoctor(d);
		d.addDiscount(disc3);
		
		doctordao.insert(d);
		
		discountdao.delete(disc1);
		
		assertEquals(new Long(2), discountdao.countDoctorDiscounts(d));
		
		assertEquals(new Long(2), discountdao.countAll());
		
		assertEquals(2,doctordao.find(d.getId()).getDiscounts().size());
		
		
		
	}
	

}
