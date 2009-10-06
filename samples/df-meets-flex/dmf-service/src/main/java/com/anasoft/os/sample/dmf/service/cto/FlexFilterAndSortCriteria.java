package com.anasoft.os.sample.dmf.service.cto;

import java.util.List;

import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

public class FlexFilterAndSortCriteria {

    private String propertyId;
    private List<String> filterValues;
    
    private Boolean sortAscending;
    private Boolean ignoreCase;
    
    public FilterAndSortCriteria getActualType() {
        FilterAndSortCriteria fasc = new FilterAndSortCriteria(propertyId);
        
        fasc.setFilterValues(filterValues.toArray(new String[0]));
        fasc.setSortAscending(sortAscending);
        fasc.setIgnoreCase(ignoreCase);
        
        return fasc;
    }
    
    public String getPropertyId() {
        return propertyId;
    }
    
    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }
    
    public List<String> getFilterValues() {
        return filterValues;
    }
    
    public void setFilterValues(List<String> filterValues) {
        this.filterValues = filterValues;
    }
    
    public Boolean getSortAscending() {
        return sortAscending;
    }
    
    public void setSortAscending(Boolean sortAscending) {
        this.sortAscending = sortAscending;
    }
    
    public Boolean getIgnoreCase() {
        return ignoreCase;
    }
    
    public void setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
    
}
