package com.anasoft.os.daofusion.sample.hellodao.client.service;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Asynchronous version of {@link GridService}.
 */
public interface GridServiceAsync<DTO extends IsSerializable> {
    
    void fetch(CriteriaTransferObject cto, AsyncCallback<ResultSet<DTO>> cb);
    
    void add(DTO dto, AsyncCallback<DTO> cb);
    
    void update(DTO dto, AsyncCallback<DTO> cb);
    
    void remove(DTO dto, AsyncCallback<Void> cb);
    
}
