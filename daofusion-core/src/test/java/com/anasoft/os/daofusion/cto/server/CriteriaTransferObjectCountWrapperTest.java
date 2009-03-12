package com.anasoft.os.daofusion.cto.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;
import com.anasoft.os.daofusion.cto.server.example.SampleConverter;

/**
 * Unit test for {@link CriteriaTransferObjectCountWrapper}.
 * 
 * @see CriteriaTransferObjectCountWrapper
 * 
 * @author vojtech.szocs
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CriteriaTransferObjectCountWrapperTest {

    /**
     * @return Sample {@link CriteriaTransferObject} instance
     * containing filter, sort and paging constraints.
     */
    private CriteriaTransferObject getSampleTransferObject_filterSortPaging() {
        final CriteriaTransferObject transferObject = getSampleTransferObject_filterOnly();
        
        final FilterAndSortCriteria nameCriteria = transferObject.get(SampleConverter.CUSTOMER_NAME_ID);
        nameCriteria.setSortAscending(true);
        nameCriteria.setIgnoreCase(true);
        
        final FilterAndSortCriteria favNoCriteria = transferObject.get(SampleConverter.CUSTOMER_FAVNO_ID);
        favNoCriteria.setSortAscending(true);
        
        final FilterAndSortCriteria joinDateCriteria = transferObject.get(SampleConverter.CUSTOMER_JOINDATE_ID);
        joinDateCriteria.setSortAscending(false);
        
        transferObject.setFirstResult(1);
        transferObject.setMaxResults(2);
        
        return transferObject;
    }
    
    /**
     * @return Sample {@link CriteriaTransferObject} instance
     * containing filter constraints only (suitable for entity
     * instance count methods).
     */
    private CriteriaTransferObject getSampleTransferObject_filterOnly() {
        final String nameCriteriaFilterValue = "Johnny %";
        final FilterAndSortCriteria nameCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_NAME_ID);
        nameCriteria.setFilterValue(nameCriteriaFilterValue);
        
        final String favNoCriteriaFilterValue = "7";
        final FilterAndSortCriteria favNoCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_FAVNO_ID);
        favNoCriteria.setFilterValue(favNoCriteriaFilterValue);
        
        final String joinDateCriteriaFilterValueOne = "2008.01.01 10:00";
        final String joinDateCriteriaFilterValueTwo = "2008.12.31 23:59";
        final FilterAndSortCriteria joinDateCriteria = new FilterAndSortCriteria(SampleConverter.CUSTOMER_JOINDATE_ID);
        joinDateCriteria.setFilterValues(joinDateCriteriaFilterValueOne, joinDateCriteriaFilterValueTwo);
        
        final CriteriaTransferObject transferObject = new CriteriaTransferObject();
        
        transferObject.add(nameCriteria);
        transferObject.add(favNoCriteria);
        transferObject.add(joinDateCriteria);
        
        return transferObject;
    }
    
    /**
     * Verifies that properties of both {@link CriteriaTransferObject}
     * instances match.
     * 
     * @param instanceOne First {@link CriteriaTransferObject} instance.
     * @param instanceTwo Second {@link CriteriaTransferObject} instance.
     */
    private void verifyTransferObjectsMatch(CriteriaTransferObject instanceOne, CriteriaTransferObject instanceTwo) {
        assertThat(instanceOne.getFirstResult(), equalTo(instanceTwo.getFirstResult()));
        assertThat(instanceOne.getMaxResults(), equalTo(instanceTwo.getMaxResults()));
        
        final Set<String> propertyIdSetOne = instanceOne.getPropertyIdSet();
        final Set<String> propertyIdSetTwo = instanceTwo.getPropertyIdSet();
        
        assertThat(propertyIdSetOne.size(), equalTo(propertyIdSetTwo.size()));
        
        for (String propertyIdOne : propertyIdSetOne) {
            assertThat(propertyIdSetTwo.contains(propertyIdOne), equalTo(true));
            
            final FilterAndSortCriteria propertyCriteriaOne = instanceOne.get(propertyIdOne);
            final FilterAndSortCriteria propertyCriteriaTwo = instanceTwo.get(propertyIdOne);
            
            assertThat(propertyCriteriaOne.getPropertyId(), equalTo(propertyCriteriaTwo.getPropertyId()));
            assertThat(propertyCriteriaOne.getSortAscending(), equalTo(propertyCriteriaTwo.getSortAscending()));
            assertThat(propertyCriteriaOne.getIgnoreCase(), equalTo(propertyCriteriaTwo.getIgnoreCase()));
            
            assertThat(Arrays.equals(propertyCriteriaOne.getFilterValues(), propertyCriteriaTwo.getFilterValues()), equalTo(true));
        }
    }
    
    /**
     * Test for {@link CriteriaTransferObjectCountWrapper#wrap()}:
     * ensuring that the resulting {@link CriteriaTransferObject}
     * instance has its paging and sort constraints suppressed.
     */
    @Test
    public void testWrap() {
        final CriteriaTransferObject transferObjectForCount = new CriteriaTransferObjectCountWrapper(
                getSampleTransferObject_filterSortPaging()).wrap();
        
        verifyTransferObjectsMatch(transferObjectForCount, getSampleTransferObject_filterOnly());
    }
    
}
