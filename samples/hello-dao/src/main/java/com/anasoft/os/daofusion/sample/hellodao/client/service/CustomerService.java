package com.anasoft.os.daofusion.sample.hellodao.client.service;

import com.anasoft.os.daofusion.sample.hellodao.client.HelloDAO;
import com.anasoft.os.daofusion.sample.hellodao.client.dto.CustomerDto;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service for managing {@link CustomerDto customer} data.
 */
@RemoteServiceRelativePath(HelloDAO.RPC_SERVICE_RELATIVE_PATH)
public interface CustomerService extends GridService<CustomerDto> {
    
    // add some business related methods here
    // (don't forget to test them too)
    
}
