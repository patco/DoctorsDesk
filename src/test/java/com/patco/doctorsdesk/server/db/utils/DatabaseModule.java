package com.patco.doctorsdesk.server.db.utils;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.patco.doctorsdesk.server.domain.dao.ActivityDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.AddressDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.ContactInfoDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.DiscountDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.DoctorDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.MedicalhistoryDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.MedicalhistoryEntryDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.MedicineDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.PatientDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.PrescriptionDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.PricelistItemDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.UserPreferencesDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.VisitDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.AddressDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ContactInfoDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryEntryDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicineDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PrescriptionDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.UserPreferencesDAO;
import com.patco.doctorsdesk.server.domain.dao.interfaces.VisitDAO;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new JpaPersistModule("com.patco.doctorsdesktest"));
		bind(JPAInitializer.class).asEagerSingleton();
		//bind(new TypeLiteral<GenericDAO<Doctor, Integer>>() {}).to(new TypeLiteral<DoctorDAOImpl>() {});
		bind(DoctorDAO.class).to(DoctorDAOImpl.class);
		bind(MedicineDAO.class).to(MedicineDAOImpl.class);
		bind(PrescriptionDAO.class).to(PrescriptionDAOImpl.class);
		bind(DiscountDAO.class).to(DiscountDAOImpl.class);
		bind(PricelistItemDAO.class).to(PricelistItemDAOImpl.class);
		bind(UserPreferencesDAO.class).to(UserPreferencesDAOImpl.class);
		bind(AddressDAO.class).to(AddressDAOImpl.class);
		bind(PatientDAO.class).to(PatientDAOImpl.class);
		bind(MedicineDAO.class).to(MedicineDAOImpl.class);
		bind(MedicalhistoryEntryDAO.class).to(MedicalhistoryEntryDAOImpl.class);
		bind(MedicalhistoryDAO.class).to(MedicalhistoryDAOImpl.class);
		bind(ActivityDAO.class).to(ActivityDAOImpl.class);
		bind(VisitDAO.class).to(VisitDAOImpl.class);
		bind(ContactInfoDAO.class).to(ContactInfoDAOImpl.class);
		bind(TestUtils.class);

	}
	
	@Singleton
	public static class JPAInitializer {

		@Inject
		public JPAInitializer(final PersistService service) {
			service.start();
		}
	}

}
