package com.patco.doctorsdesk.server.domain.dao;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.MedicalhistoryEntryDAO;
import com.patco.doctorsdesk.server.domain.entities.Medicalhistoryentry;
import com.patco.doctorsdesk.server.domain.entities.MedicalhistoryentryPK;

public class MedicalhistoryEntryDAOImpl extends GenericDAOImpl<Medicalhistoryentry, MedicalhistoryentryPK> implements MedicalhistoryEntryDAO{
	
	@Override
	public void delete(Medicalhistoryentry entry) {
		entry.getMedicalhistory().deleteMedicalEntry(entry);
		super.delete(entry);
	}

}
