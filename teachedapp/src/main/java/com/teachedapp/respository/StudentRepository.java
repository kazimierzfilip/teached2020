package com.teachedapp.respository;

import com.teachedapp.dao.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @PostAuthorize("hasRole('ROLE_STUDENT') and #id == authentication.principal.id" +
            " or hasRole('ROLE_TEACHER') and returnObject.get().courses.![#this.teacher.id].contains(authentication.principal.id)" +
            " or hasRole('ROLE_ADMIN')")
    @Override
    Optional<Student> findById(Integer id);

    @Query("select s from Student s " +
            "where s.id = ?#{ authentication.principal.id } " +
            "or (?#{ security.hasRole('ROLE_TEACHER') ? 1 : 0 } = 1 " +
            "and ?#{ authentication.principal.id } IN (select c.teacher from Course c where c.student = s.id)) " +
            "or ?#{ security.hasRole('ROLE_ADMIN') ? 1 : 0 } = 1")
    @Override
    Page<Student> findAll(Pageable pageable);

    List<Student> findByAccount_EmailContainingIgnoreCase(String email);

    List<Student> findByAccount_LoginContainingIgnoreCase(String login);

    List<Student> findByAccount_PersonalData_FirstNameContainingIgnoreCaseAndAccount_PersonalData_LastNameContainingIgnoreCase(String firstName, String lastName);

    List<Student> findByAccount_PersonalData_Sex(Character sex);

    @Query("select s\n" +
            "from Student s\n" +
            "   join Account a on s.id = a.id\n" +
            "where ((a.personalData.age between :from and :to) or (a.personalData.age is null))\n" +
            "    and (a.address.city like :city)")
    List<Student> queryByAgeBetweenAndCity(
            @Param("from") Integer from,
            @Param("to") Integer to,
            @Param("city") String city);

    @Query(nativeQuery = true,
            value = "select distinct s.*\n" +
                    "from student s\n" +
                    "join account a on s.student_id = a.account_id\n" +
                    "left join (select student_id, sum(hours_per_week)\n" +
                    "        from course\n" +
                    "        left join lesson l on course.course_id = l.course_id\n" +
                    "        where l.status = 3\n" +
                    "        group by student_id) th\n" +
                    "    on s.student_id = th.student_id\n" +
                    "left join (select sender_id, sum(amount)\n" +
                    "        from payment\n" +
                    "        where status = 1\n" +
                    "        and (pay_date >= date_trunc('month', current_date - interval '1' month)\n" +
                    "        and pay_date <= date_trunc('month', current_date))\n" +
                    "        group by sender_id) paid\n" +
                    "    on s.student_id = paid.sender_id\n" +
                    "left join (select sender_id, sum(amount)\n" +
                    "        from payment\n" +
                    "        where status = 0\n" +
                    "        group by sender_id) unpaid\n" +
                    "    on s.student_id = unpaid.sender_id\n" +
                    "where ((a.age between :minAge and :maxAge) or (a.age is null))\n" +
                    "    and ((a.city like :city) or (a.city is null))\n" +
                    "    and ((paid.sum between :minPaid and :maxPaid) or (paid.sum is null))\n" +
                    "    and ((th.sum between :minHours and :maxHours) or (th.sum is null))\n" +
                    "    and ((unpaid.sum between :minUnpaid and :maxUnpaid) or (unpaid.sum is null))")
    List<Student> queryByStatisticParameters(
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("city") String city,
            @Param("minPaid") Integer minPaid,
            @Param("maxPaid") Integer maxPaid,
            @Param("minHours") Integer minHours,
            @Param("maxHours") Integer maxHours,
            @Param("minUnpaid") Integer minUnpaid,
            @Param("maxUnpaid") Integer maxUnpaid);
}