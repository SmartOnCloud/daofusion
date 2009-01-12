package com.anasoft.os.daofusion.criteria;

/**
 * Sort criterion for a single property of the target
 * persistent entity.
 * 
 * @see NestedPropertyCriterion
 * 
 * @author vojtech.szocs
 */
public class SortCriterion extends NestedPropertyCriterion {

    private final boolean sortAscending;
    private final boolean ignoreCase;
    
    /**
     * Creates a new sort criterion.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     * @param ignoreCase <tt>true</tt> for case-insensitive sorting,
     * <tt>false</tt> for case-sensitive sorting.
     */
    public SortCriterion(String propertyPath, NestedPropertyJoinType associationJoinType, boolean sortAscending, boolean ignoreCase) {
        super(propertyPath, associationJoinType);
        
        this.sortAscending = sortAscending;
        this.ignoreCase = ignoreCase;
    }
    
    /**
     * Creates a new sort criterion using the default nested persistent entity
     * property join type.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     * @param ignoreCase <tt>true</tt> for case-insensitive sorting,
     * <tt>false</tt> for case-sensitive sorting.
     */
    public SortCriterion(String propertyPath, boolean sortAscending, boolean ignoreCase) {
        this(propertyPath, NestedPropertyJoinType.DEFAULT, sortAscending, ignoreCase);
    }
    
    /**
     * Creates a new sort criterion using the default nested persistent entity
     * property join type.
     * 
     * <p>
     * 
     * This is a convenience constructor for non-string properties where
     * the <tt>ignoreCase</tt> parameter is not supported.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param sortAscending <tt>true</tt> for ascending, <tt>false</tt>
     * for descending sort order.
     */
    public SortCriterion(String propertyPath, boolean sortAscending) {
    	this(propertyPath, sortAscending, false);
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
	 * @see com.anasoft.os.daofusion.criteria.NestedPropertyCriterion#accept(com.anasoft.os.daofusion.criteria.NestedPropertyCriterionVisitor)
	 */
	@Override
	public void accept(NestedPropertyCriterionVisitor visitor) {
		visitor.visit(this);
	}
    
}
