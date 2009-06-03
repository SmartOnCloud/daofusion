package com.anasoft.os.daofusion.criteria;

/**
 * Persistent entity criterion contract that uses
 * visitor pattern for defining query constraint
 * application logic.
 * 
 * <p>
 * 
 * Implement this interface if you want to plug
 * in your custom criterion implementations.
 * 
 * @param <V> Type of the criterion visitor applicable
 * for this criterion.
 * 
 * @see NestedPropertyCriterion
 * @see NestedPropertyCriteria
 * 
 * @author vojtech.szocs
 */
public interface PersistentEntityCriterion<V> {

    /**
     * Accepts the given <tt>visitor</tt> to visit this
     * criterion.
     * 
     * @param visitor The visitor to accept.
     */
    void accept(V visitor);
    
}
