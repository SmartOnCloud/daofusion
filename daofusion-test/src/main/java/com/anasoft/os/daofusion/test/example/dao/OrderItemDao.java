package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.entity.OrderItem;

@Component
public class OrderItemDao extends EntityManagerAwareEntityDao<OrderItem, Long> {

    public Class<OrderItem> getEntityClass() {
        return OrderItem.class;
    }
    
}
