//tag::include[]
package com.example.validation.chapter03.validation;

//end::include[]

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

//tag::include[]
public class Car {

	public Car(@NotNull String manufacturer) {
		//...
	}

	@ValidRacingCar
	public Car(String manufacturer, String team) {
		//...
	}

	public void drive(@Max(75) int speedInMph) {
		//...
	}

	@Size(min = 1)
	public List<Passenger> getPassengers() {
		//...
		return Collections.emptyList();
	}
}
//end::include[]
