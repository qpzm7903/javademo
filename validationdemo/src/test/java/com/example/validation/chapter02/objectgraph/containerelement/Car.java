//tag::include[]
package com.example.validation.chapter02.objectgraph.containerelement;

//end::include[]

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//tag::include[]
public class Car {

	private List<@NotNull @Valid Person> passengers = new ArrayList<Person>();

	private Map<@Valid Part, List<@Valid Manufacturer>> partManufacturers = new HashMap<>();

	//...
}
//end::include[]
