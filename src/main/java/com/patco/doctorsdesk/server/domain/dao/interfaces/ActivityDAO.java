package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Activity;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public interface ActivityDAO extends GenericDAO<Activity, Integer>{
	
	Long countPatientActivities(Patient patient);
	List<Activity> getPatientActivities(Patient patient);

}
