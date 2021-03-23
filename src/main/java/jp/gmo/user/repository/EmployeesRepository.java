package jp.gmo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, EmployeesKey> {

    @Query(value = "SELECT e.* FROM employees e WHERE e.email = :email AND e.delete_flag = 0", nativeQuery = true)
    EmployeesEntity findByEmail(@Param("email") String email);

    @Query(value = "SELECT e.* FROM employees e WHERE (e.email = :email OR e.employee_code = :employee_code) AND e.delete_flag = 0", nativeQuery = true)
    EmployeesEntity findByEmployees(@Param("email") String email, @Param("employee_code") String employeeCode);

    @Query(value = "SELECT e.* FROM employees e " +
            "WHERE (LENGTH(:email) = 0 OR e.email LIKE %:email%) " +
            "AND (LENGTH(:employee_name) = 0 OR e.employee_name LIKE %:employee_name%) " +
            "AND e.delete_flag = 0 " +
            "ORDER BY e.create_time DESC LIMIT :offset, :limit", nativeQuery = true)
    List<EmployeesEntity> getListEmployees(@Param("email") String email, @Param("employee_name") String employeeName, @Param("offset") int offset, @Param("limit") int limit);

    @Query(value = "SELECT count(e.employee_code) FROM employees e " +
            "WHERE (LENGTH(:email) = 0 OR e.email LIKE %:email%) " +
            "AND (LENGTH(:employee_name) = 0 OR e.employee_name LIKE %:employee_name%) " +
            "AND e.delete_flag = 0 " , nativeQuery = true)
    BigInteger countEmployees(@Param("email") String email, @Param("employee_name") String employeeName);
}
