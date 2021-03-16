package jp.gmo.user.response.data;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountResponseData {
    private String employeeCode;
    private String email;
    private String employeeName;
    private String password;
    private Integer roleId;
}
