public interface PersistentEnumerationDao<T extends PersistentEnumeration>
    extends PersistentEntityDao<T, Long> {

    /**
     * Retrieves a persistent enumeration.
     */
    public <S extends T> S get(String name, Class<S> targetEntityClass);

}
