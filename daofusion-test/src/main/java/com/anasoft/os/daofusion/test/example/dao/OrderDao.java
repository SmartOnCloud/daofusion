package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.entity.Order;

@Component
public class OrderDao extends EntityManagerAwareEntityDao<Order, Long> {

    public Class<Order> getEntityClass() {
        return Order.class;
    }
    
}
