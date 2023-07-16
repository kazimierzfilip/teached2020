package com.teachedapp.respository;

import com.teachedapp.dao.Course;
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
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @PostAuthorize("returnObject.get().teacher.id == authentication.principal.id" +
            " or returnObject.get().student.id == authentication.principal.id" +
            " or hasRole('ROLE_ADMIN')")
    @Override
    Optional<Course> findById(Integer id);

    @PostFilter("filterObject.teacher.id == authentication.principal.id" +
            " or filterObject.student.id == authentication.principal.id" +
            " or hasRole('ROLE_ADMIN')")
    @Override
    List<Course> findAll();

    @Query("select c from Course c\n" +
            "where c.teacher.id = ?#{ authentication.principal?.id }\n" +
            "or c.student.id = ?#{ authentication.principal?.id }\n" +
            "or ?#{ security.hasRole('ROLE_ADMIN') ? 1 : 0 } = 1")
    @Override
    Page<Course> findAll(Pageable pageable);
}