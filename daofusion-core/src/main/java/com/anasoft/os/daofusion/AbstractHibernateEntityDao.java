package com.anasoft.os.daofusion;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.entity.Persistable;

/**
 * Generic persistent entity DAO implementation using JPA / Hibernate
 * persistence APIs.
 * 
 * <p>
 * 
 * Note that the row count technique implementation relies on Hibernate
 * {@link Projections#rowCount rowCount} projection. See the
 * {@link BaseHibernateDataAccessor#rowCount(Criteria)} method
 * for more information about the row count implementation and
 * its implications regarding {@link Criteria} instances.
 * 
 * @param <T> Type of the persistent entity the DAO works with.
 * @param <ID> Java type of persistent entity's primary key column.
 * 
 * @see PersistentEntityDao
 * @see BaseHibernateDataAccessor
 * 
 * @author vojtech.szocs
 */
public abstract class AbstractHibernateEntityDao<T extends Persistable<ID>, ID extends Serializable> extends BaseHibernateDataAccessor implements PersistentEntityDao<T, ID> {

	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#get(java.io.Serializable, java.lang.Class)
	 */
	public <S extends T> S get(ID id, Class<S> targetEntityClass) {
	    return targetEntityClass.cast(getSession().get(targetEntityClass, id));
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#get(java.io.Serializable)
	 */
	public T get(ID id) {
        return get(id, getEntityClass());
    }
	
    /**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#getAll(java.lang.Class)
	 */
	public <S extends T> List<S> getAll(Class<S> targetEntityClass) {
		return query(new NestedPropertyCriteria(), targetEntityClass);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#getAll()
	 */
	public List<T> getAll() {
        return getAll(getEntityClass());
    }
	
    /**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#saveOrUpdate(com.anasoft.os.daofusion.entity.Persistable)
	 */
	@SuppressWarnings("unchecked")
	public <S extends T> S saveOrUpdate(S entity) {
        getSession().saveOrUpdate(entity);
		return (S) getSession().merge(entity);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#delete(com.anasoft.os.daofusion.entity.Persistable)
	 */
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#delete(java.io.Serializable, java.lang.Class)
	 */
	public <S extends T> void delete(ID id, Class<S> targetEntityClass) {
		delete(get(id, targetEntityClass));
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#delete(java.io.Serializable)
	 */
	public void delete(ID id) {
        delete(id, getEntityClass());
    }
	
    /**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#deleteAll(java.lang.Class)
	 */
	public <S extends T> int deleteAll(Class<S> targetEntityClass) {
	    List<S> instancesToDelete = getAll(targetEntityClass);
	    
	    for (S instance : instancesToDelete) {
	        delete(instance);
	    }
	    
	    return instancesToDelete.size();
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#deleteAll()
	 */
	public int deleteAll() {
        return deleteAll(getEntityClass());
    }
	
    /**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#refresh(com.anasoft.os.daofusion.entity.Persistable)
	 */
	public void refresh(T entity) {
		getSession().refresh(entity);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#query(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <S extends T> List<S> query(PersistentEntityCriteria entityCriteria, Class<S> targetEntityClass) {
		return getCriteria(entityCriteria, targetEntityClass).list();
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#query(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria)
	 */
	public List<T> query(PersistentEntityCriteria entityCriteria) {
        return query(entityCriteria, getEntityClass());
    }
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#uniqueResult(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria, boolean, java.lang.Class)
	 */
	public <S extends T> S uniqueResult(PersistentEntityCriteria entityCriteria, boolean returnNullOnMultipleResults, Class<S> targetEntityClass) {
		List<S> resultList = query(entityCriteria, targetEntityClass);
		S result = null;
		
		if ((returnNullOnMultipleResults && resultList.size() == 1) || (!returnNullOnMultipleResults && resultList.size() > 0)) {
		    result = resultList.get(0);
		}
		
		return result;
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#uniqueResult(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria, boolean)
	 */
	public T uniqueResult(PersistentEntityCriteria entityCriteria, boolean returnNullOnMultipleResults) {
        return uniqueResult(entityCriteria, returnNullOnMultipleResults, getEntityClass());
    }
	
    /**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#count(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria, java.lang.Class)
	 */
	public <S extends T> int count(PersistentEntityCriteria entityCriteria, Class<S> targetEntityClass) {
        Criteria criteria = getCriteria(entityCriteria, targetEntityClass);
        return rowCount(criteria);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#count(com.anasoft.os.daofusion.criteria.PersistentEntityCriteria)
	 */
	public int count(PersistentEntityCriteria entityCriteria) {
        return count(entityCriteria, getEntityClass());
    }
	
	/**
	 * @see com.anasoft.os.daofusion.PersistentEntityDao#countAll(java.lang.Class)
	 */
	public <S extends T> int countAll(Class<S> targetEntityClass) {
	    return count(new NestedPropertyCriteria(), targetEntityClass);
	}
	
    /**
     * @see com.anasoft.os.daofusion.PersistentEntityDao#countAll()
     */
    public int countAll() {
        return countAll(getEntityClass());
    }
	
}
