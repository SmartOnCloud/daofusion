package com.anasoft.os.daofusion.cto.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.anasoft.os.daofusion.criteria.FilterCriterion;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriterion;
import com.anasoft.os.daofusion.criteria.PropertyFilterCriterionProvider;
import com.anasoft.os.daofusion.criteria.SortCriterion;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;
import com.anasoft.os.daofusion.cto.server.example.SampleConverter;

/**
 * Unit test for {@link NestedPropertyCriteriaBasedConverter}.
 * 
 * @see NestedPropertyCriteriaBasedConverter
 * 
 * @author vojtech.szocs
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class NestedPropertyCriteriaBasedConverterTest {

    /**
     * {@link NestedPropertyCriterion} instance
     * comparator that is aware of the two default
     * subclasses:
     * 
     * <ul>
     *  <li>{@link FilterCriterion}
     *  <li>{@link SortCriterion}
     * </ul>
     * 
     * There are some general rules for comparing
     * {@link NestedPropertyCriterion} instances
     * used by this comparator:
     * 
     * <br><br>
     * 
     * <ul>
     *  <li><tt>null</tt> values are always less
     *      than not-<tt>null</tt> values
     *  <li>{@link FilterCriterion} instances
     *      are always less than {@link SortCriterion}
     *      instances
     *  <li>shorter arrays are always less than
     *      longer arrays
     * </ul>
     * 
     * It is assumed that any objects contained
     * within the {@link FilterCriterion} <tt>directValues</tt>
     * array implement the {@link Comparable} interface.
     * 
     * <p>
     * 
     * If all fails (compared instances ultimately seem
     * to be equal), instance class name comparison is
     * used as the final verdict.
     * 
     * @author vojtech.szocs
     */
    private static class NestedPropertyCriterionComparator implements Comparator<NestedPropertyCriterion> {
        
        @SuppressWarnings("unchecked")
        public int compare(NestedPropertyCriterion o1, NestedPropertyCriterion o2) {
            if (o1 == o2) {
                return 0;
            } else if (o1 == null) {
                return -1;
            } else if (o2 == null) {
                return 1;
            }
            
            if (o1 instanceof FilterCriterion && o2 instanceof SortCriterion) {
                return -1;
            } else if (o1 instanceof SortCriterion && o2 instanceof FilterCriterion) {
                return 1;
            }
            
            int propertyPathComparison = o1.getPropertyPath().compareTo(o2.getPropertyPath());
            if (propertyPathComparison != 0) {
                return propertyPathComparison;
            }
            
            int associationJoinTypeComparison = o1.getAssociationJoinType().compareTo(o2.getAssociationJoinType());
            if (associationJoinTypeComparison != 0) {
                return associationJoinTypeComparison;
            }
            
            if (o1 instanceof FilterCriterion) {
                final FilterCriterion fcOne = (FilterCriterion) o1;
                final FilterCriterion fcTwo = (FilterCriterion) o2;
                
                final String[] filterObjectValuePathsOne = fcOne.getFilterObjectValuePaths();
                final String[] filterObjectValuePathsTwo = fcTwo.getFilterObjectValuePaths();
                final int filterObjectValuePathsComparison = compareArrays(filterObjectValuePathsOne, filterObjectValuePathsTwo);
                if (filterObjectValuePathsComparison != 0) {
                    return filterObjectValuePathsComparison;
                }
                
                final Comparable[] directValuesOne = new Comparable[fcOne.getDirectValues().length];
                final Comparable[] directValuesTwo = new Comparable[fcTwo.getDirectValues().length];
                System.arraycopy(fcOne.getDirectValues(), 0, directValuesOne, 0, fcOne.getDirectValues().length);
                System.arraycopy(fcTwo.getDirectValues(), 0, directValuesTwo, 0, fcTwo.getDirectValues().length);
                final int directValuesComparison = compareArrays(directValuesOne, directValuesTwo);
                if (directValuesComparison != 0) {
                    return directValuesComparison;
                }
            } else if (o1 instanceof SortCriterion) {
                final SortCriterion scOne = (SortCriterion) o1;
                final SortCriterion scTwo = (SortCriterion) o2;
                
                final boolean sortAscendingOne = scOne.isSortAscending();
                final boolean sortAscendingTwo = scTwo.isSortAscending();
                final int sortAscendingComparison = new Boolean(sortAscendingOne).compareTo(new Boolean(sortAscendingTwo));
                if (sortAscendingComparison != 0) {
                    return sortAscendingComparison;
                }
                
                final boolean ignoreCaseOne = scOne.isIgnoreCase();
                final boolean ignoreCaseTwo = scTwo.isIgnoreCase();
                final int ignoreCaseComparison = new Boolean(ignoreCaseOne).compareTo(new Boolean(ignoreCaseTwo));
                if (ignoreCaseComparison != 0) {
                    return ignoreCaseComparison;
                }
            } else if (o1 instanceof Comparable) {
                return ((Comparable) o1).compareTo(o2);
            }
            
            final String classNameOne = o1.getClass().getName();
            final String classNameTwo = o2.getClass().getName();
            
            return classNameOne.compareTo(classNameTwo);
        }
        
        @SuppressWarnings("unchecked")
        private int compareArrays(Comparable[] a1, Comparable[] a2) {
            if (a1 == a2) {
                return 0;
            } else if (a1 == null) {
                return -1;
            } else if (a2 == null) {
                return 1;
            }
            
            final int arrayLengthComparison = a1.length - a2.length;
            if (arrayLengthComparison != 0) {
                return arrayLengthComparison;
            }
            
            for (int i = 0; i < a1.length; i++) {
                final Comparable elementOne = a1[i];
                final Comparable elementTwo = a2[i];
                
                final int elementComparison = elementOne.compareTo(elementTwo);
                if (elementComparison != 0) {
                    return elementComparison;
                }
            }
            
            return 0;
        }
        
    };
    
	private SampleConverter converter;
	
	/**
	 * Set up the tested <tt>converter</tt> instance.
	 */
	@Before
	public void setupConverter() {
		converter = new SampleConverter();
		converter.initMappings();
	}
	
	/**
	 * Verifies that properties of both {@link NestedPropertyCriteria}
	 * instances match (excluding the <tt>filterObject</tt> field).
	 * 
	 * @param instanceOne First {@link NestedPropertyCriteria} instance.
	 * @param instanceTwo Second {@link NestedPropertyCriteria} instance.
	 */
	private void verifyCriteriaPropertiesMatch(NestedPropertyCriteria instanceOne, NestedPropertyCriteria instanceTwo) {
		assertThat(instanceOne.getFirstResult(), equalTo(instanceTwo.getFirstResult()));
		assertThat(instanceOne.getMaxResults(), equalTo(instanceTwo.getMaxResults()));
		
		assertThat(instanceOne.getObjectList().size(), equalTo(instanceTwo.getObjectList().size()));
		
		final List<NestedPropertyCriterion> propertyCriterionListOne = instanceOne.getObjectList();
		final List<NestedPropertyCriterion> propertyCriterionListTwo = new ArrayList<NestedPropertyCriterion>(instanceTwo.getObjectList());
		
		final Comparator<NestedPropertyCriterion> comparator = new NestedPropertyCriterionComparator();
		Collections.sort(propertyCriterionListTwo, comparator);
		
		for (int i = 0; i < propertyCriterionListOne.size(); i++) {
			final NestedPropertyCriterion propertyCriterionOne = propertyCriterionListOne.get(i);
			
			final int propertyCriterionTwoIndex = Collections.binarySearch(propertyCriterionListTwo, propertyCriterionOne, comparator);
			assertThat(propertyCriterionTwoIndex >= 0, equalTo(true));
			
			final NestedPropertyCriterion propertyCriterionTwo = propertyCriterionListTwo.get(propertyCriterionTwoIndex);
			
			assertThat(propertyCriterionOne.getPropertyPath(), equalTo(propertyCriterionTwo.getPropertyPath()));
			assertThat(propertyCriterionOne.getAssociationJoinType(), equalTo(propertyCriterionTwo.getAssociationJoinType()));
			
			if (propertyCriterionOne instanceof FilterCriterion) {
				assertThat(propertyCriterionTwo, instanceOf(FilterCriterion.class));
				
				final FilterCriterion fcOne = (FilterCriterion) propertyCriterionOne;
				final FilterCriterion fcTwo = (FilterCriterion) propertyCriterionTwo;
				
				assertThat(Arrays.equals(fcOne.getFilterObjectValuePaths(), fcTwo.getFilterObjectValuePaths()), equalTo(true));
				assertThat(Arrays.equals(fcOne.getDirectValues(), fcTwo.getDirectValues()), equalTo(true));
			} else if (propertyCriterionOne instanceof SortCriterion) {
				assertThat(propertyCriterionTwo, instanceOf(SortCriterion.class));
				
				final SortCriterion scOne = (SortCriterion) propertyCriterionOne;
				final SortCriterion scTwo = (SortCriterion) propertyCriterionTwo;
				
				assertThat(scOne.isIgnoreCase(), equalTo(scTwo.isIgnoreCase()));
				assertThat(scOne.isSortAscending(), equalTo(scTwo.isSortAscending()));
			}
		}
	}
	
	/**
	 * Returns a {@link NestedPropertyCriteria} instance
	 * as a result of the <tt>transferObject</tt> conversion
	 * via the <tt>converter</tt>.
	 * 
	 * @param transferObject {@link CriteriaTransferObject}
	 * instance to convert.
	 * @return Resulting {@link NestedPropertyCriteria} instance.
	 */
	private NestedPropertyCriteria convertCriteria(CriteriaTransferObject transferObject) {
		return (NestedPropertyCriteria) converter.convert(transferObject, SampleConverter.MAPPING_GROUP_CUSTOMER);
	}
	
	/**
	 * Generic {@link CriteriaTransferObject} conversion test
	 * routine performing the actual conversion and comparing
	 * the result against the <tt>expectedCriteria</tt>.
	 * 
	 * @param transferObject {@link CriteriaTransferObject}
	 * instance to convert.
	 * @param expectedCriteria Expected {@link NestedPropertyCriteria}
	 * instance.
	 */
	private void performTransferObjectConversionTest(CriteriaTransferObject transferObject, NestedPropertyCriteria expectedCriteria) {
		final NestedPropertyCriteria entityCriteria = convertCriteria(transferObject);
		
		assertThat(entityCriteria, notNullValue());
		
		verifyCriteriaPropertiesMatch(entityCriteria, expectedCriteria);
	}
	
	/**
	 * Test for {@link NestedPropertyCriteriaBasedConverter#convert(CriteriaTransferObject, String)}:
	 * converting criteria transfer object that defines
	 * persistent entity property filter and sort criteria.
	 * 
	 * <p>
	 * 
	 * {@link PropertyFilterCriterionProvider} instances are
	 * set to <tt>null</tt> in this case because they don't
	 * affect the structure of a nested property criterion.
	 */
	@Test
	public void testConvert_filterAndSortCriteria() {
		final String nameCriteriaFilterValue = "Johnny %";
		final FilterAndSortCriteria nameCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_NAME_ID);
		nameCriteria.setFilterValue(nameCriteriaFilterValue);
		nameCriteria.setSortAscending(true);
		nameCriteria.setIgnoreCase(true);
		
		final String favNoCriteriaFilterValue = "7";
		final FilterAndSortCriteria favNoCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_FAVNO_ID);
		favNoCriteria.setFilterValue(favNoCriteriaFilterValue);
		favNoCriteria.setSortAscending(true);
		
		final String joinDateCriteriaFilterValueOne = "2008.01.01 10:00";
		final String joinDateCriteriaFilterValueTwo = "2008.12.31 23:59";
		final FilterAndSortCriteria joinDateCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_JOINDATE_ID);
		joinDateCriteria.setFilterValues(joinDateCriteriaFilterValueOne, joinDateCriteriaFilterValueTwo);
		joinDateCriteria.setSortAscending(false);
		
		final CriteriaTransferObject transferObject = new CriteriaTransferObject();
		transferObject.add(nameCriteria);
		transferObject.add(favNoCriteria);
		transferObject.add(joinDateCriteria);
		
		final Date joinDateFilterFrom = converter.parseDate(joinDateCriteriaFilterValueOne);
		final Date joinDateFilterTo = converter.parseDate(joinDateCriteriaFilterValueTwo);
		
		final NestedPropertyCriteria expectedCriteria = new NestedPropertyCriteria();
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_NAME_PATH, nameCriteriaFilterValue, false, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_NAME_PATH, true, true));
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_FAVNO_PATH, Integer.valueOf(favNoCriteriaFilterValue), false, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_FAVNO_PATH, true));
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_JOINDATE_PATH, null, new Object[] { joinDateFilterFrom, joinDateFilterTo }, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_JOINDATE_PATH, false));
		
		performTransferObjectConversionTest(transferObject, expectedCriteria);
	}
	
	/**
	 * Test for {@link NestedPropertyCriteriaBasedConverter#convert(CriteriaTransferObject, String)}:
	 * converting criteria transfer object that defines
	 * persistent entity paging criteria.
	 */
	@Test
	public void testConvert_pagingCriteria() {
		final CriteriaTransferObject transferObject = new CriteriaTransferObject();
		transferObject.setFirstResult(1);
		transferObject.setMaxResults(2);
		
		final NestedPropertyCriteria expectedCriteria = new NestedPropertyCriteria();
		expectedCriteria.setFirstResult(1);
		expectedCriteria.setMaxResults(2);
		
		performTransferObjectConversionTest(transferObject, expectedCriteria);
	}
	
}
