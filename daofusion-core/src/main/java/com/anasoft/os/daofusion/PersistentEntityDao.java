package com.anasoft.os.daofusion;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.CascadeType;

import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.entity.Persistable;

/**
 * Persistent entity DAO contract containing the standard set
 * of operations required to be supported by any persistence
 * provider-specific DAO implementation.
 * 
 * <p>
 * 
 * The user must provide a default transaction support within
 * the context of DAO method calls. There are basically two
 * ways to achieve this:
 * 
 * <ul>
 * 	<li>use the Spring <tt>Transactional</tt> annotation (or
 * 		any similar metadata) in conjunction with AOP-based
 * 		declarative transaction management support to create
 * 		method-level transactional proxies
 * 	<li>handle method-level transactions manually
 * </ul>
 * 
 * In either case, it is recommended that the transaction
 * support is applied at the <em>service</em> level (service
 * methods are executed within a transactional context).
 * 
 * <p>
 * 
 * Note that it is possible to query for subclasses of the implicit
 * persistent entity class the DAO works with. This way, one can have
 * a general persistent entity DAO working with a parent entity which
 * is able to query for individual entity subclasses as well.
 * 
 * @param <T> Type of the persistent entity the DAO works with.
 * @param <ID> Java type of persistent entity's primary key column.
 * 
 * @see Persistable
 * @see PersistentEntityCriteria
 * 
 * @author vojtech.szocs
 */
public interface PersistentEntityDao<T extends Persistable<ID>, ID extends Serializable> {

    /**
     * Returns the implicit persistent entity class the DAO works with.
     * 
     * @return Persistent entity class.
     */
    public Class<T> getEntityClass();
    
	/**
	 * Retrieves a persistent instance.
	 * 
	 * @param id <tt>id</tt> of the persistent instance to retrieve.
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting persistent instance or <tt>null</tt>
	 * in case the requested instance was not found.
	 */
	public <S extends T> S get(ID id, Class<S> targetEntityClass);
	
	/**
	 * Retrieves a persistent instance, using the implicit
	 * persistent entity class.
	 * 
	 * @param id <tt>id</tt> of the persistent instance to retrieve.
	 * @return Resulting persistent instance or <tt>null</tt>
     * in case the requested instance was not found.
     * 
     * @see #get(Serializable, Class)
     * @see #getEntityClass()
	 */
	public T get(ID id);
	
	/**
	 * Retrieves all persistent instances.
	 * 
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting list of persistent instances.
	 */
	public <S extends T> List<S> getAll(Class<S> targetEntityClass);
	
	/**
	 * Retrieves all persistent instances, using the implicit
	 * persistent entity class.
	 * 
	 * @return Resulting list of persistent instances.
	 * 
	 * @see #getAll(Class)
	 * @see #getEntityClass()
	 */
	public List<T> getAll();
	
	/**
	 * Persists a transient instance or updates a detached
	 * instance.
	 * 
	 * <p>
	 * 
	 * Cascade types triggered by this operation:
	 * {@link CascadeType#SAVE_UPDATE save-update},
	 * {@link CascadeType#MERGE merge}.
	 * 
	 * @param entity Transient or detached instance to save
	 * or update.
	 * @return Resulting persistent instance.
	 */
	public <S extends T> S saveOrUpdate(S entity);
	
	/**
	 * Deletes a persistent instance.
	 * 
	 * <p>
     * 
     * Cascade types triggered by this operation:
     * {@link CascadeType#DELETE delete}.
	 * 
	 * @param entity Persistent instance to delete.
	 */
	public void delete(T entity);
	
	/**
	 * Deletes a persistent instance.
	 * 
	 * <p>
     * 
     * Cascade types triggered by this operation:
     * {@link CascadeType#DELETE delete}.
	 * 
	 * @param id <tt>id</tt> of the persistent instance to delete.
	 * @param targetEntityClass Target persistent entity class.
	 */
	public <S extends T> void delete(ID id, Class<S> targetEntityClass);
	
