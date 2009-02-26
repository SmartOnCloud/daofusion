package com.anasoft.os.daofusion.test.example.dao;

import org.springframework.stereotype.Component;

import com.anasoft.os.daofusion.test.example.enums.PaymentType;

@Component
public class PaymentTypeDao extends EntityManagerAwareEnumerationDao<PaymentType> {

    public Class<PaymentType> getEntityClass() {
        return PaymentType.class;
    }
    
}
