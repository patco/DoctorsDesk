package com.patco.doctorsdesk.server.domain.dao;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PrescriptionDAO;
import com.patco.doctorsdesk.server.domain.entities.Prescription;

public class PrescriptionDAOImpl extends GenericDAOImpl<Prescription, Integer> implements PrescriptionDAO{
	@Override
	public void delete(Prescription prescription) {
		prescription.getPatienthistory().removePrescription(prescription);
		super.delete(prescription);
	}

}
