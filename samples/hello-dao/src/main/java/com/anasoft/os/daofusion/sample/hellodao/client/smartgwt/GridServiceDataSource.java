package com.anasoft.os.daofusion.sample.hellodao.client.smartgwt;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.anasoft.os.daofusion.cto.client.FilterAndSortCriteria;
import com.anasoft.os.daofusion.sample.hellodao.client.service.GridService;
import com.anasoft.os.daofusion.sample.hellodao.client.service.GridServiceAsync;
import com.anasoft.os.daofusion.sample.hellodao.client.service.ResultSet;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.rpc.RPCResponse;
import com.smartgwt.client.types.CriteriaPolicy;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 * Generic GWT RPC data source that supports common
 * grid-like operations via {@link GridService}.
 */
public abstract class GridServiceDataSource<DTO extends IsSerializable, SERVICE extends GridServiceAsync<DTO>>
        extends GwtRpcDataSource {
    
    private final SERVICE service;
    
    public GridServiceDataSource(SERVICE service, DataSourceField... fields) {
        this.service = service;
        
        for (DataSourceField f : fields)
            addField(f);
        
        // record cache is dropped whenever grid criteria changes
        setCriteriaPolicy(CriteriaPolicy.DROPONCHANGE);
    }
    
    /**
     * Transforms the given <tt>request</tt> into
     * {@link CriteriaTransferObject} instance.
     * <p>
     * We are doing this because we can apply seamless
     * CTO-to-criteria conversions back on the server.
     */
    @SuppressWarnings("unchecked")
    private CriteriaTransferObject getCto(DSRequest request) {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        
        // paging
        cto.setFirstResult(request.getStartRow());
        cto.setMaxResults(request.getEndRow() - request.getStartRow());
        
        // sort
        String sortBy = request.getSortBy();
        if (sortBy != null) {
            String sortPropertyId;
            boolean sortAscending = true;
            
            if (sortBy.startsWith("-")) {
                sortPropertyId = sortBy.substring(1);
                sortAscending = false;
            } else {
                sortPropertyId = sortBy;
            }
            
            FilterAndSortCriteria sortCriteria = cto.get(sortPropertyId);
            sortCriteria.setSortAscending(sortAscending);
        }
        
        // filter
        Map<String, String> filterData = request.getCriteria().getValues();
        Set<String> filterFieldNames = filterData.keySet();
        for (String fieldName : filterFieldNames) {
            FilterAndSortCriteria filterCriteria = cto.get(fieldName);
            filterCriteria.setFilterValue(filterData.get(fieldName));
        }
        
        return cto;
    }
    
    public static enum GridOperationType {
        FETCH, ADD, UPDATE, REMOVE
    }
    
    protected void onError(GridOperationType opType, String requestId,
            DSRequest request, DSResponse response, Throwable caught) {
        response.setStatus(RPCResponse.STATUS_FAILURE);
        processResponse(requestId, response);
        
        // show a dialog with error message
        SC.warn("<b>" + opType.name()
                + "</b><br/><br/>Error while processing RPC request:<br/><br/>"
                + caught.getMessage(), null);
    }
    
    /**
     * RPC callback adapter for common error handling.
     */
    private abstract class GridServiceAsyncCallback<T> implements AsyncCallback<T> {
        
        private final GridOperationType opType;
        private final String requestId;
        private final DSRequest request;
        private final DSResponse response;
        
        public GridServiceAsyncCallback(GridOperationType opType, String requestId,
                DSRequest request, DSResponse response) {
            this.opType = opType;
            this.requestId = requestId;
            this.request = request;
            this.response = response;
        }
        
        public void onFailure(Throwable caught) {
            onError(opType, requestId, request, response, caught);
        }
        
    }
    
    @Override
    protected void executeFetch(final String requestId,
            final DSRequest request, final DSResponse response) {
        service.fetch(getCto(request), new GridServiceAsyncCallback<ResultSet<DTO>>(
                GridOperationType.FETCH, requestId, request, response) {
                    public void onSuccess(ResultSet<DTO> result) {
                        List<DTO> resultList = result.getResultList();
                        ListGridRecord[] recordList = new ListGridRecord[resultList.size()];
                        
                        for (int i = 0; i < recordList.length; i++) {
                            ListGridRecord record = new ListGridRecord();
                            copyValues(resultList.get(i), record);
                            recordList[i] = record;
                        }
                        
                        response.setData(recordList);
                        response.setTotalRows(result.getTotalRecords());
                        processResponse(requestId, response);
                    }
        });
    }
    
    @Override
    protected void executeAdd(final String requestId, final DSRequest request,
            final DSResponse response) {
        // retrieve record which should be added
        JavaScriptObject data = request.getData();
        ListGridRecord record = new ListGridRecord(data);
        DTO dto = newDtoInstance();
        copyValues(record, dto);
        
        service.add(dto, new GridServiceAsyncCallback<DTO>(
                GridOperationType.ADD, requestId, request, response) {
                    public void onSuccess(DTO result) {
                        ListGridRecord[] list = new ListGridRecord[1];
                        ListGridRecord newRecord = new ListGridRecord();
                        copyValues(result, newRecord);
                        list[0] = newRecord;
                        
                        response.setData(list);
                        processResponse(requestId, response);
                    }
                });
    }
    
    @Override
    protected void executeUpdate(final String requestId,
            final DSRequest request, final DSResponse response) {
        // retrieve record which should be updated
        JavaScriptObject data = request.getData();
        ListGridRecord record = new ListGridRecord(data);
        
        // find grid
        ListGrid grid = (ListGrid) Canvas.getById(request.getComponentId());
        
        // get record with old and new values combined
        int index = grid.getRecordIndex(record);
        record = (ListGridRecord) grid.getEditedRecord(index);
        DTO dto = newDtoInstance();
        copyValues(record, dto);
        
        service.update(dto, new GridServiceAsyncCallback<DTO>(
                GridOperationType.UPDATE, requestId, request, response) {
                    public void onSuccess(DTO result) {
                        ListGridRecord[] list = new ListGridRecord[1];
                        ListGridRecord updRec = new ListGridRecord();
                        copyValues(result, updRec);
                        list[0] = updRec;
                        
                        response.setData(list);
                        processResponse(requestId, response);
                    }
                });
    }
    
    @Override
    protected void executeRemove(final String requestId,
            final DSRequest request, final DSResponse response) {
        // retrieve record which should be removed
        JavaScriptObject data = request.getData();
        final ListGridRecord record = new ListGridRecord(data);
        DTO dto = newDtoInstance();
        copyValues(record, dto);
        
        service.remove(dto, new GridServiceAsyncCallback<Void>(
                GridOperationType.REMOVE, requestId, request, response) {
                    public void onSuccess(Void result) {
                        ListGridRecord[] list = new ListGridRecord[1];
                        // we do not receive removed record from server,
                        // so we return record from the request
                        list[0] = record;
                        
                        response.setData(list);
                        processResponse(requestId, response);
                    }
                });
    }
    
    protected abstract DTO newDtoInstance();
    
    protected abstract void copyValues(ListGridRecord from, DTO to);
    
    protected abstract void copyValues(DTO from, ListGridRecord to);
    
}
