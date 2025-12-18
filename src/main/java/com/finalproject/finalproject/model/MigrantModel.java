package com.finalproject.finalproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "migrants")
public class MigrantModel {


    @Id
    @Column(name = "passport")
    private String passport;

    @Column(name = "name")
    private String name;

    @Column(name = "citizenship")
    private String citizenship;

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}
