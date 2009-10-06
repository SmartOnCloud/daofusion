package com.anasoft.os.daofusion.criteria;

/**
 * Sort criterion for a single property of the target
 * persistent entity.
 * 
 * @see NestedPropertyCriterion
 * 
 * @author vojtech.szocs
 */
public class SortCriterion extends NestedPropertyCriterion<NestedPropertyCriterionVisitor> {

    private final boolean sortAscending;
    private final boolean ignoreCase;
    
    /**
     * Creates a new sort criterion.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     * @param ignoreCase <tt>true</tt> for case-insensitive sorting,
     * <tt>false</tt> for case-sensitive sorting.
     */
    @Deprecated
    public SortCriterion(String propertyPath, NestedPropertyJoinType associationJoinType, boolean sortAscending, boolean ignoreCase) {
        super(propertyPath, associationJoinType);
        
        this.sortAscending = sortAscending;
        this.ignoreCase = ignoreCase;
    }
    
    /**
     * Creates a new sort criterion.
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     * @param ignoreCase <tt>true</tt> for case-insensitive sorting,
     * <tt>false</tt> for case-sensitive sorting.
     */
    public SortCriterion(AssociationPath associationPath, String targetPropertyName, boolean sortAscending, boolean ignoreCase) {
        super(associationPath, targetPropertyName);
        
        this.sortAscending = sortAscending;
        this.ignoreCase = ignoreCase;
    }
    
    /**
     * Creates a new sort criterion using the default nested persistent entity
     * property join type.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     * @param ignoreCase <tt>true</tt> for case-insensitive sorting,
     * <tt>false</tt> for case-sensitive sorting.
     */
    @Deprecated
    public SortCriterion(String propertyPath, boolean sortAscending, boolean ignoreCase) {
        this(propertyPath, NestedPropertyJoinType.DEFAULT, sortAscending, ignoreCase);
    }
    
    /**
     * Creates a new sort criterion using the default nested persistent entity
     * property join type with <tt>ignoreCase</tt> set to <tt>false</tt>.
     * 
     * <p>
     * 
     * This is a convenience constructor for non-string properties where
     * the <tt>ignoreCase</tt> parameter is not supported.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     */
    @Deprecated
    public SortCriterion(String propertyPath, boolean sortAscending) {
    	this(propertyPath, sortAscending, false);
    }
    
    /**
     * Creates a new sort criterion with <tt>ignoreCase</tt> set to <tt>false</tt>.
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     */
    public SortCriterion(AssociationPath associationPath, String targetPropertyName, boolean sortAscending) {
        this(associationPath, targetPropertyName, sortAscending, false);
    }
    
    /**
     * @return <tt>true</tt> for ascending, <tt>false</tt> for descending
     * sort order.
     */
    public boolean isSortAscending() {
        return sortAscending;
    }
    
    /**
     * @return <tt>true</tt> for case-insensitive sorting, <tt>false</tt>
     * for case-sensitive sorting.
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }
    
    /**
     * @see com.anasoft.os.daofusion.criteria.PersistentEntityCriterion#accept(java.lang.Object)
     */
    public void accept(NestedPropertyCriterionVisitor visitor) {
        visitor.visit(this);
    }
    
    /**
     * Builder for {@link SortCriterion} instances.
     * 
     * @author vojtech.szocs
     */
    public static class SortCriterionBuilder extends NestedPropertyCriterionBuilder<SortCriterion, NestedPropertyCriterionVisitor> {
        
        private final boolean sortAscending;
        
        private boolean ignoreCase = false;
        
        /**
         * Creates a new criterion builder.
         * 
         * @param associationPath {@link AssociationPath} which points
         * to the given property of the target persistent entity.
         * @param targetPropertyName Name of the target property of
         * the given persistent entity.
         * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
         * for descending sort order.
         */
        public SortCriterionBuilder(AssociationPath associationPath, String targetPropertyName, boolean sortAscending) {
            super(associationPath, targetPropertyName);
            this.sortAscending = sortAscending;
        }
        
        /**
         * Sets the <tt>ignoreCase</tt> parameter.
         * 
         * @param value <tt>true</tt> for case-insensitive sorting,
         * <tt>false</tt> for case-sensitive sorting.
         * @return <tt>this</tt> for method chaining.
         */
        public SortCriterionBuilder ignoreCase(boolean value) {
            ignoreCase = value;
            return this;
        }
        
        /**
         * @see com.anasoft.os.daofusion.criteria.NestedPropertyCriterion.NestedPropertyCriterionBuilder#build()
         */
        @Override
        public SortCriterion build() {
            return new SortCriterion(associationPath, targetPropertyName, sortAscending, ignoreCase);
        }
        
    }
    
}
