package com.patco.doctorsdesk.server.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Collection;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
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
	
	@Test
	public void createAndCount(){
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor d= new Doctor();
			d.setName("Dimitris");
			d.setSurname("Patakas");
			d.setPassword("12345");
			d.setUsername("dpatakas");
			doctordao.insert(d);	
			assertEquals(new Long(1),doctordao.countAll());
			assertEquals(new Long(0), pricelistItemDao.countAll());
			
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

		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			assertEquals(10, doctor.getPriceList().size());
			assertEquals(new Long(10),pricelistItemDao.countDoctorsPriceListItems(doctor));
			
		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertNotNull(doctor);
			assertEquals(10, doctor.getPriceList().size());
			PricelistItem item = new PricelistItem();
			item.setDoctor(doctor);
			item.setDescription("some description...");
			item.setTitle("price title " + 11);
			item.setPrice(new BigDecimal(112));
			doctor.addPricelistItem(item);
		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor doctor=doctordao.getDoctorByUserName("dpatakas");
			assertEquals(new Long(11),pricelistItemDao.countDoctorsPriceListItems(doctor));
			
			doctordao.delete(doctor);
			assertEquals(new Long(0), pricelistItemDao.countAll());
			
		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
	}
	
	@Test
	public void update(){
		try{
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor d= new Doctor();
			d.setName("Dimitris");
			d.setSurname("Patakas");
			d.setPassword("12345");
			d.setUsername("dpatakas");
			PricelistItem item = new PricelistItem();
			item.setDoctor(d);
			item.setDescription("some description...");
			item.setTitle("price title " + 11);
			item.setPrice(new BigDecimal(112));
			d.addPricelistItem(item);
			doctordao.insert(d);	
		}finally{
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor d = doctordao.getDoctorByUserName("dpatakas");
			Collection<PricelistItem> items = d.getPriceList();
			
			for (PricelistItem item:items){
				item.setDescription("altered discription");
				item.setTitle("altered title");
				item.setPrice(new BigDecimal(114));
				pricelistItemDao.update(item);
			}
			
		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
            Doctor d = doctordao.getDoctorByUserName("dpatakas");
			Collection<PricelistItem> items = pricelistItemDao.getDoctorsPriceListItems(d);
			for (PricelistItem item:items){
				assertEquals("altered discription", item.getDescription());
				assertEquals("altered title", item.getTitle());
			}
			doctordao.delete(d);
		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
	}
	
	@Test
	public void delete(){
		try {
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor d= new Doctor();
			d.setName("Dimitris");
			d.setSurname("Patakas");
			d.setPassword("12345");
			d.setUsername("dpatakas");
			doctordao.insert(d);	
			assertEquals(new Long(1),doctordao.countAll());
			assertEquals(new Long(0), pricelistItemDao.countAll());
			
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

		} finally {
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
		
		try{
			pricelistItemDao.getEntityManager().getTransaction().begin();
			Doctor d = doctordao.getDoctorByUserName("dpatakas");
			Collection<PricelistItem> items =pricelistItemDao.getDoctorsPriceListItems(d);
			for (PricelistItem item:items){
				pricelistItemDao.delete(item);
			}
			assertEquals(new Long(0), pricelistItemDao.countDoctorsPriceListItems(d));
			
			PricelistItem item = new PricelistItem();
			item.setDoctor(d);
			item.setTitle("price title"+11);
			item.setPrice(new BigDecimal(10.0+11));
			item.setDescription("some description...");
			d.addPricelistItem(item);
			
			assertEquals(new Long(1), pricelistItemDao.countDoctorsPriceListItems(d));
			
			doctordao.delete(d);
			
			assertEquals(new Long(0), pricelistItemDao.countAll());
			
		}finally{
			pricelistItemDao.getEntityManager().getTransaction().commit();
		}
	}

}
