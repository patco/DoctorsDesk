package com.patco.doctorsdesk.server.domain.dao;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PatientHistoryDAO;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.domain.entities.PatientHistory;

public class PatientHistoryDAOImpl extends GenericDAOImpl<PatientHistory, Patient> implements PatientHistoryDAO{

}
