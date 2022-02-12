//tag::include[]
package com.example.validation.chapter02.objectgraph;

//end::include[]

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

//tag::include[]
public class Car {

	@NotNull
	@Valid
	private Person driver;

	//...
}
//end::include[]

