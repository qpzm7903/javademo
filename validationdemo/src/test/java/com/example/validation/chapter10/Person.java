//tag::include[]
package com.example.validation.chapter10;

//end::include[]

import javax.validation.constraints.NotNull;

//tag::include[]
public class Person {

	public interface Basic {
	}

	@NotNull
	private String name;

	//getters and setters ...
}
//end::include[]
