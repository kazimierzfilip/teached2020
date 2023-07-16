package com.teachedapp.respository;

import com.teachedapp.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByLogin(String login);

    @Query(value = "SELECT CASE WHEN teacher_id IS NOT NULL THEN 'ROLE_TEACHER' END\n" +
            "FROM account\n" +
            "LEFT JOIN teacher ON account_id = teacher_id\n" +
            "WHERE account_id = (:accountId)\n" +
            "UNION ALL\n" +
            "SELECT CASE WHEN administrator_id IS NOT NULL THEN 'ROLE_ADMIN' ELSE null END\n" +
            "FROM account\n" +
            "LEFT JOIN administrator ON account_id = administrator_id\n" +
            "WHERE account_id = (:accountId)\n" +
            "UNION ALL\n" +
            "SELECT CASE WHEN student_id IS NOT NULL THEN 'ROLE_STUDENT' ELSE null END\n" +
            "FROM account\n" +
            "LEFT JOIN student ON account_id = student_id\n" +
            "WHERE account_id = (:accountId)", nativeQuery = true)
    List<String> findRolesByAccountId(@Param("accountId") Integer accountId);
}