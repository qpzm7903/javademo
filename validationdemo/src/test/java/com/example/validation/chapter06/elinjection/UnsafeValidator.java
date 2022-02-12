package com.example.validation.chapter06.elinjection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import com.example.validation.chapter06.constraintvalidatorpayload.ZipCode;

//tag::include[]
public class UnsafeValidator implements ConstraintValidator<ZipCode, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}

		context.disableDefaultConstraintViolation();

		HibernateConstraintValidatorContext hibernateContext = context.unwrap(
				HibernateConstraintValidatorContext.class );
		hibernateContext.disableDefaultConstraintViolation();

		if ( isInvalid( value ) ) {
			hibernateContext
					// THIS IS UNSAFE, DO NOT COPY THIS EXAMPLE
					.buildConstraintViolationWithTemplate( value + " is not a valid ZIP code" )
//					.enableExpressionLanguage()
					.addConstraintViolation();

			return false;
		}

		return true;
	}

	private boolean isInvalid(String value) {
		// ...
		return false;
	}
}
// end::include[]
