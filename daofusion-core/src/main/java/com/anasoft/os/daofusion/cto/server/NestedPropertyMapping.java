package com.anasoft.os.daofusion.cto.server;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.AssociationPathElement;
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
	
	private final AssociationPath associationPath;
    private final String targetPropertyName;
	
	/**
	 * Creates a new property mapping.
	 * 
	 * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
	 */
    @Deprecated
	public NestedPropertyMapping(String propertyId, String propertyPath, NestedPropertyJoinType associationJoinType) {
        int targetPropertyNameIndex = propertyPath.lastIndexOf(AssociationPath.SEPARATOR);
        
        if (targetPropertyNameIndex != -1) {
            targetPropertyName = propertyPath.substring(targetPropertyNameIndex + 1);
            String[] propertyPathElements = propertyPath.split(AssociationPath.SEPARATOR_REGEX);
            
            int associationPathLength = propertyPathElements.length - 1;
            AssociationPathElement[] associationPathElements = new AssociationPathElement[associationPathLength];
            
            for (int i = 0; i < associationPathLength; i++) {
                associationPathElements[i] = new AssociationPathElement(propertyPathElements[i], associationJoinType);
            }
            
            associationPath = new AssociationPath(associationPathElements);
        } else {
            targetPropertyName = propertyPath;
            associationPath = AssociationPath.ROOT;
        }
	    
		this.propertyId = propertyId;
	}
	
    /**
     * Creates a new property mapping.
     * 
     * @param propertyId Symbolic persistent entity property identifier.
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     */
    public NestedPropertyMapping(String propertyId, AssociationPath associationPath, String targetPropertyName) {
        this.associationPath = associationPath;
        this.targetPropertyName = targetPropertyName;
        this.propertyId = propertyId;
    }
    
	/**
	 * @return Symbolic persistent entity property identifier.
	 */
	public String getPropertyId() {
		return propertyId;
	}
	
	/**
	 * @return Name of the target property of the given persistent
	 * entity.
	 */
	public String getTargetPropertyName() {
	    return targetPropertyName;
	}
	
	/**
	 * @return {@link AssociationPath} which points to the given
     * property of the target persistent entity.
	 */
	public AssociationPath getAssociationPath() {
        return associationPath;
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
