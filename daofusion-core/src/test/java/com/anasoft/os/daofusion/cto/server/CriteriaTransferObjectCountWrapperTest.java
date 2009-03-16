package com.anasoft.os.daofusion.cto.server;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Unit test for {@link CriteriaTransferObjectCountWrapper}.
 * 
 * @see CriteriaTransferObjectCountWrapper
 * 
 * @author vojtech.szocs
 * @author igor.mihalik
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class CriteriaTransferObjectCountWrapperTest {

    private CriteriaTransferObject cto;
    private CriteriaTransferObject testedCtoWrapper;
    private FilterAndSortCriteria filterAndSortCriteria;

    @Before
    public void setup() {
        cto = mock(CriteriaTransferObject.class);
        filterAndSortCriteria = mock(FilterAndSortCriteria.class);
        testedCtoWrapper = new CriteriaTransferObjectCountWrapper(cto).wrap();
    }

    @Test
    public void testIgnorePagingInformationForCriteriaTransferObject() throws Exception {
        assertThat(testedCtoWrapper.getFirstResult(), is(equalTo(null)));
        assertThat(testedCtoWrapper.getMaxResults(), is(equalTo(null)));
        verify(cto, never()).getFirstResult();
        verify(cto, never()).getMaxResults();
    }

    @Test
    public void testIgnoreSortingAndCaseOnFilterAndSortCriteria() throws Exception {
        String propertyId = "propertyId";
        when(cto.get(propertyId)).thenReturn(filterAndSortCriteria);
        FilterAndSortCriteria testedFASCWrapper = testedCtoWrapper.get(propertyId);
        assertThat(testedFASCWrapper, is(FilterAndSortCriteria.class));
        assertThat(testedFASCWrapper.getIgnoreCase(), is(equalTo(null)));
        assertThat(testedFASCWrapper.getSortAscending(), is(equalTo(null)));
        testedFASCWrapper.getFilterValues();
        verify(filterAndSortCriteria, never()).getIgnoreCase();
        verify(filterAndSortCriteria, never()).getSortAscending();
        verify(filterAndSortCriteria, never()).getPropertyId();
    }

    @Test
    public void testCTOProperMethodInvocationDelegation() throws Exception {
        testedCtoWrapper.getPropertyIdSet();
        testedCtoWrapper.get("propertyId");
        testedCtoWrapper.setFirstResult(10);
        testedCtoWrapper.setMaxResults(20);
        FilterAndSortCriteria mock = mock(FilterAndSortCriteria.class);
        testedCtoWrapper.add(mock);
        verify(cto, times(1)).getPropertyIdSet();
        verify(cto, times(1)).get("propertyId");
        verify(cto, never()).setFirstResult(anyInt());
        verify(cto, never()).setMaxResults(anyInt());
        verify(cto, never()).add(mock);
    }

    @Test
    public void testFASCProperMethodInvocationDelegation() throws Exception {
        String propertyId = "propertyId";
        when(cto.get(propertyId)).thenReturn(filterAndSortCriteria);
        FilterAndSortCriteria wrappedFASC = testedCtoWrapper.get(propertyId);

        wrappedFASC.getFilterValues();
        wrappedFASC.getPropertyId();
        wrappedFASC.setFilterValue("value");
        wrappedFASC.setIgnoreCase(true);
        wrappedFASC.setSortAscending(true);

        verify(filterAndSortCriteria, times(1)).getFilterValues();
        verify(filterAndSortCriteria, times(1)).getPropertyId();
        verify(filterAndSortCriteria, never()).setFilterValue(anyString());
        verify(filterAndSortCriteria, never()).setIgnoreCase(anyBoolean());
        verify(filterAndSortCriteria, never()).setSortAscending(anyBoolean());
    }
}
