package com.teachedapp.respository;

import com.teachedapp.dao.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByAccount_LoginContainingIgnoreCase(String login);

    List<Teacher> findByAccount_PersonalData_FirstNameContainingIgnoreCaseAndAccount_PersonalData_LastNameContainingIgnoreCase(String firstName, String lastName);

    List<Teacher> findByAccount_PersonalData_Sex(Character sex);

    List<Teacher> findByAccount_PersonalData_AgeBetween(Integer from, Integer to);

    @Query("select t\n" +
            "from Teacher t\n" +
            "   join Account a on t.id = a.id\n" +
            "where ((a.personalData.age between :from and :to) or (a.personalData.age is null))\n" +
            "    and (a.address.city like :city)")
    List<Teacher> queryByAgeBetweenAndCity(
            @Param("from") Integer from,
            @Param("to") Integer to,
            @Param("city") String city);

    @Query(nativeQuery = true,
            value = "select distinct t.*\n" +
            "from teacher t\n" +
            "join account a on t.teacher_id = a.account_id\n" +
            "left join teacher_subject_assignment tsa on t.teacher_id = tsa.teacher_id\n" +
            "left join subject s on tsa.subject_id = s.subject_id\n" +
            "where ((a.age between :from and :to) or (a.age is null))\n" +
            "    and ((a.city like :city) or (a.city is null))\n" +
            "    and ((s.name like :subject) or (s.name is null))")
    List<Teacher> queryByAgeBetweenAndCityAndSubject(
            @Param("from") Integer from,
            @Param("to") Integer to,
            @Param("city") String city,
            @Param("subject") String subject);

    @Query(nativeQuery = true,
            value = "select distinct t.*" +
                    "from teacher t\n" +
                    "join account a on t.teacher_id = a.account_id\n" +
                    "left join teacher_subject_assignment tsa on t.teacher_id = tsa.teacher_id\n" +
                    "left join subject s on tsa.subject_id = s.subject_id\n" +
                    "left join (select teacher_id, count(1)\n" +
                    "        from course\n" +
                    "        where status = 1\n" +
                    "        group by teacher_id) ac\n" +
                    "    on t.teacher_id = ac.teacher_id\n" +
                    "left join (select teacher_id, sum(hours_per_week)\n" +
                    "        from course\n" +
                    "        left join lesson l on course.course_id = l.course_id\n" +
                    "        where l.status = 3\n" +
                    "        group by teacher_id) th\n" +
                    "    on t.teacher_id = th.teacher_id\n" +
                    "left join (select recipient_id, sum(amount)\n" +
                    "        from payment\n" +
                    "        where status = 1\n" +
                    "        and (pay_date >= date_trunc('month', current_date - interval '1' month)\n" +
                    "        and pay_date <= date_trunc('month', current_date))\n" +
                    "        group by recipient_id) paid\n" +
                    "    on t.teacher_id = paid.recipient_id\n" +
                    "left join (select recipient_id, sum(amount)\n" +
                    "        from payment\n" +
                    "        where status = 0\n" +
                    "        group by recipient_id) unpaid\n" +
                    "    on t.teacher_id = unpaid.recipient_id\n" +
                    "where ((a.age between :minAge and :maxAge) or (a.age is null))\n" +
                    "    and ((a.city like :city) or (a.city is null))\n" +
                    "    and ((s.name like :subject) or (s.name is null))\n" +
                    "    and ((ac.count between :minAC and :maxAC) or (ac.count is null))\n" +
                    "    and ((paid.sum between :minPaid and :maxPaid) or (paid.sum is null))\n" +
                    "    and ((th.sum between :minHours and :maxHours) or (th.sum is null))\n" +
                    "    and ((unpaid.sum between :minUnpaid and :maxUnpaid) or (unpaid.sum is null))")
    List<Teacher> queryByStatisticParameters(
            @Param("minAge") Integer minAge,
            @Param("maxAge") Integer maxAge,
            @Param("city") String city,
            @Param("subject") String subject,
            @Param("minAC") Integer minAC,
            @Param("maxAC") Integer maxAC,
            @Param("minPaid") Integer minPaid,
            @Param("maxPaid") Integer maxPaid,
            @Param("minHours") Integer minHours,
            @Param("maxHours") Integer maxHours,
            @Param("minUnpaid") Integer minUnpaid,
            @Param("maxUnpaid") Integer maxUnpaid);
}