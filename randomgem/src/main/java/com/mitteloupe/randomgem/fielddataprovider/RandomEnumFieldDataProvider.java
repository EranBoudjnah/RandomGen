package com.mitteloupe.randomgem.fielddataprovider;

import com.mitteloupe.randomgem.FieldDataProvider;

import java.util.Random;

/**
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class RandomEnumFieldDataProvider<VALUE_TYPE extends Enum> implements FieldDataProvider<VALUE_TYPE> {
	private final Random mRandom;
	private final Object[] mPossibleValues;

	public RandomEnumFieldDataProvider(Random pRandom, VALUE_TYPE pValue) {
		mRandom = pRandom;
		mPossibleValues = pValue.getDeclaringClass().getEnumConstants();
	}

	@Override
	public VALUE_TYPE generate() {
		Object value = mPossibleValues[(int)(mRandom.nextDouble() * mPossibleValues.length)];
		//noinspection unchecked
		return (VALUE_TYPE)value;
	}
}
