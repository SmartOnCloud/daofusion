package com.anasoft.os.daofusion;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

/**
 * Generic persistent enumeration DAO implementation using JPA / Hibernate
 * persistence APIs.
 * 
 * @param <T> Type of the persistent enumeration the DAO works with.
 * 
 * @see PersistentEnumerationDao
 * @see AbstractHibernateEntityDao
 * 
 * @author vojtech.szocs
 */
public abstract class AbstractHibernateEnumerationDao<T extends PersistentEnumeration> extends AbstractHibernateEntityDao<T, Long> implements PersistentEnumerationDao<T> {

	/**
	 * @see com.anasoft.os.daofusion.PersistentEnumerationDao#get(java.lang.String, java.lang.Class)
	 */
	public <S extends T> S get(String name, Class<S> targetEntityClass) {
		final Criteria criteria = getHibernateCriteria(targetEntityClass);
		criteria.add(Restrictions.eq(PersistentEnumeration._NAME, name));
		
		return targetEntityClass.cast(criteria.uniqueResult());
	}
	
    /**
     * @see com.anasoft.os.daofusion.PersistentEnumerationDao#get(java.lang.String)
     */
    public T get(String name) {
        return get(name, getEntityClass());
    }
	
}
