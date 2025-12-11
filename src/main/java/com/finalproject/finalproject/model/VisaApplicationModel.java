package com.finalproject.finalproject.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "visa_application")
public class VisaApplicationModel {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "state")
    private Short state;

    @Column(name = "name_owner")
    private String nameOwner;

    @Column(name = "passport_owner")
    private String passportOwner;

    @Column(name = "name_migrant")
    private String nameMigrant;

    @Column(name = "passport_migrant")
    private String passportMigrant;

    @Column(name = "citizenship_migrant")
    private String citizenshipMigrant;

    @Column(name = "address")
    private String address;

    @Column(name = "comment")
    private String comment;

    public VisaApplicationModel() {

    }

    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
    }
    public void setUserId(Integer _user_id) {
        this.userId = _user_id;
    }

    public Short getState() {
        return this.state;
    }
    public void setState(Short _state) {
        this.state = _state;
    }

    public String getNameOwner() {
        return this.nameOwner;
    }
    public void setNameOwner(String _nameOwner) {
        this.nameOwner = _nameOwner;
    }

    public String getPassportOwner() {
        return this.passportOwner;
    }
    public void setPassportOwner(String _passportOwner) {
        this.passportOwner = _passportOwner;
    }

    public String getNameMigrant() {
        return this.nameMigrant;
    }
    public void setNameMigrant(String _nameMigrant) {
        this.nameMigrant = _nameMigrant;
    }

    public String getPassportMigrant() {
        return this.passportMigrant;
    }
    public void setPassportMigrant(String _passportMigrant) {
        this.passportMigrant = _passportMigrant;
    }

    public String getCitizenshipMigrant() {
        return this.citizenshipMigrant;
    }
    public void setCitizenshipMigrant(String _citizenshipMigrant) {
        this.citizenshipMigrant = _citizenshipMigrant;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String _address) {
        this.address = _address;
    }

    public String getComment() {
        return this.comment;
    }
    public void setComment(String _comment) {
        this.comment = _comment;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", userId=" + userId + ", state=" + state + ", comment=" + comment + "]";
    }
}
