package com.qpzm7903.validation.demo;

import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.junit.jupiter.api.Test;

import javax.validation.*;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-08-30-22:37
 */
class CarTest {

    @Test
    @Valid
    void test_car() {
        @Valid Car car = new Car(null,false);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Car>> validate = validator.validate(car);
        validate.forEach(carConstraintViolation -> System.out.println(carConstraintViolation));

    }

}