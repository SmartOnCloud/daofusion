package com.anasoft.os.daofusion.criteria;

/**
 * Association path element contained in the {@link AssociationPath}.
 * 
 * <p>
 * 
 * Each element has a <tt>value</tt> corresponding with the given
 * persistent entity property name. An optional <tt>joinType</tt>
 * is used to specify the type of join to use when creating
 * related subcriteria within the {@link AssociationPathRegister}.
 * 
 * <p>
 * 
 * This class is immutable by design so you can safely reuse
 * its instances across the code.
 * 
 * @see AssociationPath
 * @see AssociationPathRegister
 * 
 * @author michal.jemala
 * @author vojtech.szocs
 */
public class AssociationPathElement {

    private final String value;
    private final NestedPropertyJoinType joinType;
    
    /**
     * Creates a new association path element.
     * 
     * @param value Persistent entity property name.
     * @param joinType Type of join to use when creating
     * related subcriteria.
     */
    public AssociationPathElement(String value, NestedPropertyJoinType joinType) {
        this.value = value;
        this.joinType = joinType;
    }
    
    /**
     * Creates a new association path element
     * using the default join type.
     * 
     * @param value Persistent entity property name.
     */
    public AssociationPathElement(String value) {
        this(value, NestedPropertyJoinType.DEFAULT);
    }
    
    /**
     * @return Persistent entity property name.
     */
    public String getValue() {
        return value;
    }
    
    /**
     * @return Type of join to use when creating
     * related subcriteria.
     */
    public NestedPropertyJoinType getJoinType() {
        return joinType;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((joinType == null) ? 0 : joinType.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        
        AssociationPathElement other = (AssociationPathElement) obj;
        
        if (joinType == null) {
            if (other.joinType != null)
                return false;
        } else if (!joinType.equals(other.joinType))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        
        return true;
    }
    
    @Override
    public String toString() {
        return new StringBuilder(value)
            .append(" [")
            .append(joinType.toString())
            .append("]")
            .toString();
    }
    
}
