package com.anasoft.os.daofusion.cto.server;

import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriterion;
import com.anasoft.os.daofusion.criteria.NestedPropertyJoinType;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Generic persistent entity criteria transfer object
 * mapping supporting a nested property of the target
 * persistent entity.
 * 
 * @see NestedPropertyMappingGroup
 * @see NestedPropertyCriterion
 * 
 * @author vojtech.szocs
 */
public abstract class NestedPropertyMapping {

	private final String propertyId;
	
	private final String propertyPath;
	private final NestedPropertyJoinType associationJoinType;
	
	/**
	 * Creates a new property mapping.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
	 */
	public NestedPropertyMapping(String propertyId, String propertyPath, NestedPropertyJoinType associationJoinType) {
		this.propertyId = propertyId;
		this.propertyPath = propertyPath;
		this.associationJoinType = associationJoinType;
	}
	
	/**
	 * @return Symbolic persistent entity property identifier.
	 */
	public String getPropertyId() {
		return propertyId;
	}
	
	/**
	 * @return Dot-separated logical path to the target property.
	 */
	public String getPropertyPath() {
		return propertyPath;
	}
	
	/**
	 * @return Type of join to use in case of a nested (non-direct)
	 * persistent entity property (can be <tt>null</tt> otherwise).
	 */
	public NestedPropertyJoinType getAssociationJoinType() {
		return associationJoinType;
	}
	
	/**
	 * Applies query constraints defined by the <tt>clientSideCriteria</tt>
	 * to the <tt>serverSideCriteria</tt> according to the property mapping
	 * implementation.
	 * 
	 * @param clientSideCriteria Client-side persistent entity criteria
	 * representation.
	 * @param serverSideCriteria {@link NestedPropertyCriteria} instance
	 * to update.
	 */
	public abstract void apply(FilterAndSortCriteria clientSideCriteria, NestedPropertyCriteria serverSideCriteria);
	
}
