package com.anasoft.os.daofusion.test.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.anasoft.os.daofusion.test.example.enums.StockItemCategory;

@Entity
@Table(name = "stock_items")
@Inheritance(strategy = InheritanceType.JOINED)
public class StockItem extends OidBasedMutablePersistentEntity {

    private static final long serialVersionUID = 5069562477223244609L;
    
    @Column(nullable = false, unique = true, length = 64)
    private String name;
    
    @Column
    private String description;
    
    @Column(nullable = false)
    private Integer price;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private StockItemCategory category;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public StockItemCategory getCategory() {
        return category;
    }
    
    public void setCategory(StockItemCategory category) {
        this.category = category;
    }
    
}
