package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.AddressDAO;
import com.patco.doctorsdesk.server.domain.entities.Address;
import com.patco.doctorsdesk.server.domain.entities.AddressPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public class AddressDAOImpl extends GenericDAOImpl<Address, AddressPK> implements AddressDAO{

	@Override
	public Long countPatientAddresses(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Address.COUNT_PER_PATIENT)
				  .setParameter("patient", patient);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientAddress(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Address.GETALL_PER_PATIENT)
				  .setParameter("patient", patient);
		return  q.getResultList();
	}

}
