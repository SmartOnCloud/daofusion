package com.anasoft.os.daofusion;

import java.util.List;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

/**
 * Persistent enumeration DAO contract represents an extension
 * of {@link PersistentEntityDao} working with
 * {@link PersistentEnumeration} instances.
 * 
 * @param <T> Type of the persistent enumeration the DAO works with.
 * 
 * @see PersistentEnumeration
 * @see PersistentEntityDao
 * 
 * @author vojtech.szocs
 */
public interface PersistentEnumerationDao<T extends PersistentEnumeration> extends PersistentEntityDao<T, Long> {

	/**
	 * Retrieves a persistent enumeration by its <tt>name</tt>.
	 * 
	 * @param name <tt>name</tt> of the persistent enumeration to retrieve.
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting persistent instance or <tt>null</tt>
	 * in case the requested instance was not found.
	 */
	<S extends T> S getByName(String name, Class<S> targetEntityClass);
	
	/**
     * Retrieves a persistent enumeration by its <tt>name</tt>,
     * using the implicit persistent entity class.
     * 
     * @param name <tt>name</tt> of the persistent enumeration to retrieve.
     * @return Resulting persistent instance or <tt>null</tt>
     * in case the requested instance was not found.
     * 
     * @see #getByName(String, Class)
     * @see #getEntityClass()
     */
    T getByName(String name);
	
	/**
	 * Retrieves a list of persistent enumerations by their
	 * <tt>name</tt> values.
	 * 
	 * @param targetEntityClass Target persistent entity class.
	 * @param names <tt>name</tt> values of persistent enumerations
	 * to retrieve.
	 * @return Resulting list of persistent instances.
	 */
	<S extends T> List<S> getByNames(Class<S> targetEntityClass, String... names);
	
	/**
	 * Retrieves a list of persistent enumerations by their
     * <tt>name</tt> values, using the implicit persistent
     * entity class.
	 * 
	 * @param names <tt>name</tt> values of persistent enumerations
     * to retrieve.
	 * @return Resulting list of persistent instances.
	 */
	List<T> getByNames(String... names);
	
}
