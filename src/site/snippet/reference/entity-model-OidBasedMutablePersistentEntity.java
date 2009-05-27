@MappedSuperclass
public abstract class OidBasedMutablePersistentEntity extends MutablePersistentEntity {

    public static final int OID_COLUMN_LENGTH = 36;
    public static final String OID_COLUMN_NAME = "object_id";

    @NaturalId
    @Column(length = OID_COLUMN_LENGTH, name = OID_COLUMN_NAME,
            unique = true, updatable = false, nullable = false)
    private String oid;

    public OidBasedMutablePersistentEntity() {
        oid = UUID.randomUUID().toString();
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
        if (this == obj) 
            return true;
        if (obj == null) 
            return false;
        if (!(obj instanceof OidBasedMutablePersistentEntity)) 
            return false;
        return (oid == null) ? false : oid.equals(((OidBasedMutablePersistentEntity) obj).oid);
    }

}
