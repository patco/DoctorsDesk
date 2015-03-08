package com.patco.doctorsdesk.server.db;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.patco.doctorsdesk.server.domain.dao.DoctorDao;
import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("com.patco.doctorsdesktest"));
		bind(JPAInitializer.class).asEagerSingleton();
		bind(new TypeLiteral<GenericDAO<Doctor, Integer>>() {}).to(new TypeLiteral<DoctorDao>() {});

	}
	
	@Singleton
	public static class JPAInitializer {

		@Inject
		public JPAInitializer(final PersistService service) {
			service.start();
		}
	}

}
