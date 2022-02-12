package com.example.validation.chapter04.resourcebundlelocator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceBundleLocatorTest {

	@BeforeAll
	public static void init() {
		Locale.setDefault(new Locale("en","CN"));

	}

	@Test
	public void messagesRetrievedFromSpecificBundle() {
		//tag::messagesRetrievedFromSpecificBundle[]
		Validator validator = Validation.byDefaultProvider()
				.configure()
				.messageInterpolator(
						new ResourceBundleMessageInterpolator(
								new PlatformResourceBundleLocator( "MyMessages" )
						)
				)
				.buildValidatorFactory()
				.getValidator();
		//end::messagesRetrievedFromSpecificBundle[]

		Set<ConstraintViolation<Car>> violations = validator.validateProperty(
				new Car(),
				"licensePlate"
		);
		assertEquals( 1, violations.size() );
		ConstraintViolation<Car> next = violations.iterator().next();
		assertEquals( "null is not supported", next.getMessage() );
	}

	@Test
	public void usingAggregateResourceBundleLocator() {
		//tag::usingAggregateResourceBundleLocator[]
		Validator validator = Validation.byDefaultProvider()
				.configure()
				.messageInterpolator(
						new ResourceBundleMessageInterpolator(
								new AggregateResourceBundleLocator(
										Arrays.asList(
												"MyMessages",
												"MyOtherMessages"
										)
								)
						)
				)
				.buildValidatorFactory()
				.getValidator();
		//end::usingAggregateResourceBundleLocator[]

		Set<ConstraintViolation<Car>> violations = validator.validateProperty(
				new Car(),
				"licensePlate"
		);
		assertEquals( 1, violations.size() );
		assertEquals( "null is not supported", violations.iterator().next().getMessage() );

		violations = validator.validateProperty( new Car(), "topSpeed" );
		assertEquals( 1, violations.size() );
		assertEquals( "too high", violations.iterator().next().getMessage() );
	}
}
