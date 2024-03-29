//tag::include[]
package com.example.validation.chapter06.crossparameter;

//end::include[]

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//tag::include[]
@Constraint(validatedBy = ConsistentDateParametersValidator.class)
@Target({ METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ConsistentDateParameters {

	String message() default "{org.hibernate.validator.referenceguide.chapter04." +
			"crossparameter.ConsistentDateParameters.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
//end::include[]
