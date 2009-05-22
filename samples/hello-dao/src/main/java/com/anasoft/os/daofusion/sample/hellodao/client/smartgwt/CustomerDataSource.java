package com.anasoft.os.daofusion.sample.hellodao.client.smartgwt;

import com.anasoft.os.daofusion.sample.hellodao.client.dto.CustomerDto;
import com.anasoft.os.daofusion.sample.hellodao.client.service.CustomerService;
import com.anasoft.os.daofusion.sample.hellodao.client.service.CustomerServiceAsync;
import com.google.gwt.core.client.GWT;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Data source for managing {@link CustomerDto customer}
 * data via {@link CustomerService}.
 */
public class CustomerDataSource extends GridServiceDataSource<CustomerDto, CustomerServiceAsync> {
    
    public static final String _ID = "id";
    public static final String _FIRST_NAME = "firstName";
    public static final String _LAST_NAME = "lastName";
    public static final String _TOTAL_ORDERS = "totalOrders";
    public static final String _CONTACT_EMAIL = "contactEmail";
    public static final String _CONTACT_PHONE = "contactPhone";
    
    private static final CustomerServiceAsync service = GWT.create(CustomerService.class);
    
    // data source field definitions
    private static final DataSourceField id = new DataSourceTextField(_ID);
    private static final DataSourceField firstName = new DataSourceTextField(_FIRST_NAME);
    private static final DataSourceField lastName = new DataSourceTextField(_LAST_NAME);
    private static final DataSourceField totalOrders = new DataSourceIntegerField(_TOTAL_ORDERS);
    private static final DataSourceField contactEmail = new DataSourceTextField(_CONTACT_EMAIL);
    private static final DataSourceField contactPhone = new DataSourceTextField(_CONTACT_PHONE);
    
    static {
        id.setCanEdit(false);
        id.setPrimaryKey(true);
        totalOrders.setCanEdit(false);
    }
    
    public CustomerDataSource() {
        super(service, id, firstName, lastName, totalOrders, contactEmail, contactPhone);
    }
    
    protected void copyValues(ListGridRecord from, CustomerDto to) {
        to.setId(from.getAttributeAsString(_ID));
        to.setFirstName(from.getAttributeAsString(_FIRST_NAME));
        to.setLastName(from.getAttributeAsString(_LAST_NAME));
        to.setTotalOrders(from.getAttributeAsInt(_TOTAL_ORDERS));
        to.setContactEmail(from.getAttributeAsString(_CONTACT_EMAIL));
        to.setContactPhone(from.getAttributeAsString(_CONTACT_PHONE));
    }
    
    protected void copyValues(CustomerDto from, ListGridRecord to) {
        to.setAttribute(_ID, from.getId());
        to.setAttribute(_FIRST_NAME, from.getFirstName());
        to.setAttribute(_LAST_NAME, from.getLastName());
        to.setAttribute(_TOTAL_ORDERS, from.getTotalOrders());
        to.setAttribute(_CONTACT_EMAIL, from.getContactEmail());
        to.setAttribute(_CONTACT_PHONE, from.getContactPhone());
    }
    
    protected CustomerDto newDtoInstance() {
        return new CustomerDto();
    }
    
}
