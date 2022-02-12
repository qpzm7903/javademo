package com.example.validation.chapter03.inheritance.parallel;

import javax.validation.ConstraintDeclarationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class CarTest {

	private static ExecutableValidator executableValidator;

	@BeforeAll
	public static void setUpValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		executableValidator = factory.getValidator().forExecutables();
	}

	@Test
	public void illegalParameterConstraintsInParallelTypes() {
		Assertions.assertThrows(ConstraintDeclarationException.class, () -> {
			RacingCar object = new RacingCar();
			Method method = Car.class.getMethod("drive", int.class);
			Object[] parameterValues = {};
			executableValidator.validateParameters(
					object,
					method,
					parameterValues
			);
		});

	}
}
