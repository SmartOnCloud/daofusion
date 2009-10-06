package com.anasoft.os.sample.dmf.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.sample.dmf.service.EntityInsertCommand;
import com.anasoft.os.sample.dmf.service.RemoteServiceException;
import com.anasoft.os.sample.dmf.service.dao.ProductDao;
import com.anasoft.os.sample.dmf.service.dao.entity.Product;
import com.anasoft.os.sample.dmf.service.validation.Validated;
import com.anasoft.os.sample.dmf.service.validation.impl.Length;
import com.anasoft.os.sample.dmf.service.validation.impl.NotEmpty;

@Configurable
public class InsertProduct extends EntityInsertCommand<ProductInserted, ProductDto, Product> {

    @NotEmpty
    @Length(max = Product.NAME_LENGTH)
    private String name;
    
    @NotEmpty
    private String description;
    
    private Integer price;
    
    @Autowired
    private ProductDao productDao;
    
    @Transactional
    @Secured("ROLE_ADMIN")
    @Validated
    public ProductInserted execute() throws RemoteServiceException {
        ProductInserted result = new ProductInserted();
        updateResult(result);
        
        return result;
    }
    
    @Override
    protected ProductDto convertEntity(Product entity) {
        return new ProductDto(entity);
    }
    
    @Override
    protected Product createEntity() {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        
        return product;
    }
    
    @Override
    protected PersistentEntityDao<Product, ?> getDao() {
        return productDao;
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
