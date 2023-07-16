package com.teachedapp.respository;

import com.teachedapp.dao.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @PostAuthorize("returnObject.get().course.teacher.id == authentication.principal.id" +
            " or returnObject.get().course.student.id == authentication.principal.id" +
            " or hasRole('ROLE_ADMIN')")
    @Override
    Optional<Lesson> findById(Integer id);

    @PostFilter("filterObject.course.teacher.id == authentication.principal.id" +
            " or filterObject.course.student.id == authentication.principal.id" +
            " or hasRole('ROLE_ADMIN')")
    @Override
    List<Lesson> findAll();

    @Query("select l from Lesson l\n" +
            "where l.course.teacher.id = ?#{ authentication.principal?.id }\n" +
            "or l.course.student.id = ?#{ authentication.principal?.id }\n" +
            "or ?#{ security.hasRole('ROLE_ADMIN') ? 1 : 0 } = 1")
    @Override
    Page<Lesson> findAll(Pageable pageable);
}
