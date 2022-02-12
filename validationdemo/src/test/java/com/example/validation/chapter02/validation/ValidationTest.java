package com.example.validation.chapter02.validation;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidationTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		//tag::setUpValidator[]
		Locale.setDefault(new Locale("en","CN"));
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		//end::setUpValidator[]
	}

	@Test
	public void validate() {
		//tag::validate[]
		Car car = new Car( null, true );

		Set<ConstraintViolation<Car>> constraintViolations = validator.validate( car );

		assertEquals( 1, constraintViolations.size() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
		//end::validate[]
	}

	@Test
	public void validateProperty() {
		//tag::validateProperty[]
		Car car = new Car( null, true );

		Set<ConstraintViolation<Car>> constraintViolations = validator.validateProperty(
				car,
				"manufacturer"
		);

		assertEquals( 1, constraintViolations.size() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
		//end::validateProperty[]
	}

	@Test
	public void validateValue() {
		//tag::validateValue[]
		Set<ConstraintViolation<Car>> constraintViolations = validator.validateValue(
				Car.class,
				"manufacturer",
				null
		);

		assertEquals( 1, constraintViolations.size() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
		//end::validateValue[]
	}

}
