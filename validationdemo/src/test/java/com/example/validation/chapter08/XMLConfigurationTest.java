package com.example.validation.chapter08;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.metadata.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Fail.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

/**
 * @author Hardy Ferentschik
 */
public class XMLConfigurationTest {
	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		Configuration<?> configuration = Validation.byDefaultProvider().configure();
		configuration.addMapping( XMLConfigurationTest.class.getResourceAsStream( "rental-station-mapping.xml" ) );
		ValidatorFactory factory = configuration.buildValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testXMLConstraintsApplied() {
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass( RentalStation.class );

		assertTrue( beanDescriptor.isBeanConstrained() );

		ConstructorDescriptor constructorDescriptor = beanDescriptor.getConstraintsForConstructor();
		ReturnValueDescriptor returnValueDescriptor = constructorDescriptor.getReturnValueDescriptor();
		Set<ConstraintDescriptor<?>> constraintDescriptors = returnValueDescriptor.getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, ValidRentalStation.class );

		constructorDescriptor = beanDescriptor.getConstraintsForConstructor( String.class );
		List<ParameterDescriptor> parameterDescriptors = constructorDescriptor.getParameterDescriptors();
		constraintDescriptors = parameterDescriptors.get( 0 ).getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, NotNull.class );

		MethodDescriptor methodDescriptor = beanDescriptor.getConstraintsForMethod( "getCustomers" );
		returnValueDescriptor = methodDescriptor.getReturnValueDescriptor();
		constraintDescriptors = returnValueDescriptor.getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, NotNull.class, Size.class );

		methodDescriptor = beanDescriptor.getConstraintsForMethod( "rentCar", Customer.class, Date.class, int.class );
		parameterDescriptors = methodDescriptor.getParameterDescriptors();

		constraintDescriptors = parameterDescriptors.get( 0 ).getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, NotNull.class );

		constraintDescriptors = parameterDescriptors.get( 1 ).getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, NotNull.class, Future.class );

		constraintDescriptors = parameterDescriptors.get( 2 ).getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, Min.class );
	}

	@Test
	public void testXMLCrossParameterConstraints() {
		BeanDescriptor beanDescriptor = validator.getConstraintsForClass( Garage.class );

		MethodDescriptor methodDescriptor = beanDescriptor.getConstraintsForMethod( "buildCar", List.class );
		CrossParameterDescriptor crossParameterDescriptor = methodDescriptor.getCrossParameterDescriptor();
		Set<ConstraintDescriptor<?>> constraintDescriptors = crossParameterDescriptor.getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, ELAssert.class );

		methodDescriptor = beanDescriptor.getConstraintsForMethod( "paintCar", int.class );
		ReturnValueDescriptor returnValueDescriptor = methodDescriptor.getReturnValueDescriptor();
		constraintDescriptors = returnValueDescriptor.getConstraintDescriptors();
		assertCorrectConstraintTypes( constraintDescriptors, ELAssert.class );
	}

	private void assertCorrectConstraintTypes( Set<ConstraintDescriptor<?>> constraintDescriptors,
											  Class<?>... constraints) {
		List<Class<?>> constraintAnnotations = new ArrayList<Class<?>>();
		constraintAnnotations.addAll( Arrays.asList( constraints ) );
		for ( ConstraintDescriptor descriptor : constraintDescriptors ) {
			Class<?> annotationType = descriptor.getAnnotation().annotationType();
			if ( constraintAnnotations.contains( annotationType ) ) {
				constraintAnnotations.remove( annotationType );
			}
			else {
				Assertions.fail( "found constraint type " + annotationType + " which was not expected" );
			}
		}

		if ( !constraintAnnotations.isEmpty() ) {
			fail( "not all expected constraint types were found" );
		}
	}
}
