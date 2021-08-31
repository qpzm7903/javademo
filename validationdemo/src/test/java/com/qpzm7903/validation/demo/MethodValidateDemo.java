package com.qpzm7903.validation.demo;

import com.qpzm7903.validation.demo.annotation.UpperCase;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-23:02
 */
public class MethodValidateDemo {
    @Test
    void test() throws NoSuchMethodException {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

        Validator parameterNameProvider = validatorFactory.getValidator();
        ExecutableValidator validator = (ExecutableValidator) parameterNameProvider;
        Method getString = MethodValidateDemo.class.getDeclaredMethod("getString", String.class);
        Object[] test = {"test"};
        Set<ConstraintViolation<MethodValidateDemo>> constraintViolations = validator.validateParameters(this, getString, test);
        for (ConstraintViolation<MethodValidateDemo> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getInvalidValue());
            System.out.println(constraintViolation);
        }
    }

    String getString(@UpperCase("name") String name) {
        return name;
    }
}
