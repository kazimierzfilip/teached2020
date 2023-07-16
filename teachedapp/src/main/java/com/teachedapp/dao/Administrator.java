package com.teachedapp.dao;

import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;

@Entity
@Table(name = "administrator")
@Data
public class Administrator {

    @Id
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "administrator_id", referencedColumnName = "account_id")
    @RestResource(exported=false)
    private Account account;

    private String department;

}
