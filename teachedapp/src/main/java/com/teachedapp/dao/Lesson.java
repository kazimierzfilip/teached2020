package com.teachedapp.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lesson")
@SequenceGenerator(name = "lesson_generator", sequenceName = "lesson_sequence", allocationSize = 1)
@Data
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_generator")
    @Column(name = "lesson_id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private LessonStatus status;

    @Column
    private String notes;

    @Column
    private Double duration; // in hours

    @Column
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment payment;

    @Column(name = "student_paid")
    private Boolean studentPaid;

    @Column(name = "teacher_was_paid")
    private Boolean teacherWasPaid;

}