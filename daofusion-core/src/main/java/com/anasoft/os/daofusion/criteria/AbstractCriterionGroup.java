package com.anasoft.os.daofusion.criteria;

import org.hibernate.Criteria;

import com.anasoft.os.daofusion.util.SimpleListContainer;

/**
 * Base class for persistent entity criteria implementations
 * working with {@link PersistentEntityCriterion} instances.
 * 
 * <p>
 * 
 * This class defines the contract for paging criteria
 * (<tt>firstResult</tt> and <tt>maxResults</tt>) as well
 * as its application to the target {@link Criteria} instance.
 * 
 * @param <T> Type of the criterion managed by the group.
 * @param <V> Type of the criterion visitor applicable
 * for managed criterion instances.
 * 
 * @see PersistentEntityCriterion
 * @see PersistentEntityCriteria
 * @see SimpleListContainer
 * 
 * @author vojtech.szocs
 */
public abstract class AbstractCriterionGroup<T extends PersistentEntityCriterion<V>, V> extends SimpleListContainer<T> implements PersistentEntityCriteria {

    private Integer firstResult;
    private Integer maxResults;
    
    /**
     * @return Index of the starting element or <tt>null</tt>
     * representing no constraints on this paging parameter.
     */
    public Integer getFirstResult() {
        return firstResult;
    }
    
    /**
     * @param firstResult Index of the starting element or
     * <tt>null</tt> representing no constraints on this
     * paging parameter.
     */
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }
    
    /**
     * @return Maximum number of elements to return or
     * <tt>null</tt> representing no constraints on this
     * paging parameter.
     */
    public Integer getMaxResults() {
        return maxResults;
    }
    
    /**
     * @param maxResults Maximum number of elements to return
     * or <tt>null</tt> representing no constraints on this
     * paging parameter.
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }
    
    /**
     * Applies paging criteria to the <tt>targetCriteria</tt>.
     * 
     * @param targetCriteria {@link Criteria} instance to update.
     */
    protected void applyPagingCriteria(Criteria targetCriteria) {
        if (firstResult != null) {
            targetCriteria.setFirstResult(firstResult);
        }
        
        if (maxResults != null) {
            targetCriteria.setMaxResults(maxResults);
        }
    }
    
}
