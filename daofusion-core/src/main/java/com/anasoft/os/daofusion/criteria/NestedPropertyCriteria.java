package com.anasoft.os.daofusion.criteria;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anasoft.os.daofusion.util.ReflectionHelper;
import com.anasoft.os.daofusion.util.SimpleListContainer;

/**
 * General purpose persistent entity criteria implementation
 * acting as container for {@link NestedPropertyCriterion}
 * instances.
 * 
 * <p>
 * 
 * This class implements the query constraint application
 * logic regarding specific {@link NestedPropertyCriterion}
 * subclasses via a default {@link NestedPropertyCriterionVisitor}
 * implementation. The {@link PersistentEntityCriteria#apply(Criteria)}
 * method implementation contains code that visits all
 * {@link NestedPropertyCriterion} instances, updating the given
 * root {@link Criteria} instance within the visitor class.
 * 
 * <p>
 * 
 * Additionally, the nested persistent entity property criteria
 * contains features that are common to either all or certain
 * {@link NestedPropertyCriterion} instances:
 * 
 * <ul>
 * 	<li>paging criteria definition (<tt>firstResult</tt> and
 * 		<tt>maxResults</tt>)
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
 * @see PersistentEntityCriteria
 * 
 * @author vojtech.szocs
 */
public class NestedPropertyCriteria extends SimpleListContainer<NestedPropertyCriterion<NestedPropertyCriterionVisitor>> implements PersistentEntityCriteria {

	private static final Logger LOG = LoggerFactory.getLogger(NestedPropertyCriteria.class);
	
	private Integer firstResult;
    private Integer maxResults;
	
	private Object filterObject;
	
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
	 * Returns a {@link NestedPropertyCriterionVisitor} instance
     * to be used within the {@link #apply(Criteria)} method.
     * 
     * <p>
     * 
     * Override this method if you want to plug in your custom
     * {@link NestedPropertyCriterionVisitor} implementation.
     * 
     * @param targetCriteria {@link Criteria} instance to update.
     * @param associationPathRegister {@link AssociationPathRegister}
     * initialized with Hibernate {@link Criteria} mappings.
     * @param filterObject Filter object (can be <tt>null</tt>).
     * @return {@link NestedPropertyCriterionVisitor} instance operating
     * on the <tt>targetCriteria</tt>.
	 */
	protected NestedPropertyCriterionVisitor getCriterionVisitor(Criteria targetCriteria, AssociationPathRegister associationPathRegister, Object filterObject) {
	    return new DefaultNestedPropertyCriterionVisitor(targetCriteria, associationPathRegister, filterObject);
	}
	
	/**
	 * @see com.anasoft.os.daofusion.criteria.PersistentEntityCriteria#apply(org.hibernate.Criteria)
	 */
	public final void apply(Criteria targetCriteria) {
		List<NestedPropertyCriterion<NestedPropertyCriterionVisitor>> criterionList = getObjectList();
	    
		NestedPropertyCriterionVisitor visitor = getCriterionVisitor(
				targetCriteria,
				new AssociationPathRegister(targetCriteria),
				filterObject);
		
		for (NestedPropertyCriterion<NestedPropertyCriterionVisitor> criterion : criterionList) {
			criterion.accept(visitor);
		}
		
		applyPagingCriteria(targetCriteria);
	}
	
	/**
	 * Applies paging criteria to the <tt>targetCriteria</tt>.
	 * 
	 * @param targetCriteria {@link Criteria} instance to update.
	 */
	private void applyPagingCriteria(Criteria targetCriteria) {
		if (firstResult != null) {
			targetCriteria.setFirstResult(firstResult);
		}
		
		if (maxResults != null) {
			targetCriteria.setMaxResults(maxResults);
		}
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
	        PropertyFilterCriterionProvider criterionProvider = criterion.getFilterCriterionProvider();
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
	
}
