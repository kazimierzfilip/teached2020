package com.teachedapp;

import com.teachedapp.respository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TeachedappApplicationTests {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void contextLoads() {
        assertThat(accountRepository).isNotNull();
        assertThat(administratorRepository).isNotNull();
        assertThat(courseRepository).isNotNull();
        assertThat(lessonRepository).isNotNull();
        assertThat(paymentRepository).isNotNull();
        assertThat(studentRepository).isNotNull();
        assertThat(subjectRepository).isNotNull();
        assertThat(teacherRepository).isNotNull();
    }

}
