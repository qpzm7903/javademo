package com.example.validation.chapter09;

import com.google.common.collect.Multimap;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

public class MultimapKeyValueExtractor
		implements ValueExtractor<Multimap<@ExtractedValue ?, ?>> {

	@Override
	public void extractValues(Multimap<?, ?> originalValue, ValueReceiver receiver) {
		for ( Object key : originalValue.keySet() ) {
			receiver.keyedValue( "<multimap key>", key, key );
		}
	}
}
