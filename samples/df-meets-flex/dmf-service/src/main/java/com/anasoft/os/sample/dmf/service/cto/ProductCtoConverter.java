package com.anasoft.os.sample.dmf.service.cto;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.sample.dmf.service.dao.entity.Product;

@Component
public class ProductCtoConverter extends AbstractCtoConverter {

    public static final String GROUP_NAME = "Product";
    
    public ProductCtoConverter() {
        super(GROUP_NAME);
        
        addStringMapping(Product.NAME_PID, AssociationPath.ROOT, Product._NAME);
        addStringMapping(Product.DESCRIPTION_PID, AssociationPath.ROOT, Product._DESCRIPTION);
        addIntegerMapping(Product.PRICE_PID, AssociationPath.ROOT, Product._PRICE);
    }
    
}
