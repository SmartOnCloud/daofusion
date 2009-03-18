public interface PersistentEntityCriteria {

    /**
     * Applies query constraints defined by the persistent
     * entity criteria implementation to the targetCriteria.
     */
    public void apply(Criteria targetCriteria);

}
