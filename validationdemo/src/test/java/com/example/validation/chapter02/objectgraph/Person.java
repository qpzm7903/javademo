//tag::include[]
package com.example.validation.chapter02.objectgraph;

//end::include[]

import javax.validation.constraints.NotNull;

//tag::include[]
public class Person {

	@NotNull
	private String name;

	//...
}
//end::include[]
