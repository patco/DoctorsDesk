package com.patco.doctorsdesk.server.domain.dao.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.DoctorsDeskUtils;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;

public abstract class GenericDAOImpl<E extends DBEntity<?>, P> implements
		GenericDAO<E, P> {

	@Inject
	private Provider<EntityManager> emp;

	private Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public P insert(E entity) throws ValidationException{
		try {
			emp.get().persist(entity);
		} catch (ConstraintViolationException e) {
			throw DoctorsDeskUtils.createValidationException(e);

		}
		return (P) entity.getId();
	}

	public EntityManager getEntityManager() {
		return emp.get();
	}

	public E find(P id) {
		return getEntityManager().find(getEntityClass(), id);
	}

	public void update(E entity) {
		getEntityManager().merge(entity);
	}

	public void delete(E entity) {
		getEntityManager().remove(entity);
	}

	@SuppressWarnings("unchecked")
	public Class<E> getEntityClass() {
		if (entityClass == null) {
			Type type = getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) type;

				entityClass = (Class<E>) paramType.getActualTypeArguments()[0];

			} else {
				throw new IllegalArgumentException(
						"Could not guess entity class by reflection");
			}
		}
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAll() {
		return getEntityManager().createNamedQuery(
				getEntityClass().getSimpleName() + ".GetAll").getResultList();
	}
	
	@Override
	public Long countAll() {
		return (Long) getEntityManager().createNamedQuery(
				getEntityClass().getSimpleName() + ".CountAll").getSingleResult();
	}
	
	

}
