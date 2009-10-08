package com.anasoft.os.daofusion.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.anasoft.os.daofusion.criteria.AssociationPathElement;

/**
 * Base class for persistent entities managed within
 * the JPA persistence context.
 * 
 * <p>
 * 
 * Note that this class does NOT provide any special
 * {@link #hashCode()} and {@link #equals(Object)}
 * implementation - this is a task left to be done,
 * depending on the chosen method implementation
 * pattern.
 * 
 * <p>
 * 
 * This class implicitly supports the {@link Object#clone()
 * clone} operation. Subclasses can implement {@link Cloneable}
 * interface if they wish to add explicit <tt>clone</tt>
 * operation support.
 * 
 * @param <ID> Java type of the primary key column.
 * 
 * @see Persistable
 * 
 * @author vojtech.szocs
 */
@MappedSuperclass
public abstract class PersistentEntity<ID extends Serializable> implements Persistable<ID> {

    private static final long serialVersionUID = -3831662382201152789L;
    public static final String _ID = "id";
    public static final AssociationPathElement ID = new AssociationPathElement(_ID);
    
    @Id
	@GeneratedValue
	private ID id;
	
	public ID getId() {
		return id;
	}
	
	protected void setId(ID id) {
		this.id = id;
	}
	
    @SuppressWarnings("unchecked")
    @Override
	protected Object clone() throws CloneNotSupportedException {
	    PersistentEntity<ID> clone = PersistentEntity.class.cast(super.clone());
	    clone.id = null;
        return clone;
	}
	
}
