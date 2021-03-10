package jp.gmo.user.dto.request;


import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.constant.RegexConstants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequestDto {

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_EMAIL + "}", regexp = RegexConstants.EMAIL_REGEX)
    private String email;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_PASSWORD + "}", regexp = RegexConstants.PASSWORD_REGEX)
    private String password;
    private String rememberMe;
}
