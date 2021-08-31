package com.qpzm7903.validation.demo.annotation.validator;

import com.qpzm7903.validation.demo.annotation.UpperCase;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-22:56
 */
public class MyUpperCaseValidator implements ConstraintValidator<UpperCase, String> {
    @Override
    public void initialize(UpperCase constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        return input.equals(input.toUpperCase(Locale.ROOT));
    }
}
