//tag::include[]
package com.example.validation.chapter06.crossparameter;

//end::include[]

import javax.validation.Constraint;
import javax.validation.ConstraintTarget;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//tag::include[]
@Constraint(validatedBy = {
		ScriptAssertObjectValidator.class,
		ScriptAssertParametersValidator.class
})
@Target({ TYPE, FIELD, PARAMETER, METHOD, CONSTRUCTOR, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface ScriptAssert {

	String message() default "{org.hibernate.validator.referenceguide.chapter04." +
			"crossparameter.ScriptAssert.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	String script();

	ConstraintTarget validationAppliesTo() default ConstraintTarget.IMPLICIT;
}
//end::include[]
