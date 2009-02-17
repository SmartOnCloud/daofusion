package com.anasoft.os.daofusion.test.example.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.anasoft.os.daofusion.AbstractHibernateEntityDao;
import com.anasoft.os.daofusion.entity.Persistable;

public abstract class EntityManagerAwareEntityDao<T extends Persistable<ID>, ID extends Serializable> extends AbstractHibernateEntityDao<T, ID> {

	@Autowired
    private EntityManagerHolder entityManagerHolder;
	
	@Override
	protected HibernateEntityManager getHibernateEntityManager() {
		return entityManagerHolder.getEntityManager();
	}
	
	// for test purposes only
	public Session getHibernateSession() {
        return getSession();
    }
	
}
