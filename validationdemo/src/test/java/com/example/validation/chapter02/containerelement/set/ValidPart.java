package com.example.validation.chapter02.containerelement.set;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { ValidPart.ValidPartValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ValidPart {
	String message() default "{org.hibernate.validator.referenceguide.chapter02.containerelement.ValidPart.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	class ValidPartValidator
			implements ConstraintValidator<ValidPart, String> {

		@Override
		public void initialize(ValidPart annotation) {
		}

		@Override
		public boolean isValid(String value, ConstraintValidatorContext context) {
			return value != null;
		}
	}
}
