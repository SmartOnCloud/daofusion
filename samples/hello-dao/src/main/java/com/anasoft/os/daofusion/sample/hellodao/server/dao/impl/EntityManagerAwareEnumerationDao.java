package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import org.hibernate.ejb.HibernateEntityManager;

import com.anasoft.os.daofusion.AbstractHibernateEnumerationDao;
import com.anasoft.os.daofusion.entity.PersistentEnumeration;

public abstract class EntityManagerAwareEnumerationDao<T extends PersistentEnumeration>
        extends AbstractHibernateEnumerationDao<T> {
    
    @Override
    protected HibernateEntityManager getHibernateEntityManager() {
        return EntityManagerHolder.get();
    }
	
}
