package com.finalproject.finalproject.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "admins")
public class AdminModel {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private Integer password_hash;

    public Integer getAdminId() { return this.adminId; }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String _email) {
        this.email = _email;
    }

    public Integer getPasswordHash() {
        return this.password_hash;
    }
    public void setPasswordHash(Integer _password_hash) {
        this.password_hash = _password_hash;
    }

}
