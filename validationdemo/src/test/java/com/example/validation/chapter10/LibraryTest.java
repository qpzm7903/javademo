package com.example.validation.chapter10;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ContainerElementTypeDescriptor;
import javax.validation.metadata.PropertyDescriptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUpValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void testContainerElementTypeDescriptor() {
		BeanDescriptor libraryDescriptor = validator.getConstraintsForClass( Library.class );

		//tag::testContainerElementTypeDescriptor[]
		PropertyDescriptor booksDescriptor = libraryDescriptor.getConstraintsForProperty(
				"books"
		);

		Set<ContainerElementTypeDescriptor> booksContainerElementTypeDescriptors =
				booksDescriptor.getConstrainedContainerElementTypes();
		ContainerElementTypeDescriptor booksContainerElementTypeDescriptor =
				booksContainerElementTypeDescriptors.iterator().next();

		assertTrue( booksContainerElementTypeDescriptor.hasConstraints() );
		assertTrue( booksContainerElementTypeDescriptor.isCascaded() );
		assertEquals(
				0,
				booksContainerElementTypeDescriptor.getTypeArgumentIndex().intValue()
		);
		assertEquals(
				List.class,
				booksContainerElementTypeDescriptor.getContainerClass()
		);

		Set<ConstraintDescriptor<?>> constraintDescriptors =
				booksContainerElementTypeDescriptor.getConstraintDescriptors();
		ConstraintDescriptor<?> constraintDescriptor =
				constraintDescriptors.iterator().next();

		assertEquals(
				NotNull.class,
				constraintDescriptor.getAnnotation().annotationType()
		);
		//end::testContainerElementTypeDescriptor[]
	}
}
