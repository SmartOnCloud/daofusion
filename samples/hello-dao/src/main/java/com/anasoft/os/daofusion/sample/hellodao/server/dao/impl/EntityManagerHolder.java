package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import org.hibernate.ejb.HibernateEntityManager;

import com.anasoft.os.daofusion.BaseHibernateDataAccessor;
import com.anasoft.os.daofusion.sample.hellodao.server.OpenEntityManagerInViewFilter;

/**
 * {@link OpenEntityManagerInViewFilter} sets an open Hibernate
 * <tt>EntityManager</tt> instance to this class for each thread
 * associated with the request.
 * <p>
 * DAO implementations use this class when implementing
 * {@link BaseHibernateDataAccessor}'s <tt>getHibernateEntityManager</tt>
 * method.
 */
public final class EntityManagerHolder {
    
    private static final ThreadLocal<HibernateEntityManager> emPerThread = new ThreadLocal<HibernateEntityManager>();
    
    private EntityManagerHolder() {
    }
    
    public static HibernateEntityManager get() {
        return emPerThread.get();
    }
    
    public static void set(HibernateEntityManager em) {
        emPerThread.set(em);
    }
    
}
