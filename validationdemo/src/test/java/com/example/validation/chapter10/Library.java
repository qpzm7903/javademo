//tag::include[]
package com.example.validation.chapter10;

//end::include[]

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

@SuppressWarnings("unused")
//tag::include[]
public class Library {

	@NotNull
	private String name;

	private List<@NotNull @Valid Book> books;

	//getters and setters ...
}
//end::include[]
