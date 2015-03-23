package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.ActivityDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.util.exceptions.ActivityNotFoundException;

public class ActivityDAOImpl extends GenericDAOImpl<Activity , Integer> implements ActivityDAO{

	@Override
	public Long countPatientActivities(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Activity.COUNT_PER_PATIENT)
				  .setParameter("patient", patient);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getPatientActivities(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Activity.GETALL_PER_PATIENT)
				  .setParameter("patient", patient);
		return  q.getResultList();
	}
	
	@Override
	public void delete(Activity activity) {
		activity.getPatienthistory().removeActivity(activity);
		super.delete(activity);
	}

	@Override
	public Activity findOrFail(int id) throws ActivityNotFoundException {
		Activity activity = find(id);
		if (activity==null){
			throw new ActivityNotFoundException(id);
		}
		return activity;
	}

}
