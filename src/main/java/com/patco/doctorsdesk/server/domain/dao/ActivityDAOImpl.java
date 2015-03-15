package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public class ActivityDAOImpl extends GenericDAOImpl<Activity , Integer> implements ActivityDAO{

	@Override
	public Long countPatientActivities(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Activity.COUNT_PER_PATIENT)
				  .setParameter("patient", patient);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Patient> getPatientActivities(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Activity.GETALL_PER_PATIENT)
				  .setParameter("patient", patient);
		return  q.getResultList();
	}
	
	@Override
	public void delete(Activity activity) {
		activity.getPatienthistory().removeActivity(activity);
		super.delete(activity);
	}

}
