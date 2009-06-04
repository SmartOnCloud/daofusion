package com.anasoft.os.daofusion.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class intended to ease the implementation
 * of simple map-based object containers.
 * 
 * @param <K> Type of the object key to be used within
 * the object map.
 * @param <T> Type of the object the container works with.
 * 
 * @author vojtech.szocs
 */
public abstract class SimpleMapContainer<K, T> {

	private final Map<K, T> objectMap = new HashMap<K, T>();
	
	/**
	 * Returns the key for the given object.
	 * 
	 * @param object Object for which the key
	 * is required.
	 * @return Object key to use within the
	 * object map.
	 */
	protected abstract K getKey(T object);
	
	/**
	 * Adds the given object to this container.
	 * 
	 * @param object Object to add.
	 * @return <tt>this</tt> for method chaining.
	 */
	public SimpleMapContainer<K, T> add(T object) {
		objectMap.put(getKey(object), object);
		return this;
	}
	
	/**
     * Clears the list of objects.
     */
    public void clear() {
    	objectMap.clear();
    }
	
    /**
     * Returns the map of objects.
     * 
     * <p>
     * 
     * Note that the method returns an <em>unmodifiable</em>
     * map to prevent further map manipulation.
     * 
     * @return Object map.
     */
    public Map<K, T> getObjectMap() {
    	return Collections.unmodifiableMap(objectMap);
    }
    
    /**
     * Checks whether the object map
     * contains the given key.
     * 
     * @param key Object key.
     * @return <tt>true</tt> if the object
     * map contains the given key,
     * <tt>false</tt> otherwise.
     */
    public boolean containsKey(K key) {
    	return objectMap.containsKey(key);
    }
    
}
