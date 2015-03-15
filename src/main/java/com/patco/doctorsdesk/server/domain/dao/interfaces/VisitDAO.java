package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.Visit;


public interface VisitDAO extends GenericDAO<Visit, Integer>{
	
	Long countActivityVisits(Activity activity);
	List<Visit> getActivityVisits(Activity activity);
	
	Long countPatientVisits(Patient patient);
	List<Visit> getPatientVisits(Patient patient);
}
