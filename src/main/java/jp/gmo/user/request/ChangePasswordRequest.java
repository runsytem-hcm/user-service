package jp.gmo.user.request;

import jp.gmo.user.constant.MessageConstants;
import jp.gmo.user.constant.RegexConstants;
import jp.gmo.user.validator.annotation.password.Password;
import jp.gmo.user.validator.annotation.sizelength.SizeLength;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Password
public class ChangePasswordRequest {

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_PASSWORD + "}", regexp = RegexConstants.PASSWORD_REGEX)
    @SizeLength(partition = "{minLength} - {maxLength}", message = "{" + MessageConstants.CONST_MSG_VALIDATE_LENGTH_PASSWORD + "}", maxLength = 12, minLength = 6)
    private String currentPassword;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_PASSWORD + "}", regexp = RegexConstants.PASSWORD_REGEX)
    @SizeLength(partition = "{minLength} - {maxLength}", message = "{" + MessageConstants.CONST_MSG_VALIDATE_LENGTH_PASSWORD + "}", maxLength = 12, minLength = 6)
    private String newPassword;

    @NotNull(message = "{" + MessageConstants.CONST_MSG_VALIDATE_NOT_NULL + "}")
    @Pattern(message = "{" + MessageConstants.CONST_MSG_VALIDATE_PASSWORD + "}", regexp = RegexConstants.PASSWORD_REGEX)
    @SizeLength(partition = "{minLength} - {maxLength}", message = "{" + MessageConstants.CONST_MSG_VALIDATE_LENGTH_PASSWORD + "}", maxLength = 12, minLength = 6)
    private String confirmNewPassword;
}
