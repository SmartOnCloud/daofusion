package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.enums.StockItemCategory;

@Component
public class StockItemCategoryDao extends EntityManagerAwareEnumerationDao<StockItemCategory> {

    public Class<StockItemCategory> getEntityClass() {
        return StockItemCategory.class;
    }
    
}
