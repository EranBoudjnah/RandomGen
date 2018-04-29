package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class ExplicitFieldDataProvider<VALUE_TYPE> implements FieldDataProvider<VALUE_TYPE> {
	private final VALUE_TYPE mValue;

	public ExplicitFieldDataProvider(VALUE_TYPE pValue) {
		mValue = pValue;
	}

	@Override
	public VALUE_TYPE generate() {
		return mValue;
	}
}
