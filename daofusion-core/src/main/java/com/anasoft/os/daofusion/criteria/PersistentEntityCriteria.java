package com.anasoft.os.daofusion.criteria;

import org.hibernate.Criteria;

import com.anasoft.os.daofusion.PersistentEntityDao;

/**
 * Persistent entity criteria contract used
 * by the {@link PersistentEntityDao}.
 * 
 * <p>
 * 
 * Implementations of this interface are responsible
 * for updating the {@link Criteria} instance according
 * to any query constraints they define.
 * 
 * @see PersistentEntityDao
 * 
 * @author vojtech.szocs
 */
public interface PersistentEntityCriteria {

    /**
     * Applies query constraints defined by the persistent entity
     * criteria implementation to the <tt>targetCriteria</tt>.
     * 
     * @param targetCriteria {@link Criteria} instance to update.
     */
    void apply(Criteria targetCriteria);
    
}
