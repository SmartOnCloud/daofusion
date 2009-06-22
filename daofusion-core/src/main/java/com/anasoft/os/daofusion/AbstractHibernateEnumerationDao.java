package com.anasoft.os.daofusion;

import java.util.Arrays;
import java.util.List;

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
	 * @see com.anasoft.os.daofusion.PersistentEnumerationDao#getByName(java.lang.String, java.lang.Class)
	 */
	public <S extends T> S getByName(String name, Class<S> targetEntityClass) {
		Criteria criteria = getHibernateCriteria(targetEntityClass);
		criteria.add(Restrictions.eq(PersistentEnumeration._NAME, name));
		
		return targetEntityClass.cast(criteria.uniqueResult());
	}
	
    /**
     * @see com.anasoft.os.daofusion.PersistentEnumerationDao#getByName(java.lang.String)
     */
    public T getByName(String name) {
        return getByName(name, getEntityClass());
    }
    
    /**
     * @see com.anasoft.os.daofusion.PersistentEnumerationDao#getByNames(java.lang.Class, java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    public <S extends T> List<S> getByNames(Class<S> targetEntityClass, String... names) {
        Criteria criteria =  getHibernateCriteria(targetEntityClass);
        criteria.add(Restrictions.in(PersistentEnumeration._NAME, Arrays.asList(names)));
        
        return criteria.list();
    }
    
    /**
     * @see com.anasoft.os.daofusion.PersistentEnumerationDao#getByNames(java.lang.String[])
     */
    public List<T> getByNames(String... names) {
        return getByNames(getEntityClass(), names);
    }
    
}
