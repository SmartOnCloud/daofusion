package com.anasoft.os.daofusion.criteria;

import org.hibernate.criterion.CriteriaSpecification;

/**
 * Enumeration of possible database join types
 * applicable to {@link NestedPropertyCriterion}
 * instances.
 * 
 * <p>
 * 
 * Note that the join type for a particular nested
 * property criterion is considered only in case
 * the corresponding <tt>propertyPath</tt> points
 * to a truly nested (non-direct) persistent entity
 * property.
 * 
 * @see NestedPropertyCriterion
 * 
 * @author vojtech.szocs
 */
public enum NestedPropertyJoinType {

    DEFAULT(CriteriaSpecification.INNER_JOIN),
    INNER_JOIN(CriteriaSpecification.INNER_JOIN),
    FULL_JOIN(CriteriaSpecification.FULL_JOIN),
    LEFT_JOIN(CriteriaSpecification.LEFT_JOIN);
    
    private final int hibernateJoinType;
    
    NestedPropertyJoinType(int hibernateJoinType) {
        this.hibernateJoinType = hibernateJoinType;
    }
    
    public int getHibernateJoinType() {
        return hibernateJoinType;
    }
    
}
