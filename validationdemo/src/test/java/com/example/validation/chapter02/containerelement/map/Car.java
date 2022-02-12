//tag::include[]
package com.example.validation.chapter02.containerelement.map;

//end::include[]

import javax.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

//tag::include[]
public class Car {

	public enum FuelConsumption {
		CITY,
		HIGHWAY
	}

	private Map<@NotNull FuelConsumption, @MaxAllowedFuelConsumption Integer> fuelConsumption = new HashMap<>();

	public void setFuelConsumption(FuelConsumption consumption, int value) {
		fuelConsumption.put( consumption, value );
	}

	//...

}
//end::include[]
