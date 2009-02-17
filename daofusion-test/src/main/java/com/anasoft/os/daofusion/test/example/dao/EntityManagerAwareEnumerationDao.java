package com.anasoft.os.daofusion.test.example.dao;

import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.anasoft.os.daofusion.AbstractHibernateEnumerationDao;
import com.anasoft.os.daofusion.entity.PersistentEnumeration;

public abstract class EntityManagerAwareEnumerationDao<T extends PersistentEnumeration> extends AbstractHibernateEnumerationDao<T> {

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
