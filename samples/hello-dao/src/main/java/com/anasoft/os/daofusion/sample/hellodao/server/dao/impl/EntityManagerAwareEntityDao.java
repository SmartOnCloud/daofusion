package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import java.io.Serializable;

import org.hibernate.ejb.HibernateEntityManager;

import com.anasoft.os.daofusion.AbstractHibernateEntityDao;
import com.anasoft.os.daofusion.entity.Persistable;

public abstract class EntityManagerAwareEntityDao<T extends Persistable<ID>, ID extends Serializable>
        extends AbstractHibernateEntityDao<T, ID> {
    
	@Override
	protected HibernateEntityManager getHibernateEntityManager() {
		return EntityManagerHolder.get();
	}
	
}
