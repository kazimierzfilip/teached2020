package com.teachedapp.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {

    @Column(length = 40)
    private String country;

    @Column(length = 40)
    private String state;

    @Column(length = 40)
    private String city;

    public Address() {}

    public Address(String country, String state, String city) {
        this.country = country;
        this.state = state;
        this.city = city;
    }

}
