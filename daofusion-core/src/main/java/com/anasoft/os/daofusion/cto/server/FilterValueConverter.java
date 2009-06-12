package com.anasoft.os.daofusion.cto.server;

import com.anasoft.os.daofusion.criteria.FilterCriterionProvider;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Contract for converting string-based filter values
 * received from the {@link FilterAndSortCriteria} into
 * their typed object representations.
 * 
 * @param <T> Type of filter values the underlying
 * {@link FilterCriterionProvider} works with.
 * 
 * @see FilterAndSortMapping
 * 
 * @author vojtech.szocs
 */
public interface FilterValueConverter<T> {

	/**
	 * Converts the given <tt>stringValue</tt>
	 * into appropriate object representation.
	 * 
     * <p>
     * 
     * Sample method implementations:
     * 
     * <pre>
     * return stringValue;
     * 
     * return Integer.valueOf(stringValue);
     * 
     * return Boolean.valueOf(stringValue);
     * </pre>
     * 
     * @param stringValue String-based filter value
     * received from the {@link FilterAndSortCriteria}.
     * @return Filter value object representation.
     */
	T convert(String stringValue);
	
}
