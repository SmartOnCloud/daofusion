package com.anasoft.os.daofusion.criteria;

import org.hibernate.criterion.Criterion;

/**
 * Generic {@link Criterion} instance provider
 * contract used by the {@link FilterCriterion}.
 * 
 * <p>
 * 
 * Implementations of this interface are responsible
 * for providing an appropriate {@link Criterion}
 * instance for the given property of the target
 * persistent entity.
 * 
 * @see FilterCriterion
 * 
 * @author vojtech.szocs
 */
public interface FilterCriterionProvider {

	/**
	 * Returns a {@link Criterion} instance corresponding
	 * to the given property of the target persistent entity.
	 * 
	 * <p>
	 * 
	 * Sample method implementations:
	 * 
	 * <pre>
	 * return Restrictions.isNotNull(targetPropertyName);
	 * 
	 * return Restrictions.eq(targetPropertyName, filterObjectValues[0]);
	 * 
	 * return Restrictions.between(targetPropertyName, directValues[0], directValues[1]);
	 * </pre>
	 * 
	 * @param targetPropertyName Name of the target property for
	 * which to create the {@link Criterion} instance (either a direct
	 * or indirect association to the target persistent entity).
	 * @param filterObjectValues Values extracted from the filter
	 * object.
	 * @param directValues Values provided directly by the user.
	 * @return {@link Criterion} instance for the given property.
	 */
	Criterion getCriterion(String targetPropertyName, Object[] filterObjectValues, Object[] directValues);
	
	/**
	 * Returns a flag indicating whether to use this provider
	 * during the {@link FilterCriterion} instance processing.
	 * 
	 * <p>
	 * 
	 * Use this method for disabling the {@link Criterion}
	 * instance provider in certain situations, for example:
	 * 
	 * <ul>
	 *     <li>filter value inconsistency (the provider is
	 *         unable to build the corresponding {@link Criterion}
	 *         instance due to missing or incorrect filter data)
	 *     <li>manual filter switch (the user wants to have
	 *         control over when this provider should be active)
	 * </ul>
	 * 
	 * Note that this method is called before each
	 * {@link #getCriterion(String, Object[], Object[])} invocation
	 * during the {@link FilterCriterion} instance processing.
	 * 
	 * @param filterObjectValues Values extracted from the filter
	 * object.
	 * @param directValues Values provided directly by the user.
	 * @return <tt>true</tt> to use this provider during
	 * the {@link FilterCriterion} instance processing,
	 * <tt>false</tt> otherwise.
	 */
	boolean enabled(Object[] filterObjectValues, Object[] directValues);
	
}
