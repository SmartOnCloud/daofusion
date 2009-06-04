package com.anasoft.os.daofusion.criteria;

import java.util.List;

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
    
    /**
     * @see com.anasoft.os.daofusion.criteria.PersistentEntityCriteria#apply(org.hibernate.Criteria)
     */
    public void apply(Criteria targetCriteria) {
        List<T> criterionList = getObjectList();
        
        V visitor = getCriterionVisitor(targetCriteria);
        
        for (T criterion : criterionList) {
            criterion.accept(visitor);
        }
        
        applyPagingCriteria(targetCriteria);
    }
    
    /**
     * Returns the criterion visitor instance to be used
     * within the {@link #apply(Criteria)} method.
     * 
     * @param targetCriteria Root {@link Criteria}
     * instance for visitor to work with.
     * @return Criterion visitor instance operating
     * on the <tt>targetCriteria</tt>.
     */
    protected abstract V getCriterionVisitor(Criteria targetCriteria);
    
}
