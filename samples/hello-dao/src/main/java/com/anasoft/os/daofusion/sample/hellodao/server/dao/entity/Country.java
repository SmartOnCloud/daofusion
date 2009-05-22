package com.anasoft.os.daofusion.sample.hellodao.server.dao.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

@Entity
@Table(name = "countries")
public class Country extends PersistentEnumeration {
    
    private static final long serialVersionUID = 6229061143207795718L;
	
}
