package jp.gmo.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.gmo.user.entity.EmployeesEntity;
import jp.gmo.user.entity.key.EmployeesKey;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeesEntity, EmployeesKey> {

    Optional<EmployeesEntity> findByEmailAndDeleteFlag(String email, int deleteFlag);

    Optional<EmployeesEntity> findByEmailAndEmployeeCodeAndDeleteFlag(String email, String employeeCode, int deleteFlag);

    Optional<EmployeesEntity> findByEmployeeCodeAndDeleteFlag(String employeeCode, int deleteFlag);

    List<EmployeesEntity> findByDeleteFlag (int deleteFlag);

    Page<EmployeesEntity> findByEmailContainingAndEmployeeNameContainingAndDeleteFlag(String email, String employeeName, int deleteFlag, Pageable paging);
}
