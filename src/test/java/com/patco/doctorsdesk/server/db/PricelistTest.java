package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collection;
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
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class PricelistTest {
	@Inject
	DoctorDAO doctordao;
	
	@Inject 
	PricelistItemDAO pricelistItemDao;
	
	@Before
	@Transactional
	public void setupTest(){
		Doctor d= new Doctor();
		d.setName("Dimitris");
		d.setSurname("Patakas");
		d.setPassword("12345");
		d.setUsername("dpatakas");
		doctordao.insert(d);
		
		assertEquals(new Long(1),doctordao.countAll());
		assertEquals(new Long(0), pricelistItemDao.countAll());
	}
	
	@After
	@Transactional
	public void cleanUp(){
		if (doctordao.countAll()>0){
			List<Doctor> doctors = doctordao.findAll();
			for (Doctor doctor:doctors){
				doctordao.delete(doctor);
			}
		}
		assertEquals(new Long(0),doctordao.countAll());
		assertEquals(new Long(0), pricelistItemDao.countAll());
	}
	
	@Test
	@Transactional
	public void createAndCount(){
		Doctor d=doctordao.getDoctorByUserName("dpatakas");
		for (int i = 0; i < 10; i++) {
			PricelistItem item = new PricelistItem();
			item.setDoctor(d);
			item.setTitle("price title"+i);
			item.setPrice(new BigDecimal(10.0+i));
			item.setDescription("some description...");
			d.addPricelistItem(item);
			pricelistItemDao.insert(item);
		}
		
		assertEquals(new Long(10), pricelistItemDao.countAll());
		assertEquals(new Long(10), pricelistItemDao.countDoctorsPriceListItems(d));
		assertEquals(10, d.getPriceList().size());		

		PricelistItem item = new PricelistItem();
		item.setDoctor(d);
		item.setDescription("some description...");
		item.setTitle("price title " + 11);
		item.setPrice(new BigDecimal(112));
		d.addPricelistItem(item);
		
		assertEquals(new Long(11),pricelistItemDao.countDoctorsPriceListItems(d));
		
	}
	
	@Test
	@Transactional
	public void update() {
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		PricelistItem item = new PricelistItem();
		item.setDoctor(d);
		item.setDescription("some description...");
		item.setTitle("price title " + 11);
		item.setPrice(new BigDecimal(112));
		d.addPricelistItem(item);
		doctordao.insert(d);

		List<PricelistItem> items = pricelistItemDao
				.getDoctorsPriceListItems(d);
		for (PricelistItem someitem : items) {
			someitem.setDescription("altered discription");
			someitem.setTitle("altered title");
			someitem.setPrice(new BigDecimal(114));
			pricelistItemDao.update(item);
		}

		for (PricelistItem someitem : d.getPriceList()) {
			assertEquals("altered discription", someitem.getDescription());
			assertEquals("altered title", someitem.getTitle());
		}

	}
	
	@Test
	@Transactional
	public void delete(){
		Doctor d = doctordao.getDoctorByUserName("dpatakas");
		for (int i = 0; i < 10; i++) {
			PricelistItem disc = new PricelistItem();
			disc.setDoctor(d);
			disc.setTitle("price title"+i);
			disc.setPrice(new BigDecimal(10.0+i));
			disc.setDescription("some description...");
			d.addPricelistItem(disc);
			pricelistItemDao.insert(disc);
		}
		assertEquals(new Long(10), pricelistItemDao.countAll());
		
		Collection<PricelistItem> items =pricelistItemDao.getDoctorsPriceListItems(d);
		for (PricelistItem item:items){
			pricelistItemDao.delete(item);
		}
		assertEquals(new Long(0), pricelistItemDao.countDoctorsPriceListItems(d));
	}

}
