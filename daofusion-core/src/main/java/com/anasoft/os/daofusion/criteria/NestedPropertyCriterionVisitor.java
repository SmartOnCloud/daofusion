package com.anasoft.os.daofusion.criteria;

import org.hibernate.Criteria;

/**
 * Visitor for specific {@link NestedPropertyCriterion} subclasses
 * intended to group the query constraint application logic
 * operating on a single {@link Criteria} instance.
 * 
 * @see NestedPropertyCriterion
 * @see NestedPropertyCriteria
 * 
 * @author vojtech.szocs
 */
public interface NestedPropertyCriterionVisitor {

	/**
	 * @param criterion Persistent entity property criterion to visit.
	 */
	public void visit(FilterCriterion criterion);
	
	/**
	 * @param criterion Persistent entity property criterion to visit.
	 */
	public void visit(SortCriterion criterion);
	
}
