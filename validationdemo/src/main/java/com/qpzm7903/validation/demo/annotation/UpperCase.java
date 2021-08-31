package com.qpzm7903.validation.demo.annotation;

import com.qpzm7903.validation.demo.annotation.validator.MyUpperCaseValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-22:55
 */
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = MyUpperCaseValidator.class)
public @interface UpperCase {
    String value();

    String message() default "${validatedValue} must upper case";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
