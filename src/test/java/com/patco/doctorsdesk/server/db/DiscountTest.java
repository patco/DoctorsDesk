package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.db.utils.TestUtils;
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
	
	@Inject 
	TestUtils testUtils;
	
	@Before
	@Transactional
	public void createDependencies(){
		Doctor d= new Doctor();
		d.setName("Dimitris");
		d.setSurname("Patakas");
		d.setPassword("12345");
		d.setUsername("dpatakas");
		doctordao.insert(d);
		assertEquals(new Long(1),doctordao.countAll());
		assertEquals(new Long(0), discountdao.countAll());
	}
	
	@After
	@Transactional
	public void deleteDependencies(){
		if (doctordao.countAll()==1){
			Doctor d = doctordao.getDoctorByUserName("dpatakas");
			if (d!=null){
				doctordao.delete(d);			
			}			
		}
		assertEquals(new Long(0), discountdao.countAll());
		assertEquals(new Long(0),doctordao.countAll());
	}
	
	
	@Test
	@Transactional
	public void createAndCount(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
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
		
		Discount disc = new Discount();
		disc.setDoctor(d);
		disc.setDescription("some description...");
		disc.setTitle("discount title " + 11);
		disc.setDiscount(new BigDecimal(112));
		d.addDiscount(disc);

		assertEquals(new Long(11),discountdao.countDoctorDiscounts(d));		
	}
	
	@Test
	@Transactional
	public void update(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		Discount disc = new Discount();
		disc.setDoctor(d);
		disc.setDescription("some description...");
		disc.setTitle("discount title " + 11);
		disc.setDiscount(new BigDecimal(112));
		d.addDiscount(disc);
		doctordao.insert(d);
		
		List<Discount> discs = (List<Discount>) d.getDiscounts();
		for (Discount discount:discs){
			discount.setDescription("altered discription");
			discount.setTitle("altered title");
			discount.setDiscount(new BigDecimal(114));
			discountdao.update(discount);
		}
		
		
		for (Discount discount:d.getDiscounts()){
			assertEquals("altered discription", discount.getDescription());
			assertEquals("altered title", discount.getTitle());			
		}
		
	}
	
	@Test
	@Transactional
	public void delete(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
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
		
		List<Discount> discounts =discountdao.getDoctorsDiscount(d);
		for (Discount discount:discounts){
			discountdao.delete(discount);
		}
		
		assertEquals(new Long(0), discountdao.countDoctorDiscounts(d));
		
		Discount disc = new Discount();
		disc.setDoctor(d);
		disc.setTitle("discount title"+11);
		disc.setDiscount(new BigDecimal(10.0+11));
		disc.setDescription("some description...");
		d.addDiscount(disc);
		
		assertEquals(new Long(1), discountdao.countDoctorDiscounts(d));
		doctordao.delete(d);
		
		assertEquals(new Long(0), discountdao.countAll());
	}
}
