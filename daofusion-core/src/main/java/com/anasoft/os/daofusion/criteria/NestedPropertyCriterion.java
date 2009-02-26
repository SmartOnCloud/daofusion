package com.anasoft.os.daofusion.criteria;

/**
 * Generic persistent entity property criterion supporting
 * a nested property of the target persistent entity.
 * 
 * <p>
 * 
 * Logic regarding the query constraint application for specific
 * {@link NestedPropertyCriterion} subclasses is provided via
 * the {@link NestedPropertyCriterionVisitor}.
 * 
 * <p>
 * 
 * Note that the <tt>propertyPath</tt> specifies a dot-separated
 * logical path to the target property to which the criterion
 * applies, starting at the target persistent entity as the root
 * object and navigating through associated objects as necessary.
 * This way, nested criteria which operate on associated objects
 * can be easily defined by the user.
 * 
 * <p>
 * 
 * For example:
 * 
 * <ul>
 *  <li>"<tt>firstName</tt>" points to a <em>direct</em> (simple) entity
 *  	property (<tt>targetPropertyName</tt> = <tt>firstName</tt>,
 *      <tt>associationPath</tt> = <tt>null</tt>)
 *  <li>"<tt>projectManager.contactInfo.phone</tt>" points to a <em>nested</em>
 *  	entity property (<tt>targetPropertyName</tt> = <tt>phone</tt>,
 *  	<tt>associationPath</tt> = <tt>projectManager.contactInfo</tt>)
 * </ul>
 * 
 * A nested (non-direct) persistent entity property needs to
 * be joined with the root persistent entity by specifying an
 * appropriate {@link NestedPropertyJoinType}.
 * 
 * @see NestedPropertyCriteria
 * @see NestedPropertyCriterionVisitor
 * @see NestedPropertyJoinType
 * 
 * @author vojtech.szocs
 */
public abstract class NestedPropertyCriterion {

    private final String propertyPath;
    private final NestedPropertyJoinType associationJoinType;
    
    private final String targetPropertyName;
    private final String associationPath;
    
    /**
     * Creates a new property criterion.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     */
    public NestedPropertyCriterion(String propertyPath, NestedPropertyJoinType associationJoinType) {
        this.propertyPath = propertyPath;
        this.associationJoinType = associationJoinType;
        
        final int targetPropertyNameIndex = propertyPath.lastIndexOf(".");
        if (targetPropertyNameIndex != -1) {
            targetPropertyName = propertyPath.substring(targetPropertyNameIndex + 1);
            associationPath = propertyPath.substring(0, targetPropertyNameIndex);
        } else {
            targetPropertyName = propertyPath;
            associationPath = null;
        }
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
     * @return Name of the target property to which this criterion
     * applies.
     */
    public String getTargetPropertyName() {
        return targetPropertyName;
    }
    
    /**
     * @return Association path of the subcriteria to which this
     * criterion applies (<tt>null</tt> in case of a direct entity
     * property).
     */
    public String getAssociationPath() {
        return associationPath;
    }
    
	/**
	 * Accepts the given <tt>visitor</tt> to visit this
	 * property criterion.
	 * 
	 * @param visitor {@link NestedPropertyCriterionVisitor}
	 * to visit this property criterion.
	 */
	public abstract void accept(NestedPropertyCriterionVisitor visitor);
    
}
