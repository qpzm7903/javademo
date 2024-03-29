package com.example.validation.chapter03.validation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValidRacingCar.Validator.class })
@Documented
public @interface ValidRacingCar {

	String message() default "{org.hibernate.validator.referenceguide.chapter03.validation.ValidRacingCar.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	class Validator implements ConstraintValidator<ValidRacingCar, Car> {

		@Override
		public void initialize(ValidRacingCar constraintAnnotation) {
		}

		@Override
		public boolean isValid(Car car, ConstraintValidatorContext context) {
			return false;
		}
	}
}
