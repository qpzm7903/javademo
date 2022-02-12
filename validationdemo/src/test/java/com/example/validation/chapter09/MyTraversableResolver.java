//tag::include[]
package com.example.validation.chapter09;

//end::include[]

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;

import java.lang.annotation.ElementType;

//tag::include[]
public class MyTraversableResolver implements TraversableResolver {

	@Override
	public boolean isReachable(
			Object traversableObject,
			Node traversableProperty,
			Class<?> rootBeanType,
			Path pathToTraversableObject,
			ElementType elementType) {
		//...
		return false;
	}

	@Override
	public boolean isCascadable(
			Object traversableObject,
			Node traversableProperty,
			Class<?> rootBeanType,
			Path pathToTraversableObject,
			ElementType elementType) {
		//...
		return false;
	}
}
//end::include[]
