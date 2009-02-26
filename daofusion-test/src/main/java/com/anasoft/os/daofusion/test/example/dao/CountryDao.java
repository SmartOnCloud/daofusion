package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.enums.Country;

@Component
public class CountryDao extends EntityManagerAwareEnumerationDao<Country> {

    public Class<Country> getEntityClass() {
        return Country.class;
    }
    
}
