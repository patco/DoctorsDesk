package com.patco.doctorsdesk.server.guice;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.patco.doctorsdesk.server.domain.dao.DoctorDao;
import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

public class PersistenceModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("com.patco.doctorsdesk"));
		bind(new TypeLiteral<GenericDAO<Doctor, Integer>>() {}).to(new TypeLiteral<DoctorDao>() {});
	}

}
