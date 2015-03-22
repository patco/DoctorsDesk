package com.patco.doctorsdesk.server.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.patco.doctorsdesk.server.db.utils.DatabaseModule;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;
import com.patco.doctorsdesk.server.domain.services.DoctorService;
import com.patco.doctorsdesk.server.util.exceptions.DiscountNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorExistsException;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.PricelistItemNotFoundException;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;
import com.patco.doctorsdesk.server.util.exceptions.base.DoctorsDeskException;

@RunWith(JukitoRunner.class)
@UseModules(DatabaseModule.class)
public class DoctorServiceTest {
	
	@Inject
	DoctorService doctorService;
	
	@Inject 
	DoctorDAO doctordao;
	
	@Inject 
	DiscountDAO discountDao;
	
	@Inject
	PricelistItemDAO priceListItemdao;
	
	@After
	public void cleanUp() throws DoctorNotFoundException{
		List<Doctor>  doctors = doctordao.findAll();
		for (Doctor doctor:doctors){
			doctorService.deleteDoctor(doctor.getId());
		}
		assertEquals(new Long(0), doctordao.countAll());
	}
	
	@Test
	public void createDoctorService() throws DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		for (int i = 0; i < 10; i++) {
			doctorService.createDoctor("Name_"+i, "surname", ""+i, "pwd"+i);
		}
		
		assertEquals(new Long(10), doctordao.countAll());
		
		List<Doctor> doctors = doctordao.findAll();
		for (int i = 0; i < doctors.size(); i++) {
			Doctor dn = doctors.get(i);
			assertEquals("surname", dn.getSurname());			
			assertEquals("pwd"+i, dn.getPassword());
			assertEquals(""+i, dn.getUsername());
			assertEquals("Name_"+i, dn.getName()); 
		}
		
	}
	
	@Test(expected=DoctorExistsException.class)
	public void createIdentical() throws DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
		assertEquals(new Long(1),doctordao.countAll());
		doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
	}
	
	@Test
	public void createDentistInvalidValues() {
		assertEquals(new Long(0), doctordao.countAll());
		try {
			doctorService.createDoctor(null, null, null, null);
		} catch (DoctorsDeskException| ValidationException ex) {
			assertTrue(ex instanceof ValidationException);
			ValidationException e = (ValidationException) ex;
			assertEquals(true,e.getMessage().contains("Property->NAME on Entity->DOCTOR may not be null"));
			assertEquals(true,e.getMessage().contains("Property->NAME on Entity->DOCTOR may not be empty"));
			assertEquals(true,e.getMessage().contains("Property->SURNAME on Entity->DOCTOR may not be null"));
			assertEquals(true,e.getMessage().contains("Property->SURNAME on Entity->DOCTOR may not be empty"));
			assertEquals(true,e.getMessage().contains("Property->USERNAME on Entity->DOCTOR may not be null"));
			assertEquals(true,e.getMessage().contains("Property->USERNAME on Entity->DOCTOR may not be empty"));
			assertEquals(true,e.getMessage().contains("Property->PASSWORD on Entity->DOCTOR may not be null"));
			assertEquals(true,e.getMessage().contains("Property->PASSWORD on Entity->DOCTOR may not be empty"));
		}
	}
	
	@Test
	public void deleteDoctors() throws ValidationException, DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		for (int i = 0; i < 3; i++) {
			doctorService.createDoctor("Name_"+i, "surname", ""+i, "pwd"+i);
		}
		
		assertEquals(new Long(3), doctordao.countAll());
		
		List<Doctor>  doctors = doctordao.findAll();
		for (Doctor doctor:doctors){
			doctorService.deleteDoctor(doctor.getId());
		}
		assertEquals(new Long(0), doctordao.countAll());
	}
	
	@Test(expected=DoctorNotFoundException.class)
	public void deleteNonExistingDoctor() throws DoctorNotFoundException{
		doctorService.deleteDoctorByUsername("nonexisting");
		doctorService.deleteDoctor(-1);
	}
	
	@Test
	public void createDiscount() throws ValidationException, DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		Doctor doctor =doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
		
		Discount disc1= doctorService.createDiscount(doctor.getId(), "Discount 1", "some discount 1", 10.0);
		Discount disc2= doctorService.createDiscount(doctor.getId(), "Discount 2", "some discount 2", 10.0);
		
		assertEquals(new Long(2),discountDao.countAll());
		
		assertEquals("Discount 1", discountDao.find(disc1.getId()).getTitle());
		assertEquals("Discount 2", discountDao.find(disc2.getId()).getTitle());
		assertEquals("some discount 1", discountDao.find(disc1.getId()).getDescription());
		assertEquals("some discount 2", discountDao.find(disc2.getId()).getDescription());
	}
	
	@Test
	public void deleteDiscount() throws ValidationException, DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		Doctor doctor =doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
		Discount disc1= doctorService.createDiscount(doctor.getId(), "Discount 1", "some discount 1", 10.0);
		
		doctorService.deleteDiscount(disc1.getId());
		
		assertEquals(new Long(0),discountDao.countAll());
		
	}
	
	@Test(expected=DiscountNotFoundException.class) 
	public void deleteNonExistingDiscount() throws DiscountNotFoundException{
		doctorService.deleteDiscount(-1);
	}
	
	@Test
	public void createPriceListItem() throws ValidationException, DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		Doctor doctor =doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
		PricelistItem p1 = doctorService.createPricelistItem(doctor.getId(), "Price Item 1", "some pricelist item 1", 10);
		PricelistItem p2 = doctorService.createPricelistItem(doctor.getId(), "Price Item 2", "some pricelist item 2", 10);
		
       assertEquals(new Long(2),priceListItemdao.countAll());
		
		assertEquals("Price Item 1", priceListItemdao.find(p1.getId()).getTitle());
		assertEquals("Price Item 2", priceListItemdao.find(p2.getId()).getTitle());
		assertEquals("some pricelist item 1", priceListItemdao.find(p1.getId()).getDescription());
		assertEquals("some pricelist item 2", priceListItemdao.find(p2.getId()).getDescription());
	}
	
	@Test
	public void deletePriceListItem() throws ValidationException, DoctorsDeskException{
		assertEquals(new Long(0),doctordao.countAll());
		Doctor doctor =doctorService.createDoctor("Kostas", "Patakas", "kpatakas", "pwddd");
		doctorService.createPricelistItem(doctor.getId(), "Price Item 1", "some pricelist item 1", 10);
		doctorService.createPricelistItem(doctor.getId(), "Price Item 1", "some pricelist item 1", 10);
		assertEquals(new Long(2),priceListItemdao.countAll());
		doctorService.deletePricelist(doctor.getId());
		assertEquals(new Long(0),priceListItemdao.countAll());
		
		PricelistItem p3 = doctorService.createPricelistItem(doctor.getId(), "Price Item 3", "some pricelist item 3", 10);
		assertEquals(new Long(1),priceListItemdao.countAll());
		doctorService.deletePricelistItem(p3.getId());
		assertEquals(new Long(0),priceListItemdao.countAll());
	}
	
	@Test(expected=PricelistItemNotFoundException.class)
	public void deleteNonExistingPriceListItem() throws PricelistItemNotFoundException{
		doctorService.deletePricelistItem(-1);
	}
	
	@Test(expected=DoctorNotFoundException.class)
	public void deletePriceListFromNonExistingDoctor() throws DoctorNotFoundException{
		doctorService.deletePricelist(-1);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
