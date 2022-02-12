//tag::include[]
package com.example.validation.chapter06;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//tag::include[]
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCaseValidator.class)
@Documented
@Repeatable(CheckCase.List.class)
public @interface CheckCase {

	String message() default "{com.example.CheckCase." +
			"message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	CaseMode value();

	@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
	@Retention(RUNTIME)
	@Documented
	@interface List {
		CheckCase[] value();
	}
}
//end::include[]
