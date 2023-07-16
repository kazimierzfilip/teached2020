package com.teachedapp.repository;

import com.teachedapp.dao.Subject;
import com.teachedapp.respository.SubjectRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubjectRepositoryTest {

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void findAllSubjectContainingTechnologyAndIgnoreCase() {

        List<Subject> subjectList1 = subjectRepository.findByNameContainingIgnoreCase("technology");
        List<Subject> subjectList2 = subjectRepository.findByNameContainingIgnoreCase("Technology");
        Assert.assertEquals(subjectList1, subjectList2);

    }
}
