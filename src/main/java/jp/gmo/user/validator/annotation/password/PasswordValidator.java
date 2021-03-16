package jp.gmo.user.validator.annotation.password;

import javax.validation.ConstraintValidatorContext;

import jp.gmo.user.constant.MessageConstants;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import jp.gmo.user.repository.AccountRepository;
import jp.gmo.user.request.ChangePasswordRequest;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PasswordValidator implements HibernateConstraintValidator<Password, ChangePasswordRequest> {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isValid(ChangePasswordRequest request, ConstraintValidatorContext context) {

        // Set default constraint violation
        context.disableDefaultConstraintViolation();
        // unwrap context to hibernateContext
        HibernateConstraintValidatorContext hibernateContext = context.unwrap(HibernateConstraintValidatorContext.class);

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())){
            hibernateContext.buildConstraintViolationWithTemplate(MessageConstants.CONST_MSG_VALIDATE_PASSWORD_CONFIRM_NOT_MATCH)
                                                                    .addPropertyNode("passwordConfirm")
                                                                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
