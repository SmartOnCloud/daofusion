package com.anasoft.os.daofusion.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.Criterion;

/**
 * Filter criterion for a single property of the target
 * persistent entity.
 * 
 * <p>
 * 
 * The filter criterion uses the {@link FilterCriterionProvider}
 * to construct a {@link Criterion} instance corresponding
 * to the given property of the target persistent entity.
 * 
 * <p>
 * 
 * There are basically two ways to pass filter data to
 * {@link FilterCriterionProvider} (both of which are optional):
 * 
 * <ul>
 *  <li>by specifying <tt>filterObjectValuePaths</tt> - array of
 *  	dot-separated logical paths pointing to values reachable
 *  	from the root filter object (these paths are resolved
 *      against the filter object via Java reflection)
 *  <li>by specifying <tt>directValues</tt> - array of objects
 *  	provided directly by the user
 * </ul>
 * 
 * This makes it possible to combine both filter data concepts
 * together to suit individual filter criterion needs.
 * 
 * @see FilterCriterionProvider
 * @see SimpleFilterCriterionProvider
 * @see NestedPropertyCriterion
 * 
 * @author vojtech.szocs
 */
public class FilterCriterion extends NestedPropertyCriterion<NestedPropertyCriterionVisitor> {

    private final String[] filterObjectValuePaths;
    private final Object[] directValues;
    
    private final FilterCriterionProvider filterCriterionProvider;
    
    /**
     * Creates a new filter criterion.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     * @param filterObjectValuePaths Array of dot-separated logical paths
     * pointing to values reachable from the root filter object (can be <tt>null</tt>).
     * @param directValues Array of filter values provided directly by the user
     * (can be <tt>null</tt>).
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    @Deprecated
    public FilterCriterion(String propertyPath, NestedPropertyJoinType associationJoinType, String[] filterObjectValuePaths, Object[] directValues, FilterCriterionProvider filterCriterionProvider) {
        super(propertyPath, associationJoinType);
        
        this.filterObjectValuePaths = filterObjectValuePaths;
        this.directValues = directValues;
        this.filterCriterionProvider = filterCriterionProvider;
    }
    
    /**
     * Creates a new filter criterion.
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param filterObjectValuePaths Array of dot-separated logical paths
     * pointing to values reachable from the root filter object (can be <tt>null</tt>).
     * @param directValues Array of filter values provided directly by the user
     * (can be <tt>null</tt>).
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    public FilterCriterion(AssociationPath associationPath, String targetPropertyName, String[] filterObjectValuePaths, Object[] directValues, FilterCriterionProvider filterCriterionProvider) {
        super(associationPath, targetPropertyName);
        
        this.filterObjectValuePaths = filterObjectValuePaths;
        this.directValues = directValues;
        this.filterCriterionProvider = filterCriterionProvider;
    }
    
    /**
     * Creates a new filter criterion using the default nested persistent entity
     * property join type.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param filterObjectValuePaths Array of dot-separated logical paths
     * pointing to values reachable from the root filter object (can be <tt>null</tt>).
     * @param directValues Array of filter values provided directly by the user
     * (can be <tt>null</tt>).
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    @Deprecated
    public FilterCriterion(String propertyPath, String[] filterObjectValuePaths, Object[] directValues, FilterCriterionProvider filterCriterionProvider) {
    	this(propertyPath, NestedPropertyJoinType.DEFAULT, filterObjectValuePaths, directValues, filterCriterionProvider);
    }
    
    /**
     * Creates a new filter criterion.
     * 
     * <p>
     * 
     * This is a convenience constructor supporting single filter object value path
     * or a single direct value, depending on <tt>useFilterObjectPathResolution</tt>.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
     * @param value Dot-separated logical path pointing to a value reachable
     * from the root filter object OR a direct value provided by the
     * user.
     * @param useFilterObjectPathResolution <tt>true</tt> to treat <tt>value</tt>
     * as a filter object value path, <tt>false</tt> to treat <tt>value</tt> as
     * a direct value provided by the user.
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    @Deprecated
    public FilterCriterion(String propertyPath, NestedPropertyJoinType associationJoinType, Object value, boolean useFilterObjectPathResolution, FilterCriterionProvider filterCriterionProvider) {
        this(propertyPath, associationJoinType, useFilterObjectPathResolution ? new String[] { (String) value } : null, useFilterObjectPathResolution ? null : new Object[] { value }, filterCriterionProvider);
    }
    
    /**
     * Creates a new filter criterion.
     * 
     * <p>
     * 
     * This is a convenience constructor supporting single filter object value path
     * or a single direct value, depending on <tt>useFilterObjectPathResolution</tt>.
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param value Dot-separated logical path pointing to a value reachable
     * from the root filter object <em>OR</em> a direct value provided by the
     * user.
     * @param useFilterObjectPathResolution <tt>true</tt> to treat <tt>value</tt>
     * as a filter object value path, <tt>false</tt> to treat <tt>value</tt> as
     * a direct value provided by the user.
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    public FilterCriterion(AssociationPath associationPath, String targetPropertyName, Object value, boolean useFilterObjectPathResolution, FilterCriterionProvider filterCriterionProvider) {
        this(associationPath, targetPropertyName, useFilterObjectPathResolution ? new String[] { (String) value } : null, useFilterObjectPathResolution ? null : new Object[] { value }, filterCriterionProvider);
    }
    
    /**
     * Creates a new filter criterion.
     * 
     * <p>
     * 
     * This is a convenience constructor for bypassing filter data definition
     * ({@link FilterCriterionProvider} will have to specify {@link Criterion}
     * filter data on its own).
     * 
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    public FilterCriterion(AssociationPath associationPath, String targetPropertyName, FilterCriterionProvider filterCriterionProvider) {
        this(associationPath, targetPropertyName, null, null, filterCriterionProvider);
    }
    
    /**
     * Creates a new filter criterion using the default nested persistent entity
     * property join type.
     * 
     * <p>
     * 
     * This is a convenience constructor supporting single filter object value path
     * or a single direct value, depending on <tt>useFilterObjectPathResolution</tt>.
     * 
     * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
     * 
     * @param propertyPath Dot-separated logical path to the target property.
     * @param value Dot-separated logical path pointing to a value reachable
     * from the root filter object OR a direct value provided by the
     * user.
     * @param useFilterObjectPathResolution <tt>true</tt> to treat <tt>value</tt>
     * as a filter object value path, <tt>false</tt> to treat <tt>value</tt> as
     * a direct value provided by the user.
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering.
     */
    @Deprecated
    public FilterCriterion(String propertyPath, Object value, boolean useFilterObjectPathResolution, FilterCriterionProvider filterCriterionProvider) {
        this(propertyPath, NestedPropertyJoinType.DEFAULT, value, useFilterObjectPathResolution, filterCriterionProvider);
    }
    
