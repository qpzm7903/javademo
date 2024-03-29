package com.example.validation.chapter04.complete;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		Locale.setDefault(new Locale("en","CN"));
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void messageDescriptors() {
		//tag::messageDescriptors[]
		Car car = new Car( null, "A", 1, 400.123456, BigDecimal.valueOf( 200000 ) );

		String message = validator.validateProperty( car, "manufacturer" )
				.iterator()
				.next()
				.getMessage();
		assertEquals( "must not be null", message );

		message = validator.validateProperty( car, "licensePlate" )
				.iterator()
				.next()
				.getMessage();
		assertEquals(
				"The license plate 'A' must be between 2 and 14 characters long",
				message
		);

		message = validator.validateProperty( car, "seatCount" ).iterator().next().getMessage();
		assertEquals( "There must be at least 2 seats", message );

		message = validator.validateProperty( car, "topSpeed" ).iterator().next().getMessage();
		assertEquals( "The top speed 400.12 is higher than 350", message );

		message = validator.validateProperty( car, "price" ).iterator().next().getMessage();
		assertEquals( "Price must not be higher than $100000", message );
		//end::messageDescriptors[]
	}
}
