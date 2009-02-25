package com.anasoft.os.daofusion.test.example.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

@Entity
@Table(name = "countries")
public class Country extends PersistentEnumeration {

    private static final long serialVersionUID = 7043400894861357048L;
	
}
