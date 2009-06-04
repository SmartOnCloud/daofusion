package com.anasoft.os.daofusion.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class intended to ease the implementation
 * of simple list-based object containers.
 * 
 * @param <T> Type of the object the container works with.
 * 
 * @author vojtech.szocs
 */
public abstract class SimpleListContainer<T> {

    private final List<T> objectList = new ArrayList<T>();
    
    /**
     * Adds the given object to this container.
     * 
     * @param object Object to add.
     * @return <tt>this</tt> for method chaining.
     */
    public SimpleListContainer<T> add(T object) {
        objectList.add(object);
        return this;
    }
    
    /**
     * Clears the list of objects.
     */
    public void clear() {
        objectList.clear();
    }
    
    /**
     * Returns the list of objects.
     * 
     * <p>
     * 
     * Note that the method returns an <em>unmodifiable</em>
     * list to prevent further list manipulation.
     * 
     * @return Object list.
     */
    public List<T> getObjectList() {
        return Collections.unmodifiableList(objectList);
    }
    
}
