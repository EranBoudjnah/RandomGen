package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates an {@code Enum} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class RandomEnumFieldDataProvider<OUTPUT_TYPE, VALUE_TYPE extends Enum> implements FieldDataProvider<OUTPUT_TYPE, VALUE_TYPE> {
	private final Random mRandom;
	private final VALUE_TYPE[] mPossibleValues;

	/**
	 * Creates an instance of {@link RandomEnumFieldDataProvider} generating a random {@code Enum} value.
	 *
	 * @param pRandom A random value generator
	 * @param pValue  An enum class of which values will be generated
	 */
	public RandomEnumFieldDataProvider(Random pRandom, Class<VALUE_TYPE> pValue) {
		mRandom = pRandom;
		mPossibleValues = pValue.getEnumConstants();
	}

	@Override
	public VALUE_TYPE generate(OUTPUT_TYPE instance) {
		return mPossibleValues[(int)(mRandom.nextDouble() * mPossibleValues.length)];
	}
}
