package com.anasoft.os.daofusion.entity;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.OptimisticLockType;

/**
 * Base class for <em>mutable</em> persistent entities
 * which use {@link Long} as the primary key column type.
 * 
 * <p>
 * 
 * Mutable persistent entities feature the version-based
 * optimistic locking strategy support.
 * 
 * <p>
 * 
 * Typically, this kind of persistent entity is used
 * to model standard mutable domain-specific objects.
 * 
 * @see PersistentEntity
 * 
 * @author vojtech.szocs
 */
@MappedSuperclass
@Entity(optimisticLock = OptimisticLockType.VERSION)
public abstract class MutablePersistentEntity extends PersistentEntity<Long> {

    private static final long serialVersionUID = 8208651173275218326L;
    
    @Version
	private Integer version;
	
	public Integer getVersion() {
		return version;
	}
	
	protected void setVersion(Integer version) {
		this.version = version;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
	    MutablePersistentEntity clone = MutablePersistentEntity.class.cast(super.clone());
	    clone.version = null;
        return clone;
	}
	
}
