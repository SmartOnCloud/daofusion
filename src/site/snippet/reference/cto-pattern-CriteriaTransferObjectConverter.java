public interface CriteriaTransferObjectConverter {

    /**
     * Converts the given CriteriaTransferObject instance into a corresponding
     * PersistentEntityCriteria according to property mappings defined by the
     * requested property mapping group.
     */
    public PersistentEntityCriteria convert(CriteriaTransferObject transferObject,
            String mappingGroupName);

}
