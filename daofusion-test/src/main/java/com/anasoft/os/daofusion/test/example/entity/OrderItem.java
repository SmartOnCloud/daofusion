package com.anasoft.os.daofusion.test.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "order_items")
public class OrderItem extends OidBasedMutablePersistentEntity {

    private static final long serialVersionUID = -4404462759771270182L;
    
    @OneToOne(optional = false)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    private StockItem stockItem;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Order order;
    
    public StockItem getStockItem() {
        return stockItem;
    }
    
    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Order getOrder() {
        return order;
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public int getTotalPrice() {
        return stockItem.getPrice() * quantity;
    }
    
}
