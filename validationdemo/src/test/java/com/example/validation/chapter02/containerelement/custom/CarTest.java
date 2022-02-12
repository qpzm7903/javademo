package com.example.validation.chapter02.containerelement.custom;


import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CarTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		ValidatorFactory factory = Validation.byProvider( HibernateValidator.class )
				.configure()
				.addValueExtractor( new GearBoxValueExtractor() )
				.buildValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void validateCustomContainerElementConstraint() {
		//tag::validateCustomContainerElementConstraint[]
		Car car = new Car();
		car.setGearBox( new GearBox<>( new Gear.AcmeGear() ) );

		Set<ConstraintViolation<Car>> constraintViolations = validator.validate( car );
		assertEquals( 1, constraintViolations.size() );

		ConstraintViolation<Car> constraintViolation =
				constraintViolations.iterator().next();
		assertEquals(
				"Gear is not providing enough torque.",
				constraintViolation.getMessage()
		);
		assertEquals(
				"gearBox",
				constraintViolation.getPropertyPath().toString()
		);
		//end::validateCustomContainerElementConstraint[]
	}

}
