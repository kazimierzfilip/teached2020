package com.teachedapp.respository;

import com.teachedapp.dao.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findByNameContainingIgnoreCase(String name);

    List<Subject> findAllByNameContaining(String name);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    <S extends Subject> S saveAndFlush(S s);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteInBatch(Iterable<Subject> iterable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteAllInBatch();

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    <S extends Subject> S save(S s);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteById(Integer integer);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void delete(Subject subject);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteAll(Iterable<? extends Subject> iterable);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteAll();
}
