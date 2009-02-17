package com.anasoft.os.daofusion.test.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "promoted_stock_items")
public class PromotedStockItem extends StockItem {

    private static final long serialVersionUID = 8609176925977498187L;
    
    @Column(nullable = false)
    private Integer promotionPrice;
    
    public Integer getPromotionPrice() {
        return promotionPrice;
    }
    
    public void setPromotionPrice(Integer promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
    
    public int getItemDiscount() {
        return getPrice() - promotionPrice;
    }
    
}