    /**
     * @return Array of dot-separated logical paths pointing to values
     * reachable from the root filter object (can be <tt>null</tt>).
     */
    public String[] getFilterObjectValuePaths() {
        return filterObjectValuePaths;
    }
    
    /**
     * @return Array of filter values provided directly by the user
     * (can be <tt>null</tt>).
     */
    public Object[] getDirectValues() {
        return directValues;
    }
    
    /**
     * @return {@link Criterion} instance provider used
     * for filtering.
     */
    public FilterCriterionProvider getFilterCriterionProvider() {
        return filterCriterionProvider;
    }
    
    /**
     * @see com.anasoft.os.daofusion.criteria.PersistentEntityCriterion#accept(java.lang.Object)
     */
    public void accept(NestedPropertyCriterionVisitor visitor) {
        visitor.visit(this);
    }
    
    /**
     * Builder for {@link FilterCriterion} instances.
     * 
     * @author vojtech.szocs
     */
    public static class FilterCriterionBuilder extends NestedPropertyCriterionBuilder<FilterCriterion, NestedPropertyCriterionVisitor> {
        
        private final FilterCriterionProvider filterCriterionProvider;
        
        private final List<String> filterObjectValuePaths = new ArrayList<String>();
        private final List<Object> directValues = new ArrayList<Object>();
        
        /**
         * Creates a new criterion builder.
         * 
         * @param associationPath {@link AssociationPath} which points
         * to the given property of the target persistent entity.
         * @param targetPropertyName Name of the target property of
         * the given persistent entity.
         * @param filterCriterionProvider {@link Criterion} instance
         * provider used for filtering.
         */
        public FilterCriterionBuilder(AssociationPath associationPath, String targetPropertyName, FilterCriterionProvider filterCriterionProvider) {
            super(associationPath, targetPropertyName);
            this.filterCriterionProvider = filterCriterionProvider;
        }
        
        /**
         * Appends given paths to <tt>filterObjectValuePaths</tt>.
         * 
         * @param paths Dot-separated logical paths pointing to values
         * reachable from the root filter object.
         * @return <tt>this</tt> for method chaining.
         */
        public FilterCriterionBuilder filterObjectValuePaths(String... paths) {
            filterObjectValuePaths.addAll(Arrays.asList(paths));
            return this;
        }
        
        /**
         * Appends given values to <tt>directValues</tt>.
         * 
         * @param values Filter values provided directly by the user.
         * @return <tt>this</tt> for method chaining.
         */
        public FilterCriterionBuilder directValues(Object... values) {
            directValues.addAll(Arrays.asList(values));
            return this;
        }
        
        /**
         * @see com.anasoft.os.daofusion.criteria.NestedPropertyCriterion.NestedPropertyCriterionBuilder#build()
         */
        @Override
        public FilterCriterion build() {
            return new FilterCriterion(
                    associationPath, targetPropertyName,
                    filterObjectValuePaths.toArray(new String[0]),
                    directValues.toArray(),
                    filterCriterionProvider);
        }
        
    }
    
}
