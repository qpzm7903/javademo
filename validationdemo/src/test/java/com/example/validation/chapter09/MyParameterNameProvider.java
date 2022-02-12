//tag::include[]
package com.example.validation.chapter09;

//end::include[]

import javax.validation.ParameterNameProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

//tag::include[]
public class MyParameterNameProvider implements ParameterNameProvider {

	@Override
	public List<String> getParameterNames(Constructor<?> constructor) {
		//...
		return null;
	}

	@Override
	public List<String> getParameterNames(Method method) {
		//...
		return null;
	}
}
//end::include[]
