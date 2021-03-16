/*
 * Seed's Lounge マッチングサイト
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *
 * Author: VuDA
 * Creation Date : Apr 11, 2019
 */
package jp.gmo.user.validator.annotation.sizelength;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidator;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext;

import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Map;

public class SizeLengthValidator implements HibernateConstraintValidator<SizeLength, String> {

    /**
     * The min length.
     */
    private int minLength;

    /**
     * The max length.
     */
    private int maxLength;

    /* (non-Javadoc)
     * @see org.hibernate.validator.constraintvalidation.HibernateConstraintValidator#initialize(javax.validation.metadata.ConstraintDescriptor, org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext)
     */
    @Override
    public void initialize(ConstraintDescriptor<SizeLength> constraintDescriptor,
                           HibernateConstraintValidatorInitializationContext initializationContext) {
        Map<String, Object> attributes = constraintDescriptor.getAttributes();
        this.minLength = (int) attributes.get("minLength");
        this.maxLength = (int) attributes.get("maxLength");
    }

    /* (non-Javadoc)
     * @see javax.validation.ConstraintValidator#isValid(java.lang.Object, javax.validation.ConstraintValidatorContext)
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return StringUtils.isEmpty(value)
                || (value.trim().length() >= minLength && value.trim().length() <= maxLength);
    }
}
