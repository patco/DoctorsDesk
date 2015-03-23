package com.patco.doctorsdesk.server.domain.dao;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DoctorDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.util.exceptions.DoctorNotFoundException;

public class DoctorDAOImpl extends GenericDAOImpl<Doctor, Integer> implements DoctorDAO{
	
	private static Logger LOG = LoggerFactory.getLogger(DoctorDAOImpl.class);

	@Override
	public Doctor getDoctorByUserName(String userName) {
		Query q = getEntityManager().createNamedQuery(Doctor.GETBYUSERNAME)
				  .setParameter("username", userName);
		try{      
			return (Doctor) q.getSingleResult();
		}catch(NoResultException noresult){
			LOG.info("No Doctor with user name " + userName +" exists");
			return null;
		}	
	}

	@Override
	public Doctor findOrFail(int doctorid) throws DoctorNotFoundException{
		Doctor doctor = this.find(doctorid);
		if (doctor == null) {
			throw new DoctorNotFoundException(doctorid);
		}
		return doctor;
	}

}
