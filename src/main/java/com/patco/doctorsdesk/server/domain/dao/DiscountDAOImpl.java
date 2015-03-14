package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.DiscountDAO;
import com.patco.doctorsdesk.server.domain.entities.Discount;
import com.patco.doctorsdesk.server.domain.entities.Doctor;

public class DiscountDAOImpl extends GenericDAOImpl<Discount, Integer> implements DiscountDAO{
	

	@Override
	public Long countDoctorDiscounts(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(Discount.COUNT_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return (Long) q.getSingleResult();
	}
	
	@Override
	public void delete(Discount discount) {
		discount.getDoctor().removeDiscount(discount);
		super.delete(discount);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Discount> getDoctorsDiscount(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(Discount.GETALL_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return  q.getResultList();
	}

}
