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
	 * Retrieves a persistent enumeration.
	 * 
	 * @param name <tt>name</tt> of the persistent enumeration to retrieve.
     * @param targetEntityClass Target persistent entity class.
	 * @return Resulting persistent instance or <tt>null</tt>
	 * in case the requested instance was not found.
	 */
	<S extends T> S get(String name, Class<S> targetEntityClass);
	
	/**
	 * 
	 * 
	 * @param <S>
	 * @param targetEntityClass
	 * @param names
	 * @return
	 */
	<S extends T> List<S> getByNames(Class<S> targetEntityClass, String... names);
	
	/**
	 * Retrieves a persistent enumeration, using the implicit
     * persistent entity class.
	 * 
	 * @param name <tt>name</tt> of the persistent enumeration to retrieve.
	 * @return Resulting persistent instance or <tt>null</tt>
     * in case the requested instance was not found.
     * 
     * @see #get(String, Class)
     * @see #getEntityClass()
	 */
	T get(String name);
	
}
