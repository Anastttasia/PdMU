package com.finalproject.finalproject.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
public class UserModel {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private Integer password_hash;

    public Integer getUserId() { return this.user_id; }

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

    @Override
    public String toString() {
        return "Product [user_id=" + user_id + ", email=" + email + ", password_hash=" + password_hash + "]";
    }
}
