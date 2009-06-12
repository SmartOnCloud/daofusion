package com.anasoft.os.daofusion.cto.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.anasoft.os.daofusion.criteria.FilterCriterion;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriteria;
import com.anasoft.os.daofusion.criteria.NestedPropertyCriterion;
import com.anasoft.os.daofusion.criteria.FilterCriterionProvider;
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

	private SampleConverter converter;
	
	/**
	 * Set up class under test.
	 */
	@Before
	public void setup() {
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
		
		Set<NestedPropertyCriterion<?>> propertyCriterionSetOne = new HashSet<NestedPropertyCriterion<?>>(instanceOne.getObjectList());
		Set<NestedPropertyCriterion<?>> propertyCriterionSetTwo = new HashSet<NestedPropertyCriterion<?>>(instanceTwo.getObjectList());
		
		assertThat(propertyCriterionSetOne, equalTo(propertyCriterionSetTwo));
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
		NestedPropertyCriteria entityCriteria = convertCriteria(transferObject);
		
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
	 * {@link FilterCriterionProvider} instances are
	 * set to <tt>null</tt> in this case because they don't
	 * affect the structure of a nested property criterion.
	 */
	@Test
	public void testConvert_filterAndSortCriteria() throws Exception {
		String nameCriteriaFilterValue = "Johnny %";
		FilterAndSortCriteria nameCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_NAME_ID);
		nameCriteria.setFilterValue(nameCriteriaFilterValue);
		nameCriteria.setSortAscending(true);
		nameCriteria.setIgnoreCase(true);
		
		String favNoCriteriaFilterValue = "7";
		FilterAndSortCriteria favNoCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_FAVNO_ID);
		favNoCriteria.setFilterValue(favNoCriteriaFilterValue);
		favNoCriteria.setSortAscending(true);
		
		String joinDateCriteriaFilterValueOne = "2008.01.01 10:00";
		String joinDateCriteriaFilterValueTwo = "2008.12.31 23:59";
		FilterAndSortCriteria joinDateCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_JOINDATE_ID);
		joinDateCriteria.setFilterValues(joinDateCriteriaFilterValueOne, joinDateCriteriaFilterValueTwo);
		joinDateCriteria.setSortAscending(false);
		
		CriteriaTransferObject transferObject = new CriteriaTransferObject();
		transferObject.add(nameCriteria);
		transferObject.add(favNoCriteria);
		transferObject.add(joinDateCriteria);
		
		Date joinDateFilterFrom = converter.parseDate(joinDateCriteriaFilterValueOne);
		Date joinDateFilterTo = converter.parseDate(joinDateCriteriaFilterValueTwo);
		
		NestedPropertyCriteria expectedCriteria = new NestedPropertyCriteria();
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_NAME_APATH, SampleConverter.CUSTOMER_NAME_TARGET, nameCriteriaFilterValue, false, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_NAME_APATH, SampleConverter.CUSTOMER_NAME_TARGET, true, true));
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_FAVNO_APATH, SampleConverter.CUSTOMER_FAVNO_TARGET, Integer.valueOf(favNoCriteriaFilterValue), false, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_FAVNO_APATH, SampleConverter.CUSTOMER_FAVNO_TARGET, true));
		
		expectedCriteria.add(new FilterCriterion(SampleConverter.CUSTOMER_JOINDATE_APATH, SampleConverter.CUSTOMER_JOINDATE_TARGET, null, new Object[] { joinDateFilterFrom, joinDateFilterTo }, null));
		expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_JOINDATE_APATH, SampleConverter.CUSTOMER_JOINDATE_TARGET, false));
		
		performTransferObjectConversionTest(transferObject, expectedCriteria);
	}
	
	/**
	 * Test for {@link NestedPropertyCriteriaBasedConverter#convert(CriteriaTransferObject, String)}:
	 * converting criteria transfer object that defines
	 * persistent entity paging criteria.
	 */
	@Test
	public void testConvert_pagingCriteria() throws Exception {
		CriteriaTransferObject transferObject = new CriteriaTransferObject();
		transferObject.setFirstResult(1);
		transferObject.setMaxResults(2);
		
		NestedPropertyCriteria expectedCriteria = new NestedPropertyCriteria();
		expectedCriteria.setFirstResult(1);
		expectedCriteria.setMaxResults(2);
		
		performTransferObjectConversionTest(transferObject, expectedCriteria);
	}
	
	/**
	 * Test for {@link NestedPropertyCriteriaBasedConverter#convert(CriteriaTransferObject, String)}:
	 * converting criteria transfer object that contains {@link FilterAndSortCriteria} instances
	 * without any filter values.
	 */
	@Test
	public void testConvert_noFilterValues() throws Exception {
	    FilterAndSortCriteria nameCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_NAME_ID);
	    nameCriteria.setSortAscending(true);
        nameCriteria.setIgnoreCase(true);
        
        FilterAndSortCriteria favNoCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_FAVNO_ID);
        favNoCriteria.setSortAscending(true);
        
        FilterAndSortCriteria joinDateCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_JOINDATE_ID);
        joinDateCriteria.setSortAscending(false);
        
        CriteriaTransferObject transferObject = new CriteriaTransferObject();
        transferObject.add(nameCriteria);
        transferObject.add(favNoCriteria);
        transferObject.add(joinDateCriteria);
        
        NestedPropertyCriteria expectedCriteria = new NestedPropertyCriteria();
        expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_NAME_APATH, SampleConverter.CUSTOMER_NAME_TARGET, true, true));
        expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_FAVNO_APATH, SampleConverter.CUSTOMER_FAVNO_TARGET, true));
        expectedCriteria.add(new SortCriterion(SampleConverter.CUSTOMER_JOINDATE_APATH, SampleConverter.CUSTOMER_JOINDATE_TARGET, false));
        
        performTransferObjectConversionTest(transferObject, expectedCriteria);
	}
	
}
