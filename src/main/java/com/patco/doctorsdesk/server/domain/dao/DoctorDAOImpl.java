package com.patco.doctorsdesk.server.domain.dao;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

public class DoctorDAOImpl extends GenericDAOImpl<Doctor, Integer> implements DoctorDAO{

	@Override
	public Doctor getDoctorByUserName(String userName) {
		Query q = getEntityManager().createNamedQuery(Doctor.GETBYUSERNAME)
				  .setParameter("username", userName);
		return  (Doctor) q.getSingleResult();
	}

}
