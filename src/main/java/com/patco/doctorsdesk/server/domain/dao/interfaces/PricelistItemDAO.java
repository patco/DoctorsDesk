package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

public interface PricelistItemDAO extends GenericDAO<PricelistItem, Integer>{
	
	Long countDoctorsPriceListItems(Doctor doctor);
	
	List<PricelistItem> getDoctorsPriceListItems(Doctor doctor);

}
