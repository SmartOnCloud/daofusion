package com.anasoft.os.daofusion.cto.server;

/**
 * Typed filter value object provider contract used
 * by the {@link FilterAndSortMapping}.
 * 
 * <p>
 * 
 * Implementations of this interface are responsible
 * for providing an appropriate filter value object
 * representation for the given string-based filter
 * value.
 * 
 * @see FilterAndSortMapping
 * 
 * @author vojtech.szocs
 */
public interface FilterValueObjectProvider {

	/**
     * Returns a typed object representation of the
     * given string-based filter value.
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
     * return parseDate(stringValue);
     * </pre>
     * 
     * @param stringValue String-based filter value.
     * @return Filter value object representation.
     */
	Object getObject(String stringValue);
	
}
