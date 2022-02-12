package com.example.validation.chapter06.crossparameter;

import javax.validation.ConstraintTarget;

import java.util.List;

public class ScriptAssertTest {

	//tag::buildCar[]
	@ScriptAssert(script = "arg1.size() <= arg0", validationAppliesTo = ConstraintTarget.PARAMETERS)
	public Car buildCar(int seatCount, List<Passenger> passengers) {
		//...
		return null;
	}
	//end::buildCar[]

	private static class Car {
	}

	private static class Passenger {
	}
}
