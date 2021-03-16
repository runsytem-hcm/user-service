package jp.gmo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, EmployeesKey> {

    @Query(value = "SELECT e.* FROM employees e WHERE e.email = :email AND e.delete_flag = 0", nativeQuery = true)
    EmployeesEntity findByEmail(@Param("email") String email);
}
