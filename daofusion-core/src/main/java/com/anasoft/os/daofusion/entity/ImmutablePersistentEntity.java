package com.anasoft.os.daofusion.entity;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Immutable;

/**
 * Base class for <em>immutable</em> persistent entities
 * which use {@link Long} as the primary key column type.
 * 
 * <p>
 * 
 * After their persistence, any updates performed
 * on immutable persistent entities are ignored by
 * Hibernate.
 * 
 * <p>
 * 
 * Typically, this kind of persistent entity is used to model
 * domain-specific objects which are not allowed to be changed
 * after their persistence.
 * 
 * @see PersistentEntity
 * 
 * @author vojtech.szocs
 */
@MappedSuperclass
@Immutable
public abstract class ImmutablePersistentEntity extends PersistentEntity<Long> {

    private static final long serialVersionUID = -8938621442547726985L;
    
}
