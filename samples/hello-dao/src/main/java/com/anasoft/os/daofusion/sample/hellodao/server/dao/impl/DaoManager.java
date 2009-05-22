package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import com.anasoft.os.daofusion.sample.hellodao.server.dao.CountryDao;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.CustomerDao;

/**
 * This class holds static references to our DAOs.
 * <p>
 * For serious applications, we recommend using Spring
 * for managing your DAOs as well as other application
 * beans.
 */
public final class DaoManager {
    
    private static final CustomerDao customerDao = new CustomerDaoImpl();
    private static final CountryDao countryDao = new CountryDaoImpl();
    
    private DaoManager() {
    }
    
    public static CustomerDao getCustomerDao() {
        return customerDao;
    }
    
    public static CountryDao getCountryDao() {
        return countryDao;
    }
    
}
