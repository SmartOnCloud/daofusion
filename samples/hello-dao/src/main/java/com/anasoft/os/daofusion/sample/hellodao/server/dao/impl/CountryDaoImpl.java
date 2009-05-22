package com.anasoft.os.daofusion.sample.hellodao.server.dao.impl;

import com.anasoft.os.daofusion.sample.hellodao.server.dao.CountryDao;
import com.anasoft.os.daofusion.sample.hellodao.server.dao.entity.Country;

public class CountryDaoImpl extends EntityManagerAwareEnumerationDao<Country>
        implements CountryDao {
    
    // instances are created via DaoManager
    CountryDaoImpl() {
        super();
    }
    
    public Class<Country> getEntityClass() {
        return Country.class;
    }
    
}
