package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public class PatientDAOImpl extends GenericDAOImpl<Patient, Integer> implements PatientDAO{

	@Override
	public Long countPatientPerDoctor(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(Patient.COUNT_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getDoctorsPatient(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(Discount.GETALL_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return  q.getResultList();
	}
	
	@Override
	public void delete(Patient patient) {
		patient.getDoctor().removePatient(patient);
		super.delete(patient);
	}

}
