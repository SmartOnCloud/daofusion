package com.anasoft.os.daofusion.test.example.enums;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.anasoft.os.daofusion.entity.PersistentEnumeration;

@Entity
@Table(name = "stock_item_categories")
public class StockItemCategory extends PersistentEnumeration {

    private static final long serialVersionUID = 6286254963548902347L;
    
    @Column
    private String description;
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
