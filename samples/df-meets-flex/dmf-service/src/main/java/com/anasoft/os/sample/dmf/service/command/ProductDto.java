package com.anasoft.os.sample.dmf.service.command;

import com.anasoft.os.sample.dmf.service.DataTransferObject;
import com.anasoft.os.sample.dmf.service.dao.entity.Product;

public class ProductDto implements DataTransferObject {

    private String name;
    private String description;
    private Integer price;
    
    public ProductDto(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }
    
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
