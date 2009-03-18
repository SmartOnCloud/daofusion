public interface PropertyFilterCriterionProvider {

    /**
     * Returns a Criterion instance corresponding to the given
     * property of the target persistent entity.
     */
    public Criterion getCriterion(String targetPropertyName,
            Object[] filterObjectValues,
            Object[] directValues);

    /**
     * Returns a flag indicating whether to use this provider
     * during the FilterCriterion instance processing.
     */
    public boolean enabled(Object[] filterObjectValues,
            Object[] directValues);

}
