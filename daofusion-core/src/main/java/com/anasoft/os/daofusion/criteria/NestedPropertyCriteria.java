package com.anasoft.os.daofusion.criteria;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anasoft.os.daofusion.util.ReflectionHelper;

/**
 * General purpose persistent entity criteria implementation
 * acting as container for {@link NestedPropertyCriterion}
 * instances.
 * 
 * <p>
 * 
 * This class implements the query constraint application
 * logic regarding specific {@link NestedPropertyCriterion}
 * subclasses via default {@link NestedPropertyCriterionVisitor}
 * implementation.
 * 
 * <p>
 * 
 * Additionally, the nested persistent entity property criteria
 * contains features that are common to either all or certain
 * {@link NestedPropertyCriterion} instances:
 * 
 * <ul>
 * 	<li>filter object as an <em>optional</em> source of filter values
 *      (see {@link FilterCriterion} for more information about
 *      the filter object and direct filter value concepts)
 * 	<li>use of {@link AssociationPathRegister} for root {@link Criteria}
 *      preprocessing regarding nested subcriteria (association paths
 *      defined by {@link NestedPropertyCriterion} instances)
 * </ul>
 * 
 * @see NestedPropertyCriterion
 * @see NestedPropertyCriterionVisitor
 * @see AbstractCriterionGroup
 * 
 * @author vojtech.szocs
 */
public class NestedPropertyCriteria extends AbstractCriterionGroup<NestedPropertyCriterion<NestedPropertyCriterionVisitor>, NestedPropertyCriterionVisitor> {

	private static final Logger LOG = LoggerFactory.getLogger(NestedPropertyCriteria.class);
	
	private Object filterObject;
	
	/**
	 * @return Filter object (can be <tt>null</tt>).
	 */
	public Object getFilterObject() {
		return filterObject;
	}
	
	/**
	 * @param filterObject Filter object (can be <tt>null</tt>).
	 */
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
	}
	
	/**
	 * @see com.anasoft.os.daofusion.criteria.AbstractCriterionGroup#getCriterionVisitor(org.hibernate.Criteria)
	 */
	@Override
	protected NestedPropertyCriterionVisitor getCriterionVisitor(Criteria targetCriteria) {
	    return new DefaultNestedPropertyCriterionVisitor(
	            targetCriteria,
	            new AssociationPathRegister(targetCriteria),
	            filterObject);
	}
	
	/**
	 * Default {@link NestedPropertyCriterionVisitor} implementation
	 * operating on the given {@link Criteria} instance.
	 * 
	 * @see NestedPropertyCriterionVisitor
	 * 
	 * @author vojtech.szocs
	 */
	public static class DefaultNestedPropertyCriterionVisitor implements NestedPropertyCriterionVisitor {

	    protected final Criteria targetCriteria;
	    protected final AssociationPathRegister associationPathRegister;
	    protected final Object filterObject;
	    
	    public DefaultNestedPropertyCriterionVisitor(Criteria targetCriteria, AssociationPathRegister associationPathRegister, Object filterObject) {
	        this.targetCriteria = targetCriteria;
            this.associationPathRegister = associationPathRegister;
	        this.filterObject = filterObject;
	    }
	    
	    /**
	     * @see com.anasoft.os.daofusion.criteria.NestedPropertyCriterionVisitor#visit(com.anasoft.os.daofusion.criteria.FilterCriterion)
	     */
	    public void visit(FilterCriterion criterion) {
	        
	        // resolve filterObjectValuePaths against the filterObject
	        Object[] filterObjectValues = null;
	        
	        if (filterObject != null) {
	            String[] filterObjectValuePaths = criterion.getFilterObjectValuePaths();
	            
	            if (filterObjectValuePaths != null) {
	                filterObjectValues = new Object[filterObjectValuePaths.length];
	                
	                for (int i = 0; i < filterObjectValuePaths.length; i++) {
	                    filterObjectValues[i] = ReflectionHelper.resolvePropertyPath(filterObject, filterObjectValuePaths[i]);
	                }
	            }
	        }
	        
	        // apply query constraints to the Criteria instance
	        FilterCriterionProvider criterionProvider = criterion.getFilterCriterionProvider();
            Object[] directValues = criterion.getDirectValues();
            
	        if (criterionProvider.enabled(filterObjectValues, directValues)) {
	            Criterion propertyFilterCriterion = criterionProvider.getCriterion(
	                    criterion.getTargetPropertyName(),
	                    filterObjectValues,
	                    directValues);
	            
	            AssociationPath associationPath = criterion.getAssociationPath();
	            associationPathRegister.get(associationPath).add(propertyFilterCriterion);
	        } else {
	            LOG.info("Skipping FilterCriterion instance processing for associationPath '{}' / targetPropertyName '{}' - associated filter criterion provider reports to be disabled",
	                    criterion.getAssociationPath().toString(),
	                    criterion.getTargetPropertyName());
	        }
	    }
	    
	    /**
	     * @see com.anasoft.os.daofusion.criteria.NestedPropertyCriterionVisitor#visit(com.anasoft.os.daofusion.criteria.SortCriterion)
	     */
	    public void visit(SortCriterion criterion) {
	        String targetPropertyName = criterion.getTargetPropertyName();
	        
	        Order order = criterion.isSortAscending() ? Order.asc(targetPropertyName) : Order.desc(targetPropertyName);
	        
	        if (criterion.isIgnoreCase()) {
	            order.ignoreCase();
	        }
	        
	        AssociationPath associationPath = criterion.getAssociationPath();
            associationPathRegister.get(associationPath).addOrder(order);
	    }
	    
	}
	
	@Override
	public String toString() {
	    return getObjectList().toString();
	}
	
}
