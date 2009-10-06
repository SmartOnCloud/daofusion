package com.anasoft.os.sample.dmf.service.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends OidBasedMutablePersistentEntity {

    public static final int NAME_LENGTH = 16;
    public static final String _NAME = "name";
    public static final String NAME_PID = _NAME;
    
    public static final String _DESCRIPTION = "description";
    public static final String DESCRIPTION_PID = _DESCRIPTION;
    
    public static final String _PRICE = "price";
    public static final String PRICE_PID = _PRICE;
    
    @Column(nullable = false, length = NAME_LENGTH)
    private String name;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private Integer price;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
}
