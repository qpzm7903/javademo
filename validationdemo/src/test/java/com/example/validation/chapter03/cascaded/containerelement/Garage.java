//tag::include[]
package com.example.validation.chapter03.cascaded.containerelement;

//end::include[]

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

//tag::include[]
public class Garage {

	public boolean checkCars(@NotNull List<@Valid Car> cars) {
		//...
		return false;
	}
}
//end::include[]
