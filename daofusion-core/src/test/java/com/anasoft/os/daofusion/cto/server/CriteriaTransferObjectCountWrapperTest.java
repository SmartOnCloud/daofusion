package com.anasoft.os.daofusion.cto.server;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Unit test for {@link CriteriaTransferObjectCountWrapper#wrap()}.
 * 
 * @see CriteriaTransferObjectCountWrapper
 * 
 * @author vojtech.szocs
 * @author igor.mihalik
 */
@RunWith(MockitoJUnitRunner.class)
public class CriteriaTransferObjectCountWrapperTest {

    @Mock
    private CriteriaTransferObject cto;
    
    @Mock
    private FilterAndSortCriteria filterAndSortCriteria;
    
    private CriteriaTransferObject testedCtoWrapper;
    
    /**
     * Set up class under test.
     */
    @Before
    public void setup() {
        testedCtoWrapper = new CriteriaTransferObjectCountWrapper(cto).wrap();
    }
    
    @Test
    public void testIgnorePagingInformation() throws Exception {
        assertThat(testedCtoWrapper.getFirstResult(), equalTo(null));
        assertThat(testedCtoWrapper.getMaxResults(), equalTo(null));
        
        verify(cto, never()).getFirstResult();
        verify(cto, never()).getMaxResults();
    }
    
    @Test
    public void testIgnoreSortAndPagingConstraints() throws Exception {
        String propertyId = "propertyId";
        when(cto.get(propertyId)).thenReturn(filterAndSortCriteria);
        
        FilterAndSortCriteria criteriaWrapper = testedCtoWrapper.get(propertyId);
        assertThat(criteriaWrapper, is(FilterAndSortCriteria.class));
        assertThat(criteriaWrapper.getIgnoreCase(), equalTo(null));
        assertThat(criteriaWrapper.getSortAscending(), equalTo(null));
        
        verify(filterAndSortCriteria, never()).getIgnoreCase();
        verify(filterAndSortCriteria, never()).getSortAscending();
        verify(filterAndSortCriteria, never()).getPropertyId();
    }
    
    @Test
    public void testProperMethodDelegation_criteriaTransferObject() throws Exception {
        String propertyId = "propertyId";
        testedCtoWrapper.getPropertyIdSet();
        testedCtoWrapper.get(propertyId);
        testedCtoWrapper.setFirstResult(1);
        testedCtoWrapper.setMaxResults(2);
        
        FilterAndSortCriteria criteriaMock = mock(FilterAndSortCriteria.class);
        testedCtoWrapper.add(criteriaMock);
        
        verify(cto, times(1)).getPropertyIdSet();
        verify(cto, times(1)).get(propertyId);
        verify(cto, never()).setFirstResult(anyInt());
        verify(cto, never()).setMaxResults(anyInt());
        verify(cto, never()).add(criteriaMock);
    }
    
    @Test
    public void testProperMethodDelegation_filterAndSortCriteria() throws Exception {
        String propertyId = "propertyId";
        when(cto.get(propertyId)).thenReturn(filterAndSortCriteria);
        
        FilterAndSortCriteria criteriaWrapper = testedCtoWrapper.get(propertyId);
        criteriaWrapper.getFilterValues();
        criteriaWrapper.getPropertyId();
        criteriaWrapper.setFilterValue("value");
        criteriaWrapper.setIgnoreCase(true);
        criteriaWrapper.setSortAscending(true);
        
        verify(filterAndSortCriteria, times(1)).getFilterValues();
        verify(filterAndSortCriteria, times(1)).getPropertyId();
        verify(filterAndSortCriteria, never()).setFilterValue(anyString());
        verify(filterAndSortCriteria, never()).setIgnoreCase(anyBoolean());
        verify(filterAndSortCriteria, never()).setSortAscending(anyBoolean());
    }
    
}
