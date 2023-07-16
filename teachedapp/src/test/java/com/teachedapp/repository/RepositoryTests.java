package com.teachedapp.repository;

import com.teachedapp.dao.Subject;
import com.teachedapp.respository.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests {

    @Autowired
    SubjectRepository subjectRepository;

    @Test
    public void testCourseRepositoryCRUD() {
        Subject subject = new Subject();
        subject.setId(0);
        subject.setName("Maths");

        subjectRepository.save(subject);

        Optional<Subject> foundSubject = subjectRepository.findById(0);
        assertThat(foundSubject.isPresent());
        foundSubject.ifPresent(value -> assertThat(value).isEqualTo(subject));

        subjectRepository.delete(subject);
        assertThat(subjectRepository.findById(0).isPresent()).isFalse();
    }
}
