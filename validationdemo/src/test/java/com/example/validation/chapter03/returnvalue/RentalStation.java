//tag::include[]
package com.example.validation.chapter03.returnvalue;

//end::include[]

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

//tag::include[]
public class RentalStation {

	@ValidRentalStation
	public RentalStation() {
		//...
	}

	@NotNull
	@Size(min = 1)
	public List<@NotNull Customer> getCustomers() {
		//...
		return null;
	}
}
//end::include[]
