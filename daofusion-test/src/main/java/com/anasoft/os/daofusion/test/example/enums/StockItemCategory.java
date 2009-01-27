package com.anasoft.os.daofusion.test.example.enums;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

@Entity
@Table(name = "stock_item_categories")
public class StockItemCategory extends PersistentEnumeration {

    private static final long serialVersionUID = -8367685978206665534L;
    
}
