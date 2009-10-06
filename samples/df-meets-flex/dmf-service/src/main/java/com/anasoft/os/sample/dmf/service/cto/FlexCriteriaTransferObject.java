package com.anasoft.os.sample.dmf.service.cto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;

public class FlexCriteriaTransferObject {

    private static final Integer UNDEFINED = Integer.valueOf(-1);
    
    private Integer firstResult;
    private Integer maxResults;
    
    private Map<String, FlexFilterAndSortCriteria> criteriaMap = new HashMap<String, FlexFilterAndSortCriteria>();
    
    public CriteriaTransferObject getActualType() {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        
        cto.setFirstResult(firstResult);
        cto.setMaxResults(maxResults);
        
        Collection<FlexFilterAndSortCriteria> criteriaValueSet = criteriaMap.values();
        for (FlexFilterAndSortCriteria criteria : criteriaValueSet) {
            cto.add(criteria.getActualType());
        }
        
        return cto;
    }
    
    public Integer getFirstResult() {
        return firstResult;
    }
    
    public void setFirstResult(Integer firstResult) {
        if (!UNDEFINED.equals(firstResult))
            this.firstResult = firstResult;
    }
    
    public Integer getMaxResults() {
        return maxResults;
    }
    
    public void setMaxResults(Integer maxResults) {
        if (!UNDEFINED.equals(maxResults))
            this.maxResults = maxResults;
    }
    
    public Map<String, FlexFilterAndSortCriteria> getCriteriaMap() {
        return criteriaMap;
    }
    
    public void setCriteriaMap(Map<String, FlexFilterAndSortCriteria> criteriaMap) {
        this.criteriaMap = criteriaMap;
    }
    
}
