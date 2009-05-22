package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import com.anasoft.os.daofusion.sample.hellodao.server.dao.CustomerDao;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Customer;

public class CustomerDaoImpl extends EntityManagerAwareEntityDao<Customer, Long>
        implements CustomerDao {
    
    // instances are created via DaoManager
    CustomerDaoImpl() {
        super();
    }
    
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }
    
}
