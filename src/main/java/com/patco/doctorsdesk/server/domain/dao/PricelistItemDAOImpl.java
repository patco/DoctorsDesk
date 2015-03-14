package com.patco.doctorsdesk.server.domain.dao;

import java.util.List;

import javax.persistence.Query;

import com.patco.doctorsdesk.server.domain.dao.base.GenericDAOImpl;
import com.patco.doctorsdesk.server.domain.dao.interfaces.PricelistItemDAO;
import com.patco.doctorsdesk.server.domain.entities.Doctor;
import com.patco.doctorsdesk.server.domain.entities.PricelistItem;

public class PricelistItemDAOImpl  extends GenericDAOImpl<PricelistItem, Integer> implements PricelistItemDAO {
	@Override
	public void delete(PricelistItem pricelistItem) {
		pricelistItem.getDoctor().removePricelistItem(pricelistItem);
		super.delete(pricelistItem);
	}

	@Override
	public Long countDoctorsPriceListItems(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(PricelistItem.COUNT_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return (Long) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PricelistItem> getDoctorsPriceListItems(Doctor doctor) {
		Query q = getEntityManager().createNamedQuery(PricelistItem.GETALL_PER_DOCTOR)
				  .setParameter("doctor", doctor);
		return q.getResultList();
	}
	
	
		
}
