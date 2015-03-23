package com.patco.doctorsdesk.server.domain.dao.interfaces;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;

public interface DoctorDAO extends GenericDAO<Doctor, Integer> {
	Doctor getDoctorByUserName(String userName);
	Doctor findOrFail(int doctorid) throws DoctorNotFoundException;
}
