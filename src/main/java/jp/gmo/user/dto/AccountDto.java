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
}
