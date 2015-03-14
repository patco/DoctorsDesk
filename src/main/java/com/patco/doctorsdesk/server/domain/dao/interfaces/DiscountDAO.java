package com.patco.doctorsdesk.server.domain.dao.interfaces;

import java.util.List;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

public interface DiscountDAO extends GenericDAO<Discount, Integer> {
	Long countDoctorDiscounts(Doctor doctor);
	
	List<Discount> getDoctorsDiscount(Doctor doctor);

}
