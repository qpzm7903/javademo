//tag::include[]
package com.example.validation.chapter07.valueextractor;

//end::include[]

import com.google.common.collect.Multimap;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

//tag::include[]
public class MultimapKeyValueExtractor
		implements ValueExtractor<Multimap<@ExtractedValue ?, ?>> {

	@Override
	public void extractValues(Multimap<?, ?> originalValue, ValueReceiver receiver) {
		for ( Object key : originalValue.keySet() ) {
			receiver.keyedValue( "<multimap key>", key, key );
		}
	}
}
//end::include[]
