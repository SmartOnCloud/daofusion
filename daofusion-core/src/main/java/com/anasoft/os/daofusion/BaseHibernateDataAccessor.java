package com.anasoft.os.daofusion;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;

import com.anasoft.os.daofusion.entity.Persistable;

/**
 * Base class for persistent entity DAO implementations
 * providing data access through JPA / Hibernate persistence
 * APIs.
 * 
 * @see Persistable
 * 
 * @author vojtech.szocs
 */
public abstract class BaseHibernateDataAccessor {

	/**
	 * Returns an open {@link HibernateEntityManager} instance
	 * providing access to the Hibernate {@link Session}.
	 * 
	 * <p>
	 * 
	 * The most convenient method implementation pattern is to rely
	 * on entity manager instance injection via the {@link PersistenceContext}
	 * annotation (within the JPA persistence context) and returning
	 * the injected instance. Alternatively, the entity manager instance
	 * can be created directly via the {@link EntityManagerFactory}.
	 * 
	 * @return Open {@link HibernateEntityManager} instance.
	 */
	protected abstract HibernateEntityManager getHibernateEntityManager();
	
	/**
	 * Convenience method for retrieving the current {@link Session}
	 * from the entity manager.
	 * 
	 * @return {@link Session} obtained from the entity manager.
	 */
	protected final Session getSession() {
	    return getHibernateEntityManager().getSession();
	}
	
	/**
	 * Convenience method for retrieving a new {@link Criteria}
	 * instance bound to the current {@link Session}.
	 * 
	 * @param entityClass Persistent entity class for which
	 * to create the {@link Criteria} instance.
	 * @return New {@link Criteria} instance obtained from
	 * the session.
	 */
	protected final Criteria getNewCriteria(Class<? extends Persistable<? extends Serializable>> entityClass) {
		return getSession().createCriteria(entityClass);
	}
	
}
