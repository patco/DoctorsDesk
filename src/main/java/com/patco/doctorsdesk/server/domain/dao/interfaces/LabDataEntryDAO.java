package com.patco.doctorsdesk.server.domain.dao.interfaces;


import java.util.Date;
import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.LabDataEntry;
import com.patco.doctorsdesk.server.domain.entities.LabEntryPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public interface LabDataEntryDAO extends GenericDAO<LabDataEntry, LabEntryPK>{
	
	public Long countLabDataPerPatientAndDate(Patient patient,Date date);
	public List<LabDataEntry> getLabDataPerPatientAndDate(Patient patient,Date date);
}
