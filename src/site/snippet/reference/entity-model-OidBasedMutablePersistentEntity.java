@MappedSuperclass
public abstract class OidBasedMutablePersistentEntity extends MutablePersistentEntity {

    @NaturalId
    @Column(length = 36, name = "object_id", unique = true, updatable = false, nullable = false)
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
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof OidBasedMutablePersistentEntity)) {
            return false;
        }

        final OidBasedMutablePersistentEntity other = (OidBasedMutablePersistentEntity) obj;

        return oid.equals(other.oid);
    }

}
