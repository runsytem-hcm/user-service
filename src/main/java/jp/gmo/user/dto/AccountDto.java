package jp.gmo.user.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String employeeCode;
    private String employeeName;
    private Integer roleId;
}
