package jp.gmo.user.validator.annotation.password;

import jp.gmo.user.constant.Constants;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface Password {

    String message() default Constants.CONST_STR_BLANK;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the not null[]
         */
        Password[] value();
    }
}
