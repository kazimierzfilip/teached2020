package com.teachedapp.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class PersonalData {

    @Column(name = "first_name", length = 40)
    private String firstName;

    @Column(name = "last_name", length = 40)
    private String lastName;

    private Integer age;

    private Character sex;

    public PersonalData() {}

    public PersonalData(String firstName, String lastName, Integer age, Character sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

}
