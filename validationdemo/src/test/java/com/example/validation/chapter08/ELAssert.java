package com.example.validation.chapter08;

import javax.validation.*;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ELAssert.Validator.class })
@Documented
public @interface ELAssert {

	String message() default "{org.hibernate.validator.referenceguide.chapter03.crossparameter.ELAssert.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;

	String expression();

	@SupportedValidationTarget({
			ValidationTarget.PARAMETERS,
			ValidationTarget.ANNOTATED_ELEMENT
	})
	class Validator
			implements ConstraintValidator<ELAssert, Object[]> {

		@Override
		public void initialize(ELAssert constraintAnnotation) {
		}

		@Override
		public boolean isValid(Object[] value, ConstraintValidatorContext context) {
			return false;
		}
	}
}
