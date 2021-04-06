package jp.gmo.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import jp.gmo.user.dto.EmployeeDto;
import jp.gmo.user.entity.EmployeesEntity;

@Mapper
public interface EmployeeMapper {

//    @Mapping(source = "entity.id.employeeCode", target = "employeeCode")
//    @Mapping(source = "entity.id.email", target = "email")
    EmployeeDto toEmployeesDto(EmployeesEntity entity);
}
