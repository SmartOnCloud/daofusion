package com.anasoft.os.daofusion.test.example.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Customer extends OidBasedMutablePersistentEntity {

    private static final long serialVersionUID = -509237572262823489L;
    
    @Column(nullable = false, length = 16)
    private String firstName;
    
    @Column(nullable = false, length = 16)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @OneToMany(mappedBy = "customer")
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<Order> orders = new ArrayList<Order>();
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    protected List<Order> getOrders() {
        return orders;
    }
    
    protected void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }
    
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }
    
    public List<Order> getUnmodifiableOrderList() {
        return Collections.unmodifiableList(orders);
    }
    
}
