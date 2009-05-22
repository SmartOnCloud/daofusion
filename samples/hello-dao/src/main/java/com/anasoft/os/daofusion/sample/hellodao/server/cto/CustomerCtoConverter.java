package com.anasoft.os.daofusion.sample.hellodao.server.cto;

import com.anasoft.os.daofusion.cto.server.FilterAndSortMapping;
import com.anasoft.os.daofusion.cto.server.FilterValueObjectProvider;
import com.anasoft.os.daofusion.cto.server.NestedPropertyCriteriaBasedConverter;
import com.anasoft.os.daofusion.sample.hellodao.client.smartgwt.CustomerDataSource;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.ContactDetails;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Customer;

/**
 * CTO converter for the {@link Customer} entity.
 */
public class CustomerCtoConverter extends NestedPropertyCriteriaBasedConverter {

    public static final String GROUP_NAME = "customer";
    
    public CustomerCtoConverter() {
        // direct properties
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._FIRST_NAME, Customer._FIRST_NAME);
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._LAST_NAME, Customer._LAST_NAME);
        
        // collection property
        addCollectionSizeEqMapping(GROUP_NAME, CustomerDataSource._TOTAL_ORDERS, Customer._ORDERS);
        
        // nested properties
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._CONTACT_EMAIL,
                Customer._CONTACT_DETAILS + "." + ContactDetails._EMAIL);
        addStringLikeMapping(GROUP_NAME, CustomerDataSource._CONTACT_PHONE,
                Customer._CONTACT_DETAILS + "." + ContactDetails._PHONE);
    }
    
    protected void addStringLikeMapping(String mappingGroupName, String propertyId, String propertyPath) {
        addMapping(mappingGroupName, new FilterAndSortMapping(propertyId,
                propertyPath, PropertyFilterCriterionProviders.LIKE,
                new FilterValueObjectProvider() {
                    public Object getObject(String stringValue) {
                        return stringValue;
                    }
                }));
    }
    
    protected void addCollectionSizeEqMapping(String mappingGroupName, String propertyId, String propertyPath) {
        addMapping(mappingGroupName, new FilterAndSortMapping(propertyId,
                propertyPath, PropertyFilterCriterionProviders.COLLECTION_SIZE_EQ,
                new FilterValueObjectProvider() {
                    public Object getObject(String stringValue) {
                        return Integer.valueOf(stringValue);
                    }
                }));
    }
    
}
