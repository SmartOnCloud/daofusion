package com.anasoft.os.daofusion.sample.hellodao.server.dao.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.anasoft.os.daofusion.criteria.AssociationPath;
import com.anasoft.os.daofusion.criteria.AssociationPathElement;

@Entity
@Table(name = "customers", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Customer extends OidBasedMutablePersistentEntity {
    
    private static final long serialVersionUID = -7700978135371817765L;
    
    public static final String _FIRST_NAME = "firstName";
    public static final String _LAST_NAME = "lastName";
    public static final String _ORDERS = "orders";
    public static final String _CONTACT_DETAILS = "contactDetails";
    
    public static final AssociationPath CONTACT_DETAILS = new AssociationPath(
            new AssociationPathElement(_CONTACT_DETAILS));
    
    @Column(nullable = false, length = 16)
    private String firstName;
    
    @Column(nullable = false, length = 16)
    private String lastName;
    
    @OneToMany(mappedBy = "customer")
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private List<Order> orders = new ArrayList<Order>();
    
    @OneToOne(optional = false)
    @Cascade(value = {CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private ContactDetails contactDetails;
    
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
    
    public ContactDetails getContactDetails() {
        return contactDetails;
    }
    
    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }
    
}
