package com.patco.doctorsdesk.server.domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.LabDataEntryDAO;
import com.patco.doctorsdesk.server.domain.entities.LabDataEntry;
import com.patco.doctorsdesk.server.domain.entities.LabEntryPK;
import com.patco.doctorsdesk.server.domain.entities.Patient;

public class LabDataEntryDAOImpl extends GenericDAOImpl<LabDataEntry, LabEntryPK> implements LabDataEntryDAO{

	@Override
	public Long countLabDataPerPatientAndDate(Patient patient, Date date) {
		Query q = getEntityManager().createNamedQuery(LabDataEntry.COUNT_PER_PATIENT_AND_DATE)
				  .setParameter("patientid", patient.getId())
				  .setParameter("added", date);
		return  (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LabDataEntry> getLabDataPerPatientAndDate(Patient patient, Date date) {
		Query q = getEntityManager().createNamedQuery(LabDataEntry.GET_PER_PATIENT_AND_DATE)
				  .setParameter("patient", patient).setParameter("added", date);
		return  q.getResultList();
	}
	
    

}
