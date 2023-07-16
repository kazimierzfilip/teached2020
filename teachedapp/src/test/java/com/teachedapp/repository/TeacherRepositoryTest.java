package com.teachedapp.repository;

import com.teachedapp.dao.*;
import com.teachedapp.respository.AccountRepository;
import com.teachedapp.respository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void addTeacher() {
        Account teacher1Account = new Account();
        teacher1Account.setEmail("multilanguage.teacher1@mail.com");
        teacher1Account.setLogin("MultilanguageTeacher1");
        teacher1Account.setPassword("P@ssw0rd1");
        teacher1Account.setStatus(AccountStatus.ACTIVE);
        teacher1Account.setPersonalData(new PersonalData("Tomasz", "Kowalski", 30, 'M'));
        teacher1Account.setAddress(new Address("Poland", "wielkopolskie", "Poznań"));
        accountRepository.save(teacher1Account);
        Teacher teacher1 = new Teacher();
        teacher1.setAccount(teacher1Account);
        teacher1.setDescription("I love languages.");
        teacherRepository.save(teacher1);

        Optional<Teacher> foundTeacher = teacherRepository.findById(teacher1Account.getId());
        assertThat(foundTeacher.isPresent());
        foundTeacher.ifPresent(value -> assertThat(value).isEqualTo(teacher1));
    }

}
