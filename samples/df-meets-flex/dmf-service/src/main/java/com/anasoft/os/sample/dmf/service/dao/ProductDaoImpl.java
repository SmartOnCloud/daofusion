package com.anasoft.os.sample.dmf.service.dao;

import org.springframework.stereotype.Repository;

import com.anasoft.os.sample.dmf.service.dao.entity.Product;
import com.anasoft.os.sample.dmf.service.dao.jpa.EntityManagerAwareEntityDao;

@Repository
public class ProductDaoImpl extends EntityManagerAwareEntityDao<Product, Long>
        implements ProductDao {

    public Class<Product> getEntityClass() {
        return Product.class;
    }
    
}
