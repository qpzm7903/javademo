//tag::include[]
package com.example.validation.chapter09;

//end::include[]

import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

import java.util.List;

//tag::include[]
public class OsgiServiceDiscoverer implements ValidationProviderResolver {

	@Override
	public List<ValidationProvider<?>> getValidationProviders() {
		//...
		return null;
	}
}
//end::include[]
