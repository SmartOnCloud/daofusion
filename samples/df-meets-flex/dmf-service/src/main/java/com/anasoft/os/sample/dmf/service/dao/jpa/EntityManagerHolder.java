package com.anasoft.os.sample.dmf.service.dao.jpa;

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
