package com.anasoft.os.sample.dmf.service.cto;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.PersistentEntityCriteria;
import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.server.FilterAndSortMapping;
import com.anasoft.os.daofusion.cto.server.NestedPropertyCriteriaBasedConverter;
import com.anasoft.os.daofusion.util.FilterValueConverters;

public abstract class AbstractCtoConverter extends NestedPropertyCriteriaBasedConverter {

    private final String mappingGroupName;
    
    public AbstractCtoConverter(String mappingGroupName) {
        this.mappingGroupName = mappingGroupName;
    }
    
    public PersistentEntityCriteria convert(CriteriaTransferObject transferObject) {
        return convert(transferObject, mappingGroupName);
    }
    
    protected void addStringMapping(String propertyId, AssociationPath associationPath, String targetPropertyName) {
        addMapping(mappingGroupName, new FilterAndSortMapping<String>(
                propertyId, associationPath, targetPropertyName,
                FilterCriterionProviders.LIKE, FilterValueConverters.STRING));
    }
    
    protected void addIntegerMapping(String propertyId, AssociationPath associationPath, String targetPropertyName) {
        addMapping(mappingGroupName, new FilterAndSortMapping<Integer>(
                propertyId, associationPath, targetPropertyName,
                FilterCriterionProviders.EQ, FilterValueConverters.INTEGER));
    }
    
}
