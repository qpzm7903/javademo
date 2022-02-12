package com.example.validation.chapter02.containerelement.optional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CarTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		ValidatorFactory factory = Validation.byProvider( HibernateValidator.class )
				.configure()
				.buildValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validateOptionalContainerElementConstraint() {
		//tag::validateOptionalContainerElementConstraint[]
		Car car = new Car();
		car.setTowingCapacity( 100 );

		Set<ConstraintViolation<Car>> constraintViolations = validator.validate( car );

		assertEquals( 1, constraintViolations.size() );

		ConstraintViolation<Car> constraintViolation = constraintViolations.iterator().next();
		assertEquals(
				"Not enough towing capacity.",
				constraintViolation.getMessage()
		);
		assertEquals(
				"towingCapacity",
				constraintViolation.getPropertyPath().toString()
		);
		//end::validateOptionalContainerElementConstraint[]
	}

}
