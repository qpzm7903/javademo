//tag::include[]
package com.example.validation.chapter03.crossparameter.constrainttarget;

//end::include[]

import javax.validation.ConstraintTarget;

import java.util.List;

//tag::include[]
public class Garage {

	@ELAssert(expression = "...", validationAppliesTo = ConstraintTarget.PARAMETERS)
	public Car buildCar(List<Part> parts) {
		//...
		return null;
	}

	@ELAssert(expression = "...", validationAppliesTo = ConstraintTarget.RETURN_VALUE)
	public Car paintCar(int color) {
		//...
		return null;
	}
}
//end::include[]
