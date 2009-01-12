package com.anasoft.os.daofusion.test.example.dao;

import javax.persistence.PersistenceContext;

import org.hibernate.ejb.HibernateEntityManager;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerHolder {

    @PersistenceContext
    private HibernateEntityManager entityManager;
    
    public HibernateEntityManager getEntityManager() {
        return entityManager;
    }
    
    public void setEntityManager(HibernateEntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
