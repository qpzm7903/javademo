package com.example.validation.chapter06;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		Locale.setDefault(new Locale("en","CN"));
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testCheckCaseConstraint() {
		//tag::testCheckCaseConstraint[]
		//invalid license plate
		Car car = new Car( "Morris", "dd-ab-123", 4 );
		Set<ConstraintViolation<Car>> constraintViolations =
				validator.validate( car );
		assertEquals( 1, constraintViolations.size() );
		assertEquals(
				"Case mode must be UPPER.",
				constraintViolations.iterator().next().getMessage()
		);

		//valid license plate
		car = new Car( "Morris", "DD-AB-123", 4 );

		constraintViolations = validator.validate( car );

		assertEquals( 0, constraintViolations.size() );
		//end::testCheckCaseConstraint[]
	}
}
