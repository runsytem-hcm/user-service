package jp.gmo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    
    private String employeeCode;
    private String email;
    private String employeeName;
    private String password;
    private Integer roleId;

    public AccountDto(AccountDto accountDto) {
        this.employeeCode = accountDto.getEmployeeCode();
        this.email = accountDto.getEmail();
        this.employeeName = accountDto.getEmployeeName();
        this.password = accountDto.getPassword();
        this.roleId = accountDto.getRoleId();
    }
}
