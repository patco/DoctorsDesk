package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.VisitDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.Visit;
import com.patco.doctorsdesk.server.util.exceptions.VisitNotFoundException;

public class VisitDAOImpl extends GenericDAOImpl<Visit, Integer> implements VisitDAO{

	@Override
	public Long countActivityVisits(Activity activity) {
		Query q = getEntityManager().createNamedQuery(Visit.COUNT_PER_ACTIVITY)
				  .setParameter("activity", activity);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Visit> getActivityVisits(Activity activity) {
		Query q = getEntityManager().createNamedQuery(Visit.GETALL_PER_ACTIVITY)
				  .setParameter("activity", activity);
		return  q.getResultList();
	}

	@Override
	public Long countPatientVisits(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Visit.COUNT_PER_PATIENT)
				  .setParameter("patient", patient);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Visit> getPatientVisits(Patient patient) {
		Query q = getEntityManager().createNamedQuery(Visit.GETALL_PER_PATIENT)
				  .setParameter("patient", patient);
		return  q.getResultList();
	}
	
	@Override
	public void delete(Visit visit) {
		visit.getActivity().removeVisit(visit);
		super.delete(visit);
	}

	@Override
	public Visit findOrFail(Integer id) throws VisitNotFoundException{
		Visit visit = find(id);
		if (visit==null){
			throw new VisitNotFoundException(id);
		}
		return visit;
	}

}
