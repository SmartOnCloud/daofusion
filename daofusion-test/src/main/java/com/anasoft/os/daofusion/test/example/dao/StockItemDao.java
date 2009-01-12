package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.entity.StockItem;

@Component
public class StockItemDao extends EntityManagerAwareEntityDao<StockItem, Long> {

    public Class<StockItem> getEntityClass() {
        return StockItem.class;
    }
    
}
