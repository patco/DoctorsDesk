package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.Patient;
import com.patco.doctorsdesk.server.util.exceptions.PatientNotFoundException;

public interface PatientDAO extends GenericDAO<Patient, Integer>{
	Long countPatientPerDoctor(Doctor doctor);
	List<Patient> getDoctorsPatient(Doctor doctor);
	Patient findOrFail(int patientid) throws PatientNotFoundException;
}
