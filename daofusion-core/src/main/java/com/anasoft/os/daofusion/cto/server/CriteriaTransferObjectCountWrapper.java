package com.anasoft.os.daofusion.cto.server;

import java.util.Set;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;

/**
 * Server-side {@link CriteriaTransferObject} wrapper for entity
 * instance count purposes.
 * 
 * <p>
 * 
 * Use this class to wrap {@link CriteriaTransferObject} instances which
 * should suppress paging and sort constraints in conjunction with entity
 * instance count methods defined by the {@link PersistentEntityDao}.
 * 
 * @see CriteriaTransferObject
 * @see PersistentEntityDao
 * 
 * @author vojtech.szocs
 * @author igor.mihalik
 */
public class CriteriaTransferObjectCountWrapper {

    private final CriteriaTransferObject transferObject;
    
    /**
     * Creates a new {@link CriteriaTransferObject} wrapper.
     * 
     * @param transferObject {@link CriteriaTransferObject} instance to wrap.
     */
    public CriteriaTransferObjectCountWrapper(CriteriaTransferObject transferObject) {
        this.transferObject = transferObject;
    }
    
    /**
     * Returns a {@link CriteriaTransferObject} instance suitable for entity
     * instance count methods defined by the {@link PersistentEntityDao}.
     * 
     * <p>
     * 
     * Resulting transfer object delegates most of its methods to the wrapped
     * {@link CriteriaTransferObject} instance with the exception of paging
     * and sort constraints and methods that modify internal state of the
     * transfer object.
     * 
     * <p>
     * 
     * Use this method after receiving the original {@link CriteriaTransferObject}
     * instance from the client prior to conversion, for example:
     * 
     * <pre>
     * PersistentEntityCriteria countCriteria = converter.convert(
     *     new CriteriaTransferObjectCountWrapper(transferObject).wrap(),
     *     myMappingGroup);
     * 
     * int totalRecords = myDao.count(countCriteria);
     * </pre>
     * 
     * @return {@link CriteriaTransferObject} instance suitable for entity
     * instance count methods.
     */
    @SuppressWarnings("serial")
    public CriteriaTransferObject wrap() {
        CriteriaTransferObject transferObjectForCount = new CriteriaTransferObject() {
            
            @Override
            public FilterAndSortCriteria get(String propertyId) {
                final FilterAndSortCriteria transferObjectCriteria = transferObject.get(propertyId);
                
                FilterAndSortCriteria criteriaForCount = new FilterAndSortCriteria(propertyId) {
                    
                    @Override
                    public String getPropertyId() {
                        return transferObjectCriteria.getPropertyId();
                    }
                    
                    @Override
                    public String[] getFilterValues() {
                        return transferObjectCriteria.getFilterValues();
                    }
                    
                    @Override
                    public Boolean getSortAscending() {
                        return null;
                    }
                    
                    @Override
                    public Boolean getIgnoreCase() {
                        return null;
                    }
                    
                };
                
                return criteriaForCount;
            }
            
            @Override
            public Integer getFirstResult() {
                return null;
            }
            
            @Override
            public Integer getMaxResults() {
                return null;
            }
            
            @Override
            public Set<String> getPropertyIdSet() {
                return transferObject.getPropertyIdSet();
            }
            
        };
        
        return transferObjectForCount;
    }
    
    /**
     * @see #wrap()
     */
    public static CriteriaTransferObject wrap(CriteriaTransferObject cto) {
        return new CriteriaTransferObjectCountWrapper(cto).wrap();
    }
    
}
