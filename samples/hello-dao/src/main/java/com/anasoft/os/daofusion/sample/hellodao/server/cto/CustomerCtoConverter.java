package com.anasoft.os.daofusion.sample.hellodao.server.cto;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.cto.server.FilterAndSortMapping;
import com.anasoft.os.daofusion.cto.server.NestedPropertyCriteriaBasedConverter;
import com.anasoft.os.daofusion.sample.hellodao.client.smartgwt.CustomerDataSource;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.ContactDetails;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Customer;
import com.anasoft.os.daofusion.util.FilterValueConverters;

/**
 * CTO converter for the {@link Customer} entity.
 */
public class CustomerCtoConverter extends NestedPropertyCriteriaBasedConverter {

    public static final String GROUP_NAME = "customer";
    
    public CustomerCtoConverter() {
        // direct properties
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._FIRST_NAME,
                AssociationPath.ROOT, Customer._FIRST_NAME);
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._LAST_NAME,
                AssociationPath.ROOT, Customer._LAST_NAME);
        
        // collection property
        addCollectionSizeEqMapping(GROUP_NAME, CustomerDataSource._TOTAL_ORDERS,
                AssociationPath.ROOT, Customer._ORDERS);
        
        // nested properties
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._CONTACT_EMAIL,
                Customer.CONTACT_DETAILS, ContactDetails._EMAIL);
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._CONTACT_PHONE,
                Customer.CONTACT_DETAILS, ContactDetails._PHONE);
    }
    
    protected void addStringLikeMapping(String mappingGroupName, String propertyId,
            AssociationPath associationPath, String targetPropertyName) {
        addMapping(mappingGroupName, new FilterAndSortMapping<String>(
                propertyId, associationPath, targetPropertyName,
                FilterCriterionProviders.LIKE, FilterValueConverters.STRING));
    }
    
    protected void addCollectionSizeEqMapping(String mappingGroupName, String propertyId,
            AssociationPath associationPath, String targetPropertyName) {
        addMapping(mappingGroupName, new FilterAndSortMapping<Integer>(
                propertyId, associationPath, targetPropertyName,
                FilterCriterionProviders.COLLECTION_SIZE_EQ, FilterValueConverters.INTEGER));
    }
    
}
