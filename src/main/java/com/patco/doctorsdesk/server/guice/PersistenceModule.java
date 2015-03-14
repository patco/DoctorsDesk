package com.patco.doctorsdesk.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.patco.doctorsdesk.server.domain.dao.DiscountDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.DoctorDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.MedicineDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.PrescriptionDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.PricelistItemDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.UserPreferencesDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicineDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PrescriptionDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.UserPreferencesDAO;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("com.patco.doctorsdesk"));
		//bind(new TypeLiteral<GenericDAO<Doctor, Integer>>() {}).to(new TypeLiteral<DoctorDAOImpl>() {});
		bind(DoctorDAO.class).to(DoctorDAOImpl.class);
		bind(MedicineDAO.class).to(MedicineDAOImpl.class);
		bind(PrescriptionDAO.class).to(PrescriptionDAOImpl.class);
		bind(DiscountDAO.class).to(DiscountDAOImpl.class);
		bind(PricelistItemDAO.class).to(PricelistItemDAOImpl.class);
		bind(UserPreferencesDAO.class).to(UserPreferencesDAOImpl.class);
		
	}

}
