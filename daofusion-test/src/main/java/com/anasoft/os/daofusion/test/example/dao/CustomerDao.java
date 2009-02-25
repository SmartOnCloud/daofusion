package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.entity.Customer;

@Component
public class CustomerDao extends EntityManagerAwareEntityDao<Customer, Long> {

    public Class<Customer> getEntityClass() {
        return Customer.class;
    }
    
}
