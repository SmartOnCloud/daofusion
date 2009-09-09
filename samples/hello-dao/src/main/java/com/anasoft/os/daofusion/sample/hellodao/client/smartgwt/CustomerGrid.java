package com.anasoft.os.daofusion.sample.hellodao.client.smartgwt;

import com.anasoft.os.daofusion.sample.hellodao.client.dto.CustomerDto;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;

/**
 * {@link ListGrid} bound to {@link CustomerDataSource}
 * for showing {@link CustomerDto customer} data.
 */
public class CustomerGrid extends ListGrid {
    
    // grid field definitions
    private static final ListGridField id = new ListGridField(CustomerDataSource._ID, "Id", 50);
    private static final ListGridField firstName = new ListGridField(CustomerDataSource._FIRST_NAME, "First Name");
    private static final ListGridField lastName = new ListGridField(CustomerDataSource._LAST_NAME, "Last Name");
    private static final ListGridField totalOrders = new ListGridField(CustomerDataSource._TOTAL_ORDERS, "Total orders", 100);
    private static final ListGridField contactEmail = new ListGridField(CustomerDataSource._CONTACT_EMAIL, "Contact e-mail");
    private static final ListGridField contactPhone = new ListGridField(CustomerDataSource._CONTACT_PHONE, "Contact phone");
    
    public CustomerGrid() {
        super();
        
        setAlternateRecordStyles(true);
        setCanDragSelect(true);
        setSelectionType(SelectionStyle.MULTIPLE);
        
        setCanEdit(true);
        setEditEvent(ListGridEditEvent.DOUBLECLICK);
        setEditByCell(true);
        setAutoSaveEdits(false);
        
        setDataSource(new CustomerDataSource());
        setAutoFetchData(true);
        
        setShowFilterEditor(true);
        
        setFields(id, firstName, lastName, totalOrders, contactEmail, contactPhone);
        
        addRowContextClickHandler(new RowContextClickHandler() {
            public void onRowContextClick(RowContextClickEvent event) {
                SC.say("Secret context menu discovered", "Hello from DAO Fusion team :-)");
                
                // prevent default browser context menu
                event.cancel();
            }
        });
    }
    
}
