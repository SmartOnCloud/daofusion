package com.anasoft.os.sample.dmf.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.annotation.Secured;
import org.springframework.security.vote.AuthenticatedVoter;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.anasoft.os.daofusion.PersistentEntityDao;
import com.anasoft.os.sample.dmf.service.EntityFetchCommand;
import com.anasoft.os.sample.dmf.service.RemoteServiceException;
import com.anasoft.os.sample.dmf.service.cto.AbstractCtoConverter;
import com.anasoft.os.sample.dmf.service.cto.ProductCtoConverter;
import com.anasoft.os.sample.dmf.service.dao.ProductDao;
import com.anasoft.os.sample.dmf.service.dao.entity.Product;

@Configurable
public class GetProducts extends EntityFetchCommand<GotProducts, ProductDto, Product> {

    @Autowired
    private ProductDao productDao;
    
    @Autowired
    private ProductCtoConverter ctoConverter;
    
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Secured(AuthenticatedVoter.IS_AUTHENTICATED_ANONYMOUSLY)
    public GotProducts execute() throws RemoteServiceException {
        GotProducts result = new GotProducts();
        updateResult(result);
        
        return result;
    }
    
    @Override
    protected ProductDto convertEntity(Product entity) {
        return new ProductDto(entity);
    }
    
    @Override
    protected AbstractCtoConverter getCtoConverter() {
        return ctoConverter;
    }
    
    @Override
    protected PersistentEntityDao<Product, ?> getDao() {
        return productDao;
    }
    
}