	/**
	 * Deletes a persistent instance, using the implicit
     * persistent entity class.
     * 
     * <p>
     * 
     * Cascade types triggered by this operation:
     * {@link CascadeType#DELETE delete}.
	 * 
	 * @param id <tt>id</tt> of the persistent instance to delete.
	 * 
	 * @see #delete(Serializable, Class)
	 * @see #getEntityClass()
	 */
	public void delete(ID id);
	
	/**
	 * Deletes all persistent instances.
	 * 
	 * <p>
     * 
     * Cascade types triggered by this operation:
     * {@link CascadeType#DELETE delete}.
	 * 
	 * @param targetEntityClass Target persistent entity class.
	 * @return Number of persistent instances deleted.
	 */
	public <S extends T> int deleteAll(Class<S> targetEntityClass);
	
	/**
	 * Deletes all persistent instances, using the implicit
     * persistent entity class.
     * 
     * <p>
     * 
     * Cascade types triggered by this operation:
     * {@link CascadeType#DELETE delete}.
	 * 
	 * @return Number of persistent instances deleted.
	 * 
	 * @see #deleteAll(Class)
	 * @see #getEntityClass()
	 */
	public int deleteAll();
	
	/**
	 * Refreshes a persistent or a detached instance by
	 * synchronizing its state with the database.
	 * 
	 * @param entity Persistent or detached instance to
	 * refresh.
	 */
	public void refresh(T entity);
	
	/**
	 * Retrieves a list of persistent instances.
	 * 
	 * @param entityCriteria {@link PersistentEntityCriteria}
	 * instance defining persistent entity query constraints.
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting list of persistent instances.
	 */
	public <S extends T> List<S> query(PersistentEntityCriteria entityCriteria, Class<S> targetEntityClass);
	
	/**
	 * Retrieves a list of persistent instances, using the implicit
     * persistent entity class.
	 * 
     * @param entityCriteria {@link PersistentEntityCriteria}
     * instance defining persistent entity query constraints.
	 * @return Resulting list of persistent instances.
	 * 
	 * @see #query(PersistentEntityCriteria, Class)
	 * @see #getEntityClass()
	 */
	public List<T> query(PersistentEntityCriteria entityCriteria);
	
	/**
	 * Returns a single persistent instance (if available).
	 * 
     * @param entityCriteria {@link PersistentEntityCriteria}
     * instance defining persistent entity query constraints.
     * @param returnNullOnMultipleResults <tt>true</tt> to return
     * <tt>null</tt> in case the query results in more than one
     * persistent instance.
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting persistent instance or <tt>null</tt>
	 * in case the requested instance was not found.
	 */
	public <S extends T> S uniqueResult(PersistentEntityCriteria entityCriteria, boolean returnNullOnMultipleResults, Class<S> targetEntityClass);
	
	/**
	 * Returns a single persistent instance (if available),
	 * using the implicit persistent entity class.
	 * 
     * @param entityCriteria {@link PersistentEntityCriteria}
     * instance defining persistent entity query constraints.
     * @param returnNullOnMultipleResults <tt>true</tt> to return
     * <tt>null</tt> in case the query results in more than one
     * persistent instance.
	 * @return Resulting persistent instance or <tt>null</tt>
     * in case the requested instance was not found.
     * 
     * @see #uniqueResult(PersistentEntityCriteria, boolean, Class)
     * @see #getEntityClass()
	 */
	public T uniqueResult(PersistentEntityCriteria entityCriteria, boolean returnNullOnMultipleResults);
	
	/**
	 * Returns the total number of instances persisted
	 * within the database.
	 * 
     * @param entityCriteria {@link PersistentEntityCriteria}
     * instance defining persistent entity query constraints.
     * @param targetEntityClass Target persistent entity class.
	 * @return Total instance count.
	 */
	public <S extends T> int count(PersistentEntityCriteria entityCriteria, Class<S> targetEntityClass);
	
	/**
	 * Returns the total number of instances persisted
     * within the database, using the implicit persistent
     * entity class.
	 * 
     * @param entityCriteria {@link PersistentEntityCriteria}
     * instance defining persistent entity query constraints.
     * @return Total instance count.
     * 
     * @see #count(PersistentEntityCriteria, Class)
     * @see #getEntityClass()
	 */
	public int count(PersistentEntityCriteria entityCriteria);
	
}
