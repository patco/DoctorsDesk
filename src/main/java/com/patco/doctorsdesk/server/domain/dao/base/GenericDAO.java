package com.patco.doctorsdesk.server.domain.dao.base;

import java.util.List;

import javax.persistence.EntityManager;

import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;
import com.patco.doctorsdesk.server.util.exceptions.ValidationException;

public interface GenericDAO<E extends DBEntity<?>, P> {

	/**
	 * Persist the indicated entity to database
	 * 
	 * @param entity
	 * @return the primary key
	 */
	P insert(E entity) throws ValidationException;

	/**
	 * Retrieve an object using indicated ID
	 * 
	 * @param id
	 * @return
	 */
	E find(P id);

	/**
	 * Update indicated entity to database
	 * 
	 * @param entity
	 */
	void update(E entity);

	/**
	 * Delete indicated entity from database
	 * 
	 * @param entity
	 */
	void delete(E entity);

	/**
	 * Return the entity class
	 * 
	 * @return
	 */
	Class<E> getEntityClass();

	/**
	 * Get the entity manager
	 * 
	 * @return
	 */
	EntityManager getEntityManager();

	/**
	 * 
	 * @return
	 */
	List<E> findAll();
	
	Long countAll();
}
