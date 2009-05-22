package com.anasoft.os.daofusion.sample.hellodao.server.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "contact_details")
public class ContactDetails extends OidBasedMutablePersistentEntity {
    
    private static final long serialVersionUID = 6157698532821804689L;
    
    public static final String _EMAIL = "email";
    public static final String _PHONE = "phone";
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
