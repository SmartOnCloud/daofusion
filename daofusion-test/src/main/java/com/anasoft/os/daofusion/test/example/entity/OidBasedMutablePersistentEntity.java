package com.anasoft.os.daofusion.test.example.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.NaturalId;

import com.anasoft.os.daofusion.entity.MutablePersistentEntity;

@MappedSuperclass
public abstract class OidBasedMutablePersistentEntity extends MutablePersistentEntity {

    private static final long serialVersionUID = 8066348165196551565L;
    
    public static final int OID_COLUMN_LENGTH = 36;
    public static final String OID_COLUMN_NAME = "object_id";
    
    @NaturalId
    @Column(length = OID_COLUMN_LENGTH, name = OID_COLUMN_NAME,
            unique = true, updatable = false, nullable = false)
	private String oid;
	
	public OidBasedMutablePersistentEntity() {
		oid = generateOid();
	}
	
	public String getOid() {
		return oid;
	}
	
	protected void setOid(String oid) {
		this.oid = oid;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}
	
	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof OidBasedMutablePersistentEntity)) {
			return false;
		}
		
		OidBasedMutablePersistentEntity other = (OidBasedMutablePersistentEntity) obj;
		
		return (oid == null) ? false : oid.equals(other.oid);
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        OidBasedMutablePersistentEntity clone = OidBasedMutablePersistentEntity.class.cast(super.clone());
        clone.oid = generateOid();
        return clone;
    }
    
    protected String generateOid() {
        return UUID.randomUUID().toString();
    }
	
}
