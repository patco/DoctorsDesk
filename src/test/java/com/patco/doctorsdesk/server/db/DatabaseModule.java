package com.patco.doctorsdesk.server.db;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.patco.doctorsdesk.server.domain.dao.DiscountDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.DoctorDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.MedicineDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicineDAO;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("com.patco.doctorsdesktest"));
		bind(JPAInitializer.class).asEagerSingleton();
		//bind(new TypeLiteral<GenericDAO<Doctor, Integer>>() {}).to(new TypeLiteral<DoctorDAOImpl>() {});
		bind(DoctorDAO.class).to(DoctorDAOImpl.class);
		bind(MedicineDAO.class).to(MedicineDAOImpl.class);
		bind(DiscountDAO.class).to(DiscountDAOImpl.class);

	}
	
	@Singleton
	public static class JPAInitializer {

		@Inject
		public JPAInitializer(final PersistService service) {
			service.start();
		}
	}

}
