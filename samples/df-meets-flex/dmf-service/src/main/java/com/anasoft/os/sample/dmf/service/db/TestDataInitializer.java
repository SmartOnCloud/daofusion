package com.anasoft.os.sample.dmf.service.db;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.anasoft.os.sample.dmf.service.dao.ProductDao;
import com.anasoft.os.sample.dmf.service.dao.entity.Product;

@Component
public class TestDataInitializer {

    @Autowired
    private ProductDao productDao;
    
    @PostConstruct
    @Transactional
    public void initData() {
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setName("Name " + i);
            product.setDescription("Desc " + i);
            product.setPrice(i * 10);
            
            productDao.saveOrUpdate(product);
        }
    }
    
}
