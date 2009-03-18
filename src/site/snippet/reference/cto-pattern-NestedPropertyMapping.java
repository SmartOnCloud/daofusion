public abstract class NestedPropertyMapping {

    /**
     * Applies query constraints defined by the clientSideCriteria to the
     * serverSideCriteria according to the property mapping implementation.
     */
    public abstract void apply(FilterAndSortCriteria clientSideCriteria,
            NestedPropertyCriteria serverSideCriteria);

}
