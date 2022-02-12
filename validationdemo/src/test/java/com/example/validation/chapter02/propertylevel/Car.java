//tag::include[]
package com.example.validation.chapter02.propertylevel;

//end::include[]

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

//tag::include[]
public class Car {

	private String manufacturer;

	private boolean isRegistered;

	public Car(String manufacturer, boolean isRegistered) {
		this.manufacturer = manufacturer;
		this.isRegistered = isRegistered;
	}

	@NotNull
	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	@AssertTrue
	public boolean isRegistered() {
		return isRegistered;
	}

	public void setRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}
}
//end::include[]
