package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

/**
 * A {@link FieldDataProvider} that generates an explicit value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class ExplicitFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> implements FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> {
	private final VALUE_TYPE mValue;

	/**
	 * Creates an instance of {@link ExplicitFieldDataProvider} generating the same explicit value every time.
	 *
	 * @param pValue The value to generate
	 */
	public ExplicitFieldDataProvider(VALUE_TYPE pValue) {
		mValue = pValue;
	}

	@Override
	public VALUE_TYPE generate(OUTPUT_TYPE instance) {
		return mValue;
	}
}
