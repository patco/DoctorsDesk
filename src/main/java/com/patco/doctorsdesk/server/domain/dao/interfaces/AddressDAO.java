package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Address;
import com.patco.doctorsdesk.server.domain.entities.AddressPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public interface AddressDAO extends GenericDAO<Address, AddressPK>{
	Long countPatientAddresses(Patient patient);
	List<Patient> getPatientAddress(Patient patient);

}
