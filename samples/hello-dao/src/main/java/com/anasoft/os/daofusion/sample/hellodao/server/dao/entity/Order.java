package com.anasoft.os.daofusion.sample.hellodao.server.dao.entity;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "orders")
public class Order extends OidBasedMutablePersistentEntity {
    
    private static final long serialVersionUID = 9166884244779318455L;
    
    public static final String _CREATION_DATE = "creationDate";
    public static final String _SHIPPING_ADDRESS = "shippingAddress";
    public static final String _BILLING_ADDRESS = "billingAddress";
    public static final String _COMPLETE = "complete";
    public static final String _CUSTOMER = "customer";
    public static final String _DESCRIPTION = "description";
    
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "city", column = @Column(name = "shipping_city"))})
    @AssociationOverrides({
        @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "shipping_country"))})
    private Address shippingAddress;
    
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "city", column = @Column(name = "billing_city"))})
    @AssociationOverrides({
        @AssociationOverride(name = "country", joinColumns = @JoinColumn(name = "billing_country"))})
    private Address billingAddress;
    
    @Column(nullable = false)
    private Boolean complete;
    
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Customer customer;
    
    @Column
    private String description;
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    public Address getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public Address getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public Boolean getComplete() {
        return complete;
    }
    
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
}
