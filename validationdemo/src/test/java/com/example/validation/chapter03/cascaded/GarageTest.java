package com.example.validation.chapter03.cascaded;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.executable.ExecutableValidator;
//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GarageTest {

	private static ExecutableValidator executableValidator;

//	@BeforeAll
	@BeforeAll
	public static void setUpValidator() {
		Locale.setDefault(new Locale("en","CN"));
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		executableValidator = factory.getValidator().forExecutables();
	}

	@Test
	public void cascadedMethodParameterValidation() throws Exception {
		//cascaded method parameter
		Garage object = new Garage( "Bob's Auto Shop" );
		Method method = Garage.class.getMethod( "checkCar", Car.class );
		Object[] parameterValues = { new Car( "Morris", "A" ) };

		Set<ConstraintViolation<Garage>> violations = executableValidator.validateParameters(
				object,
				method,
				parameterValues
		);

		//Car#licensePlate is too short
		assertEquals( 1, violations.size() );
		ConstraintViolation<Garage> violation = violations.iterator().next();
		assertEquals(
				Size.class,
				violation.getConstraintDescriptor().getAnnotation().annotationType()
		);
	}

	@Test
	public void cascadedConstructorReturnValueValidation() throws Exception {
		//cascaded constructor return value
		Constructor<Garage> constructor = Garage.class.getConstructor( String.class );
		Garage createdObject = new Garage( null );

		Set<ConstraintViolation<Garage>> violations = executableValidator.validateConstructorReturnValue(
				constructor,
				createdObject
		);

		//Garage#name is null
		assertEquals( 1, violations.size() );
		ConstraintViolation<Garage> violation = violations.iterator().next();
		assertEquals(
				NotNull.class,
				violation.getConstraintDescriptor().getAnnotation().annotationType()
		);
	}
}
