//tag::include[]
package com.example.validation.chapter03.parameterscriptassert;

//end::include[]

import org.hibernate.validator.constraints.ParameterScriptAssert;

import java.util.List;

//tag::include[]
public class Car {

	@ParameterScriptAssert(lang = "groovy", script = "luggage.size() <= passengers.size() * 2")
	public void load(List<Person> passengers, List<PieceOfLuggage> luggage) {
		//...
	}
}
//end::include[]
