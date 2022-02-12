//tag::include[]
package com.example.validation.chapter03.parameter;

//end::include[]

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.util.Date;

//tag::include[]
public class RentalStation {

	public RentalStation(@NotNull String name) {
		//...
	}

	public void rentCar(
			@NotNull Customer customer,
			@NotNull @Future Date startDate,
			@Min(1) int durationInDays) {
		//...
	}
}
//end::include[]
