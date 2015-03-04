package com.patco.doctorsdesk.server.domain.dao.base;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.patco.doctorsdesk.server.domain.entities.base.DBEntity;

public class BaseDao<T extends DBEntity> {

	@Inject
	private Provider<EntityManager> emProvider;

	public BaseDao() {
		super();
	}

	public EntityManager getEntityManager() {
		return emProvider.get();
	}

}
