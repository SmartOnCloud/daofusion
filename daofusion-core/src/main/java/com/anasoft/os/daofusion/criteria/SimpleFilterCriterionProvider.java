package com.anasoft.os.daofusion.criteria;

import org.hibernate.criterion.Criterion;

/**
 * Base class for {@link FilterCriterionProvider} implementations
 * that use either <em>filter object</em> or <em>direct values</em>
 * filter data concept.
 * 
 * <p>
 * 
 * If you wish to combine both filter data concepts together,
 * we recommend writing your own specific {@link FilterCriterionProvider}
 * implementation.
 * 
 * @see FilterCriterionProvider
 * 
 * @author vojtech.szocs
 */
public abstract class SimpleFilterCriterionProvider implements FilterCriterionProvider {
    
    /**
     * Type of the strategy for passing filter data
     * to the given {@link FilterCriterionProvider}.
     * 
     * @see FilterCriterionProvider#getCriterion(String, Object[], Object[])
     * 
     * @author vojtech.szocs
     */
    public static enum FilterDataStrategy {
        
        /**
         * Filter data comes from the filter object.
         */
        FILTER_OBJECT,
        
        /**
         * Filter data is provided directly by the user.
         */
        DIRECT,
        
        /**
         * {@link FilterCriterionProvider} implementation
         * has to manage filter data on its own.
         */
        NONE
        
    }
    
    private final FilterDataStrategy strategy;
    private final int expectedValueCount;
    private final boolean checkNullValues;
    
    /**
     * Creates a new {@link Criterion} instance provider.
     * 
     * @param strategy Type of the strategy for
     * passing filter data to this provider.
     * @param expectedValueCount Expected length of the
     * <tt>filterObjectValuePaths</tt> or <tt>directValues</tt>
     * array, depending on the chosen <tt>strategy</tt>.
     * @param checkNullValues <tt>true</tt> to enforce
     * non-null values within the value array (depending
     * on the chosen <tt>strategy</tt>), <tt>false</tt>
     * to skip the null value check.
     */
    public SimpleFilterCriterionProvider(FilterDataStrategy strategy, int expectedValueCount, boolean checkNullValues) {
        this.strategy = strategy;
        this.expectedValueCount = expectedValueCount;
        this.checkNullValues = checkNullValues;
    }
    
    /**
     * Creates a new {@link Criterion} instance provider
     * which enforces non-null values within the appropriate
     * value array by default.
     * 
     * @param strategy Type of the strategy for
     * passing filter data to this provider.
     * @param expectedValueCount Expected length of the
     * <tt>filterObjectValuePaths</tt> or <tt>directValues</tt>
     * array, depending on the chosen <tt>strategy</tt>.
     */
    public SimpleFilterCriterionProvider(FilterDataStrategy strategy, int expectedValueCount) {
        this(strategy, expectedValueCount, true);
    }
    
    /**
     * Creates a new {@link Criterion} instance provider
     * which doesn't expect any filter values (subclasses
     * have to manage filter data on their own).
     */
    public SimpleFilterCriterionProvider() {
        this(FilterDataStrategy.NONE, 0, false);
    }
    
    /**
     * @see com.anasoft.os.daofusion.criteria.FilterCriterionProvider#enabled(java.lang.Object[], java.lang.Object[])
     */
    public boolean enabled(Object[] filterObjectValues, Object[] directValues) {
        Object[] values = null;
        
        switch (strategy) {
            case FILTER_OBJECT:
                values = filterObjectValues;
                break;
            case DIRECT:
                values = directValues;
                break;
            case NONE:
                // no filter data checking
                return true;
        }
        
        if (values.length != expectedValueCount)
            return false;
        
        if (checkNullValues) {
            for (int i = 0; i < expectedValueCount; i++) {
                if (values[i] == null)
                    return false;
            }
        }
        
        return true;
    }
    
}
