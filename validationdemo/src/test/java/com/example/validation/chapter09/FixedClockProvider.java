//tag::include[]
package com.example.validation.chapter09;

//end::include[]

import javax.validation.ClockProvider;

import java.time.Clock;
import java.time.ZonedDateTime;

//tag::include[]
public class FixedClockProvider implements ClockProvider {

	private Clock clock;

	public FixedClockProvider(ZonedDateTime dateTime) {
		clock = Clock.fixed( dateTime.toInstant(), dateTime.getZone() );
	}

	@Override
	public Clock getClock() {
		return clock;
	}

}
//end::include[]
