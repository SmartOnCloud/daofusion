package com.anasoft.os.daofusion.sample.hellodao.server.dao;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Customer;

public interface CustomerDao extends PersistentEntityDao<Customer, Long> {
    
    // add some business related methods here
    // (don't forget to integration-test them too)
    
}
