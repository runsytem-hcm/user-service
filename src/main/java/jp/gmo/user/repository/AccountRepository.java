package jp.gmo.user.repository;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(name = "getAccountInfo", nativeQuery = true)
    Optional<AccountDto> getAccountInfo(@Param("email") String email);

    @Query(name = "updatePassword", nativeQuery = true)
    @Modifying
    void updatePassword(
            @Param("email") String email,
            @Param("password") String password,
            @Param("currentDateTime") LocalDateTime currentDateTime,
            @Param("updateBy") String updateBy);
}
