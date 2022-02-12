//tag::include[]
package com.example.validation.chapter02.containerelement.nested;

//end::include[]

import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//tag::include[]
public class Car {

	private Map<@NotNull Part, List<@NotNull Manufacturer>> partManufacturers =
			new HashMap<>();

	//...
}
//end::include[]
