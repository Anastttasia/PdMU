package com.finalproject.finalproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "owners")
public class OwnerModel {

    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name")
    private String name;

    @Column(name = "passport")
    private String passport;

    @Column(name = "address")
    private String address;

    public OwnerModel()
    {
    }

    public OwnerModel
            (
                    Integer _userId,
                    String _name,
                    String _passport,
                    String _address
            )
    {
        this.userId = _userId;
        this.name = _name;
        this.passport = _passport;
        this.address = _address;
    }

    public Integer getUserId() { return this.userId; }

    public String getName() {
        return this.name;
    }
    public void setName(String _name) {
        this.name = _name;
    }

    public String getPassport() {
        return this.passport;
    }
    public void setPassport(String _passport) {
        this.passport = _passport;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String _address) {
        this.address = _address;
    }
}
