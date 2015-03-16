package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class DiscountTest {
	
	@Inject
	DoctorDAO doctordao;
	
	@Inject
	DiscountDAO discountdao;
	
	@Test
	public void createAndCount(){
		try {
			discountdao.getEntityManager().getTransaction().begin();
			Doctor d= new Doctor();
			d.setName("Dimitris");
			d.setSurname("Patakas");
			d.setPassword("12345");
			d.setUsername("dpatakas");
			doctordao.insert(d);	
			assertEquals(new Long(1),doctordao.countAll());
			assertEquals(new Long(0), discountdao.countAll());
			
			for (int i = 0; i < 10; i++) {
				Discount disc = new Discount();
				disc.setDoctor(d);
				disc.setTitle("discount title"+i);
				disc.setDiscount(new BigDecimal(10.0+i));
				disc.setDescription("some description...");
				d.addDiscount(disc);
				discountdao.insert(disc);
			}
			assertEquals(new Long(10), discountdao.countAll());

		} finally {
			discountdao.getEntityManager().getTransaction().commit();
		}
		
		try {
			discountdao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			assertEquals(10, doctor.getDiscounts().size());
			assertEquals(new Long(10),discountdao.countDoctorDiscounts(doctor));
			
		} finally {
			discountdao.getEntityManager().getTransaction().commit();
		}
		
		try {
			discountdao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			assertEquals(10, doctor.getDiscounts().size());
			Discount disc = new Discount();
			disc.setDoctor(doctor);
			disc.setDescription("some description...");
			disc.setTitle("discount title " + 11);
			disc.setDiscount(new BigDecimal(112));
			doctor.addDiscount(disc);
		} finally {
			discountdao.getEntityManager().getTransaction().commit();
		}
		
		try {
			discountdao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertEquals(new Long(11),discountdao.countDoctorDiscounts(doctor));
			
			doctordao.delete(doctor);
			assertEquals(new Long(0), discountdao.countAll());
			
		} finally {
			discountdao.getEntityManager().getTransaction().commit();
		}
		
	}
	
	

}
