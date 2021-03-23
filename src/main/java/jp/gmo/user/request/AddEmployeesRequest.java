package jp.gmo.user.request;

import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.constant.RegexConstants;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class AddEmployeesRequest {

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_EMPLOYEE_CODE + "}", regexp = RegexConstants.EMPLOYEES_CODE_REGEX)
    @Length(max = 10, message = "{" + MessageConstants.CONST_MSG_VALIDATE_EMPLOYEES_CODE_MAX_LENGTH + "}")
    private String employeeCode;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Length(max = 50, message = "{" + MessageConstants.CONST_MSG_VALIDATE_EMPLOYEES_MAX_LENGTH + "}")
    private String employeeName;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_EMAIL + "}", regexp = RegexConstants.EMAIL_REGEX)
    private String email;
}
