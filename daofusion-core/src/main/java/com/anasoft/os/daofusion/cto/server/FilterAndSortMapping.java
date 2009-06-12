package com.anasoft.os.daofusion.cto.server;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.FilterCriterion;
import com.anasoft.os.daofusion.criteria.FilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.NestedPropertyJoinType;
import com.anasoft.os.daofusion.criteria.SortCriterion;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Criteria transfer object mapping regarding the <em>filtering</em>
 * and <em>sorting</em> functionality.
 * 
 * <p>
 * 
 * This mapping uses a {@link FilterValueConverter} to convert
 * string-based filter values received from the {@link FilterAndSortCriteria}
 * into their typed object representations (resulting  objects will be passed
 * as <tt>directValues</tt> to the underlying {@link FilterCriterion} instances
 * in case the <tt>filterCriterionProvider</tt> is not <tt>null</tt>).
 * 
 * <p>
 * 
 * Note that the filtering functionality can be disabled by setting
 * <tt>filterCriterionProvider</tt> to <tt>null</tt>.
 * 
 * @param <T> Type of filter values the underlying
 * {@link FilterCriterionProvider} works with.
 * 
 * @see FilterCriterionProvider
 * @see FilterValueConverter
 * @see NestedPropertyMapping
 * 
 * @author vojtech.szocs
 */
public class FilterAndSortMapping<T> extends NestedPropertyMapping {

	private final FilterCriterionProvider filterCriterionProvider;
	private final FilterValueConverter<T> filterValueConverter;
	
	/**
	 * Creates a new property mapping.
	 * 
	 * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
	 * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering or <tt>null</tt> to disable the filtering functionality.
     * @param filterValueConverter {@link FilterValueConverter} implementation
     * applicable to corresponding filter values (effective only only when the
     * <tt>filterCriterionProvider</tt> is not <tt>null</tt>).
	 */
	@Deprecated
	public FilterAndSortMapping(String propertyId, String propertyPath, NestedPropertyJoinType associationJoinType, FilterCriterionProvider filterCriterionProvider, FilterValueConverter<T> filterValueConverter) {
		super(propertyId, propertyPath, associationJoinType);
		
		this.filterCriterionProvider = filterCriterionProvider;
		this.filterValueConverter = filterValueConverter;
	}
	
	/**
	 * Creates a new property mapping.
	 * 
     * @param propertyId Symbolic persistent entity property identifier.
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
     * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering or <tt>null</tt> to disable the filtering functionality.
     * @param filterValueConverter {@link FilterValueConverter} implementation
     * applicable to corresponding filter values (effective only only when the
     * <tt>filterCriterionProvider</tt> is not <tt>null</tt>).
	 */
	public FilterAndSortMapping(String propertyId, AssociationPath associationPath, String targetPropertyName, FilterCriterionProvider filterCriterionProvider, FilterValueConverter<T> filterValueConverter) {
	    super(propertyId, associationPath, targetPropertyName);
	    
        this.filterCriterionProvider = filterCriterionProvider;
        this.filterValueConverter = filterValueConverter;
	}
	
	/**
	 * Creates a new property mapping using the default
	 * nested persistent entity property join type.
	 * 
	 * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 * @param filterCriterionProvider {@link Criterion} instance provider used
     * for filtering or <tt>null</tt> to disable the filtering functionality.
     * @param filterValueConverter {@link FilterValueConverter} implementation
     * applicable to corresponding filter values (effective only only when the
     * <tt>filterCriterionProvider</tt> is not <tt>null</tt>).
	 */
	@Deprecated
	public FilterAndSortMapping(String propertyId, String propertyPath, FilterCriterionProvider filterCriterionProvider, FilterValueConverter<T> filterValueConverter) {
		this(propertyId, propertyPath, NestedPropertyJoinType.DEFAULT, filterCriterionProvider, filterValueConverter);
	}
	
	/**
	 * Creates a new property mapping.
	 * 
	 * <p>
	 * 
	 * This is a convenience constructor for mappings
	 * which don't require the filtering functionality.
	 * 
	 * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 * @param associationJoinType Type of join to use in case of a nested
     * (non-direct) persistent entity property (can be <tt>null</tt> otherwise).
	 */
	@Deprecated
	public FilterAndSortMapping(String propertyId, String propertyPath, NestedPropertyJoinType associationJoinType) {
		this(propertyId, propertyPath, associationJoinType, null, null);
	}
	
