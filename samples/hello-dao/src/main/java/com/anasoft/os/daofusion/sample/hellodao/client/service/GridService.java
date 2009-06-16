package com.anasoft.os.daofusion.sample.hellodao.client.service;

import com.anasoft.os.daofusion.cto.client.CriteriaTransferObject;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Generic service interface defining common grid-like operations.
 */
public interface GridService<DTO extends IsSerializable> extends RemoteService {
    
    ResultSet<DTO> fetch(CriteriaTransferObject cto) throws ServiceException;
    
    DTO add(DTO dto) throws ServiceException;
    
    DTO update(DTO dto) throws ServiceException;
    
    void remove(DTO dto) throws ServiceException;
    
}
