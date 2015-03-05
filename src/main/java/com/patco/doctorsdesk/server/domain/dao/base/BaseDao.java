package com.patco.doctorsdesk.server.domain.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;

public class BaseDao<T extends DBEntity> {

	private Provider<EntityManager> emProvider;

	@Inject
	public BaseDao(Provider<EntityManager> emProvider) {
		super();
		this.emProvider = emProvider;
	}

	public EntityManager getEntityManager() {
		return emProvider.get();
	}

	@Transactional
	public T findOrFail(Class<T> clazz, Integer id) {
		T e = getEntityManager().find(clazz, id);
		if (e == null) {
			return null;
		}
		return e;
	}

	@Transactional
	public void update(T entity) {
		getEntityManager().merge(entity);
	}
	
	
	@Transactional
	public void persist(T entity) throws ValidationException {
		try {
			getEntityManager().persist(entity);
		} catch (ConstraintViolationException e) {
			throw DoctorsDeskUtils.createValidationException(e);
		}
	}

	@Transactional
	public void delete(DBEntity dbe) {
		getEntityManager().remove(dbe);
	}

	@Transactional
	public int executeSingleIntQuery(Query q) {
		try {
			return (Integer) q.getSingleResult();
		} catch (NoResultException e) {
			// TODO Handle logging CloudentUtils.logQueryString(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	

	@Transactional
	public long executeSingleLongQuery(Query q) {
		try {
			return (Long) q.getSingleResult();
		} catch (NoResultException e) {
			// TODO Handle logging CloudentUtils.logQueryString(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Transactional
	public Object executeSingleObjectQuery(Query q) {
		try {
			return q.getSingleResult();
		} catch (NoResultException ignored) {
			// TODO Handle logging CloudentUtils.logQueryString(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public Object executeMultipleObjectQuery(Query q) {
		try {
			return q.getResultList();
		} catch (NoResultException ignored) {
			// TODO Handle logging CloudentUtils.logQueryString(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public int executeUpdateQuery(Query q) {
		try {
			return q.executeUpdate();
		} catch (NoResultException ignored) {
			// TODO Handle logging CloudentUtils.logQueryString(q);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

}
