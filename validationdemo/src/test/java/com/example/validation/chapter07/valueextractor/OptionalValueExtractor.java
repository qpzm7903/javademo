//tag::include[]
package com.example.validation.chapter07.valueextractor;

//end::include[]

import com.google.common.base.Optional;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

//tag::include[]
public class OptionalValueExtractor
		implements ValueExtractor<Optional<@ExtractedValue ?>> {

	@Override
	public void extractValues(Optional<?> originalValue, ValueReceiver receiver) {
		receiver.value( null, originalValue.orNull() );
	}
}
//end::include[]
