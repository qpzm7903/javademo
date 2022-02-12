//tag::include[]
package com.example.validation.chapter02.classlevel;

//end::include[]

import java.util.List;

//tag::include[]
@ValidPassengerCount
public class Car {

	private int seatCount;

	private List<Person> passengers;

	//...
}
//end::include[]
