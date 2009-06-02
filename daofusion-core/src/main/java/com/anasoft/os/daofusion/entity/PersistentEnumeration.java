package com.anasoft.os.daofusion.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NaturalId;

/**
 * Base class for persistent enumerations managed within
 * the JPA / Hibernate persistence context.
 * 
 * <p>
 * 
 * Persistent enumeration is a special kind of persistent
 * entity which adds a <tt>name</tt> field with unique,
 * not-updatable and not-null constraints. The <tt>name</tt>
 * field must be manually set by the user before persisting
 * the instance. Persistent enumeration uses {@link Long}
 * as the primary key column type.
 * 
 * <p>
 * 
 * Note that the persistent enumeration provides
 * a {@link #hashCode()} and {@link #equals(Object)}
 * implementation based on the <tt>name</tt> field.
 * 
 * @see PersistentEntity
 * 
 * @author vojtech.szocs
 */
@MappedSuperclass
public abstract class PersistentEnumeration extends PersistentEntity<Long> {

    private static final long serialVersionUID = 3309395499860991633L;
    
    public static final String _NAME = "name";
    
    @NaturalId
    @Column(length = 36, unique = true, updatable = false, nullable = false)
    private String name;
    
	public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		// we use instanceof because of Hibernate proxies
		if (!(obj instanceof PersistentEnumeration))
			return false;
		
		PersistentEnumeration other = (PersistentEnumeration) obj;
		
		return name.equals(other.name);
	}
    
}
