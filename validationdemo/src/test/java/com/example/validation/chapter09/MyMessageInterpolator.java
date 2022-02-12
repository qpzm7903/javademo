//tag::include[]
package com.example.validation.chapter09;

//end::include[]

import javax.validation.MessageInterpolator;

import java.util.Locale;

//tag::include[]
public class MyMessageInterpolator implements MessageInterpolator {

	@Override
	public String interpolate(String messageTemplate, Context context) {
		//...
		return null;
	}

	@Override
	public String interpolate(String messageTemplate, Context context, Locale locale) {
		//...
		return null;
	}
}
//end::include[]
