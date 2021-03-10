package jp.gmo.user.repository;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AccountEntity, Long> {

    @Query(name = "getAccountInfo", nativeQuery = true)
    AccountDto getAccountInfo(@Param("email") String email, @Param("employeeCode") String employeeCode);
}
