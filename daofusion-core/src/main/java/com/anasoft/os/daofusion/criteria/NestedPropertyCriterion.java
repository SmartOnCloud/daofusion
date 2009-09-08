package com.anasoft.os.daofusion.criteria;

/**
 * Generic persistent entity property criterion supporting
 * a nested property of the target persistent entity.
 * 
 * <p>
 * 
 * The nested property criterion is essentially a combination
 * of two factors:
 * 
 * <ul>
 *  <li><tt>associationPath</tt> which points to the given
 *      property of the target persistent entity
 *  <li><tt>targetPropertyName</tt> denoting target property
 *      of the given persistent entity
 * </ul>
 * 
 * This class is immutable by design. Subclasses should remain
 * immutable as well in order to be safely reused across the
 * code.
 * 
 * @param <V> Type of the criterion visitor applicable
 * for this criterion.
 * 
 * @see NestedPropertyCriteria
 * @see NestedPropertyCriterionVisitor
 * @see AssociationPath
 * 
 * @author vojtech.szocs
 */
public abstract class NestedPropertyCriterion<V extends NestedPropertyCriterionVisitor> implements PersistentEntityCriterion<V> {

    private final AssociationPath associationPath;
    private final String targetPropertyName;
    
    /**
     * Creates a new property criterion.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     */
    @Deprecated
    public NestedPropertyCriterion(String propertyPath, NestedPropertyJoinType associationJoinType) {
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
    }
    
    /**
     * Creates a new property criterion.
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     */
    public NestedPropertyCriterion(AssociationPath associationPath, String targetPropertyName) {
        this.associationPath = associationPath;
        this.targetPropertyName = targetPropertyName;
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
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((associationPath == null) ? 0 : associationPath.hashCode());
        result = prime * result + ((targetPropertyName == null) ? 0 : targetPropertyName.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        NestedPropertyCriterion<?> other = (NestedPropertyCriterion<?>) obj;
        
        if (associationPath == null) {
            if (other.associationPath != null)
                return false;
        } else if (!associationPath.equals(other.associationPath))
            return false;
        if (targetPropertyName == null) {
            if (other.targetPropertyName != null)
                return false;
        } else if (!targetPropertyName.equals(other.targetPropertyName))
            return false;
        
        return true;
    }
    
    @Override
    public String toString() {
        return new StringBuilder(associationPath.toString())
            .append(":")
            .append(targetPropertyName)
            .toString();
    }
    
    /**
     * Base class for specific {@link NestedPropertyCriterion} builders.
     * 
     * @author vojtech.szocs
     */
    protected static abstract class NestedPropertyCriterionBuilder<T extends NestedPropertyCriterion<V>, V extends NestedPropertyCriterionVisitor> {
        
        protected final AssociationPath associationPath;
        protected final String targetPropertyName;
        
        /**
         * Creates a new criterion builder.
         * 
         * @param associationPath {@link AssociationPath} which points
         * to the given property of the target persistent entity.
         * @param targetPropertyName Name of the target property of
         * the given persistent entity.
         */
        public NestedPropertyCriterionBuilder(AssociationPath associationPath, String targetPropertyName) {
            this.associationPath = associationPath;
            this.targetPropertyName = targetPropertyName;
        }
        
        /**
         * Builds a {@link NestedPropertyCriterion} instance according
         * to the state of this builder.
         * 
         * @return Resulting {@link NestedPropertyCriterion} instance.
         */
        public abstract T build();
        
    }
    
}
