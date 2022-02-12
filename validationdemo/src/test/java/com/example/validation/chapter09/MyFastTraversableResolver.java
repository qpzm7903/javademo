package com.example.validation.chapter09;

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;

import java.lang.annotation.ElementType;

public class MyFastTraversableResolver implements TraversableResolver {

	@Override
	public boolean isReachable(
			Object traversableObject,
			Node traversableProperty,
			Class<?> rootBeanType,
			Path pathToTraversableObject,
			ElementType elementType) {
		return true;
	}

	@Override
	public boolean isCascadable(
			Object traversableObject,
			Node traversableProperty,
			Class<?> rootBeanType,
			Path pathToTraversableObject,
			ElementType elementType) {
		return true;
	}
}
