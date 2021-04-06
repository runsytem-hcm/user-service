package jp.gmo.user.mapper;

import jp.gmo.user.dto.AccountDto;
import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.entity.AccountEntity;
import jp.gmo.user.entity.EmployeesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AccountMapper {

    @Mapping(source = "entity.emp.employeeCode", target = "employeeCode")
    @Mapping(source = "entity.emp.email", target = "email")
    @Mapping(source = "entity.emp.employeeName", target = "employeeName")
    AccountDto toAccountDto(AccountEntity entity);
}
