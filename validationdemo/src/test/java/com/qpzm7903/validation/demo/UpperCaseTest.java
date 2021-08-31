package com.qpzm7903.validation.demo;

import com.qpzm7903.validation.demo.annotation.UpperCase;
import lombok.Data;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-22:58
 */
public class UpperCaseTest {
    @Test
    void test() {
        User user = new User();
        user.setName("wei");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> validate = validator.validate(user);
        for (ConstraintViolation<User> userConstraintViolation : validate) {
            System.out.println(userConstraintViolation.getMessage());
        }
    }



    @Data
    static class User{
        @UpperCase("name")
        private String name;
    }
}
