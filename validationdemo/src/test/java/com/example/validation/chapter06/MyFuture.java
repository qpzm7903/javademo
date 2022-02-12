package com.example.validation.chapter06;

import javax.validation.Constraint;
import javax.validation.Payload;
import com.example.validation.chapter06.MyFuture.List;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(List.class)
@Documented
@Constraint(validatedBy = { })
public @interface MyFuture {

	String message() default "{com.example.MyFuture." +
			"message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	boolean orPresent() default false;

	@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
	@Retention(RUNTIME)
	@Documented
	@interface List {

		MyFuture[] value();
	}
}