	/**
	 * Creates a new property mapping.
     * 
     * <p>
     * 
     * This is a convenience constructor for mappings
     * which don't require the filtering functionality.
	 * 
     * @param propertyId Symbolic persistent entity property identifier.
     * @param associationPath {@link AssociationPath} which points
     * to the given property of the target persistent entity.
     * @param targetPropertyName Name of the target property of
     * the given persistent entity.
	 */
	public FilterAndSortMapping(String propertyId, AssociationPath associationPath, String targetPropertyName) {
	    this(propertyId, associationPath, targetPropertyName, null, null);
	}
	
	/**
	 * Creates a new property mapping using the default
	 * nested persistent entity property join type.
	 * 
	 * <p>
	 * 
	 * This is a convenience constructor for mappings
	 * which don't require the filtering functionality.
	 * 
	 * @deprecated <tt>propertyPath</tt> / <tt>associationJoinType</tt> concept
     * is now deprecated in favor of the <tt>associationPath</tt> / <tt>targetPropertyName</tt>
     * approach.
	 * 
	 * @param propertyId Symbolic persistent entity property identifier.
	 * @param propertyPath Dot-separated logical path to the target property.
	 */
	@Deprecated
	public FilterAndSortMapping(String propertyId, String propertyPath) {
		this(propertyId, propertyPath, null, null);
	}
	
	/**
	 * @return {@link Criterion} instance provider used for filtering
	 * or <tt>null</tt> to disable the filtering functionality.
	 */
	public FilterCriterionProvider getFilterCriterionProvider() {
		return filterCriterionProvider;
	}
	
	/**
	 * @return {@link FilterValueConverter} implementation applicable
	 * to corresponding filter values (effective only only when the
     * <tt>filterCriterionProvider</tt> is not <tt>null</tt>).
	 */
	public FilterValueConverter<T> getFilterValueConverter() {
		return filterValueConverter;
	}
	
	/**
	 * @see com.anasoft.os.daofusion.cto.server.NestedPropertyMapping#apply(com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria, com.anasoft.os.daofusion.criteria.NestedPropertyCriteria)
	 */
	@Override
	public void apply(FilterAndSortCriteria clientSideCriteria, NestedPropertyCriteria serverSideCriteria) {
		if (filterCriterionProvider != null && clientSideCriteria.getFilterValues().length > 0) {
			serverSideCriteria.add(getFilterCriterion(clientSideCriteria));
		}
		
		if (clientSideCriteria.getSortAscending() != null) {
			serverSideCriteria.add(getSortCriterion(clientSideCriteria));
		}
	}
	
	/**
	 * Returns a {@link FilterCriterion} instance built
	 * from the <tt>clientSideCriteria</tt>.
	 * 
	 * @param clientSideCriteria Client-side persistent entity
	 * criteria representation.
	 * @return Resulting {@link FilterCriterion} instance.
	 */
	private FilterCriterion getFilterCriterion(FilterAndSortCriteria clientSideCriteria) {
		String[] stringFilterValues = clientSideCriteria.getFilterValues();
		List<T> typedFilterValues = new ArrayList<T>();
		
		// convert string-based filter values into their typed representations
		for (int i = 0; i < stringFilterValues.length; i++) {
		    typedFilterValues.add(filterValueConverter.convert(stringFilterValues[i]));
		}
		
		return new FilterCriterion(getAssociationPath(),
				getTargetPropertyName(),
				null,
				typedFilterValues.toArray(),
				filterCriterionProvider);
	}
	
	/**
	 * Returns a {@link SortCriterion} instance built
	 * from the <tt>clientSideCriteria</tt>.
	 * 
	 * @param clientSideCriteria Client-side persistent entity
	 * criteria representation.
	 * @return Resulting {@link SortCriterion} instance.
	 */
	private SortCriterion getSortCriterion(FilterAndSortCriteria clientSideCriteria) {
		boolean sortAscending = clientSideCriteria.getSortAscending() != null ? clientSideCriteria.getSortAscending().booleanValue() : false;
		boolean ignoreCase = clientSideCriteria.getIgnoreCase() != null ? clientSideCriteria.getIgnoreCase().booleanValue() : false;
		
		return new SortCriterion(getAssociationPath(),
				getTargetPropertyName(),
				sortAscending,
				ignoreCase);
	}
	
}
