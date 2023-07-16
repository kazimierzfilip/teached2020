package com.teachedapp.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {

    @Id
    private Integer id;

    @MapsId
    @OneToOne(optional=false, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", referencedColumnName = "account_id")
    @RestResource(exported=false)
    private Account account;

    private String description;

    @OneToMany(mappedBy = "teacher")
    List<Course> courses;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "teacher_subject_assignment",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")})
    private List<Subject> subjects;

}
