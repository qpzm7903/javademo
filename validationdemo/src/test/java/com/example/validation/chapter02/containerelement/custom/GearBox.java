//tag::include[]
package com.example.validation.chapter02.containerelement.custom;

//end::include[]

//tag::include[]
public class GearBox<T extends Gear> {

	private final T gear;

	public GearBox(T gear) {
		this.gear = gear;
	}

	public Gear getGear() {
		return this.gear;
	}
}
//end::include[]
