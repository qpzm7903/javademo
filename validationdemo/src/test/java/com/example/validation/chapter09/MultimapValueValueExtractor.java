package com.example.validation.chapter09;

import com.google.common.collect.Multimap;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

import java.util.Map.Entry;

public class MultimapValueValueExtractor
		implements ValueExtractor<Multimap<?, @ExtractedValue ?>> {

	@Override
	public void extractValues(Multimap<?, ?> originalValue, ValueReceiver receiver) {
		for ( Entry<?, ?> entry : originalValue.entries() ) {
			receiver.keyedValue( "<multimap value>", entry.getKey(), entry.getValue() );
		}
	}
}
