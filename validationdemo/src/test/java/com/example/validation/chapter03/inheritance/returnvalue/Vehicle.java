//tag::include[]
package com.example.validation.chapter03.inheritance.returnvalue;

//end::include[]

import javax.validation.constraints.NotNull;

import java.util.List;

//tag::include[]
public interface Vehicle {

	@NotNull
	List<Person> getPassengers();
}
//end::include[]
