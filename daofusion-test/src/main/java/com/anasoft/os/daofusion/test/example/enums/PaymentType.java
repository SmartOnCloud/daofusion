package com.anasoft.os.daofusion.test.example.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

@Entity
@Table(name = "payment_types")
public class PaymentType extends PersistentEnumeration {

    private static final long serialVersionUID = -2456472110859924455L;
    
}
