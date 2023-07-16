package com.teachedapp.dao;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "student")
@Data
public class Student {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(optional=false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "student_id", referencedColumnName = "account_id")
    @RestResource(exported=false)
    private Account account;

    private BigDecimal budget;

    @OneToMany(mappedBy = "student")
    List<Course> courses;

}

