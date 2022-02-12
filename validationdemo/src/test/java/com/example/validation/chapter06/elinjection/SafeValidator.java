package com.example.validation.chapter06.elinjection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import com.example.validation.chapter06.constraintvalidatorpayload.ZipCode;

//tag::include[]
public class SafeValidator implements ConstraintValidator<ZipCode, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if ( value == null ) {
			return true;
		}

		HibernateConstraintValidatorContext hibernateContext = context.unwrap(
				HibernateConstraintValidatorContext.class );
		hibernateContext.disableDefaultConstraintViolation();

		if ( isInvalid( value ) ) {
			hibernateContext
					.addExpressionVariable( "validatedValue", value )
					.buildConstraintViolationWithTemplate( "${validatedValue} is not a valid ZIP code" )
					// 6.x不支持
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
