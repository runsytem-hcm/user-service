/*
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *
 * Author: VuDA
 * Creation Date : Apr 11, 2019
 */
package jp.gmo.user.validator.annotation.sizelength;

import jp.gmo.user.constant.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {SizeLengthValidator.class})
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface SizeLength {

    /**
     * Message.
     *
     * @return the string
     */
    String message() default Constants.CONST_STR_BLANK;

    /**
     * Error code.
     *
     * @return the string
     */
    String errorCode() default Constants.CONST_STR_BLANK;

    /**
     * Item name.
     *
     * @return the string
     */
    String itemName() default Constants.CONST_STR_BLANK;

    /**
     * Partition.
     *
     * @return the string
     */
    String partition() default Constants.CONST_STR_BLANK;

    /**
     * Min length.
     *
     * @return the int
     */
    int minLength() default 0;

    /**
     * Max length.
     *
     * @return the int
     */
    int maxLength() default 0;

    /**
     * Groups.
     *
     * @return the class[]
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return the class<? extends payload>[]
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@link NotNull} annotations on the same element.
     *
     * @see javax.validation.constraints.NotNull
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    @interface List {

        /**
         * Value.
         *
         * @return the size length[]
         */
        SizeLength[] value();
    }
}
