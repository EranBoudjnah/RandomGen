package com.mitteloupe.randomgen.fielddataprovider;

import com.mitteloupe.randomgen.FieldDataProvider;

import java.util.Random;

/**
 * A {@link FieldDataProvider} that generates a {@code Float} value.
 *
 * Created by Eran Boudjnah on 24/04/2018.
 */
public final class FloatFieldDataProvider<OUTPUT_TYPE> implements FieldDataProvider<OUTPUT_TYPE, Float> {
	private final Random mRandom;
	private final float mMinimum;
	private final float mMaximum;

	/**
	 * Creates an instance of {@link FloatFieldDataProvider} generating a {@code Float} between {@code 0.0f} and {@code 1.0f}.
	 *
	 * @param pRandom A random value generator
	 */
	public FloatFieldDataProvider(Random pRandom) {
		this(pRandom, 0f, 1f);
	}

	/**
	 * Creates an instance of {@link FloatFieldDataProvider} generating a {@code Float} between {@code pMinimum} and {@code pMaximum}.
	 *
	 * @param pRandom A random value generator
	 * @param pMinimum The lowest possible value
	 * @param pMaximum The highest possible value
	 */
	public FloatFieldDataProvider(Random pRandom, float pMinimum, float pMaximum) {
		mRandom = pRandom;
		mMinimum = pMinimum;
		mMaximum = pMaximum;
	}

	@Override
	public Float generate(OUTPUT_TYPE instance) {
		return mRandom.nextFloat() * (mMaximum - mMinimum) + mMinimum;
	}
}
