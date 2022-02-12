package com.example.validation.chapter07;

import com.google.common.collect.Multimap;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.valueextraction.Unwrapping;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.StringProperty;

import java.util.OptionalInt;

@SuppressWarnings({ "restriction", "unused" })
public class Container {

	//tag::multimapValues[]
	private Multimap<String, @NotBlank String> map1;
	//end::multimapValues[]

	//tag::multimapKeysAndValues[]
	private Multimap<@NotBlank String, @NotBlank String> map2;
	//end::multimapKeysAndValues[]

	//tag::optionalIntUnwrap[]
	@Min(value = 5, payload = Unwrapping.Unwrap.class)
	private OptionalInt optionalInt1;
	//end::optionalIntUnwrap[]

	//tag::optionalInt[]
	@Min(5)
	private OptionalInt optionalInt2;
	//end::optionalInt[]

	//tag::optionalIntSkip[]
	@NotNull(payload = Unwrapping.Skip.class)
	@Min(5)
	private OptionalInt optionalInt3;
	//end::optionalIntSkip[]

	//tag::stringProperty[]
	@NotBlank
	private StringProperty stringProperty;
	//end::stringProperty[]

	//tag::longProperty[]
	@Min(5)
	private LongProperty longProperty;
	//end::longProperty[]

	//tag::listProperty[]
	@Size(min = 1)
	private ReadOnlyListProperty<@NotBlank String> listProperty;
	//end::listProperty[]
}
